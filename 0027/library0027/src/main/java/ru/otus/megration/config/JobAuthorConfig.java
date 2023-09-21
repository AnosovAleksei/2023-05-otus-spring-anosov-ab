package ru.otus.megration.config;

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
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.lang.NonNull;
import ru.otus.domain.Author;


import javax.sql.DataSource;
import java.util.Map;


@SuppressWarnings("unused")
@Slf4j
@Configuration
@RequiredArgsConstructor
public class JobAuthorConfig {

    public final String IMPORT_AUTHOR_JOB_NAME = "importAuthor";

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;


    @StepScope
    @Bean
    public MongoItemReader<Author> readerAuthor(MongoTemplate mongoTemplate) {
        return new MongoItemReaderBuilder<Author>()
                .name("authorReader")
                .targetType(Author.class)
                .template(mongoTemplate)
                .jsonQuery("{}")
                .sorts(Map.of())
                .build();
    }


    @StepScope
    @Bean
    public ItemProcessor<ru.otus.domain.Author,
                         ru.otus.megration.entity.Author>
                            processorAuthor(ru.otus.megration.service.AuthorService authorService) {
        return authorService::convert;
    }

    @StepScope
    @Bean
    public JdbcBatchItemWriter<ru.otus.megration.entity.Author> writerAuthor(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<ru.otus.megration.entity.Author>()
                .dataSource(dataSource)
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("""
                        insert into author (name)
                         values (:name);""")

                .build();
    }

    @Bean
    public Step transformAuthorStep(ItemReader<Author> readerAuthor,
                                    JdbcBatchItemWriter<ru.otus.megration.entity.Author> writerAuthor,
                                    ItemProcessor<ru.otus.domain.Author, ru.otus.megration.entity.Author> itemProcessorAuthor) {
        return new StepBuilder("transformPersonsStep", jobRepository)
                .<ru.otus.domain.Author, ru.otus.megration.entity.Author>chunk(10, platformTransactionManager)
                .reader(readerAuthor)
                .processor(itemProcessorAuthor)
                .writer(writerAuthor)
                .listener(new ItemReadListener<>() {
                    public void beforeRead() {
                        log.info("Начало чтения");
                    }

                    public void afterRead(@NonNull Author o) {
                        log.info("Конец чтения {}", o.getName());
                    }

                    public void onReadError(@NonNull Exception e) {
                        log.info("Ошибка чтения");
                    }
                })
                .build();
    }

    @Bean
    public Job importAuthorJob(Step transformAuthorStep) {
        return new JobBuilder(IMPORT_AUTHOR_JOB_NAME, jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(transformAuthorStep)
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
