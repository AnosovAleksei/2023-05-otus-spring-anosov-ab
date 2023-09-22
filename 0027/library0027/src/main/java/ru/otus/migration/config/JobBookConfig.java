package ru.otus.migration.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.lang.NonNull;
import org.springframework.transaction.PlatformTransactionManager;
import ru.otus.domain.Book;

import javax.sql.DataSource;
import java.util.Map;


@SuppressWarnings("unused")
@Slf4j
@Configuration
@RequiredArgsConstructor
public class JobBookConfig {


    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;



    @StepScope
    @Bean
    public MongoItemReader<Book> readerBook(MongoTemplate mongoTemplate) {
        return new MongoItemReaderBuilder<Book>()
                .name("bookReader")
                .targetType(Book.class)
                .template(mongoTemplate)
                .jsonQuery("{}")
                .sorts(Map.of())
                .build();
    }


    @StepScope
    @Bean
    public ItemProcessor<Book,
                         ru.otus.migration.entity.Book>
                            processorBook(ru.otus.migration.service.BookService bookService) {
        return bookService::convert;
    }

    @StepScope
    @Bean
    public JdbcBatchItemWriter<ru.otus.migration.entity.Book> writerBook(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<ru.otus.migration.entity.Book>()
                .dataSource(dataSource)
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("""
                        insert into book (name, author_id, genre_id)
                         values (:name, 
                         select author_id from author where name = :author.name, 
                         select genre_id from genre where name = :genre.name);""")

                .build();
    }

    @Bean
    public Step transformBookStep(ItemReader<Book> readerBook,
                                    JdbcBatchItemWriter<ru.otus.migration.entity.Book> writerBook,
                                    ItemProcessor<Book, ru.otus.migration.entity.Book> itemProcessorBook) {
        return new StepBuilder("transformBookStep", jobRepository)
                .<Book, ru.otus.migration.entity.Book>chunk(10, platformTransactionManager)
                .reader(readerBook)
                .processor(itemProcessorBook)
                .writer(writerBook)
                .listener(new ItemReadListener<>() {
                    public void beforeRead() {
                        log.info("Начало чтения");
                    }

                    public void afterRead(@NonNull Book o) {
                        log.info("Конец чтения {}", o.getName());
                    }

                    public void onReadError(@NonNull Exception e) {
                        log.info("Ошибка чтения");
                    }
                })
                .build();
    }

    @Bean
    public Job importBookJob(Step transformBookStep) {
        return new JobBuilder("importBook", jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(transformBookStep)
                .end()
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(@NonNull JobExecution jobExecution) {
                        log.info("Начало job");
                    }

                    @Override
                    public void afterJob(@NonNull JobExecution jobExecution) {
                        log.info("Конец job");
                    }
                })
                .build();
    }


}
