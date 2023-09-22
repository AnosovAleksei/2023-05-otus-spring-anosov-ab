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
import ru.otus.domain.Commentary;

import javax.sql.DataSource;
import java.util.Map;


@SuppressWarnings("unused")
@Slf4j
@Configuration
@RequiredArgsConstructor
public class JobCommentaryConfig {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;


    @StepScope
    @Bean
    public MongoItemReader<Commentary> readerCommentary(MongoTemplate mongoTemplate) {
        return new MongoItemReaderBuilder<Commentary>()
                .name("commentaryReader")
                .targetType(Commentary.class)
                .template(mongoTemplate)
                .jsonQuery("{}")
                .sorts(Map.of())
                .build();
    }


    @StepScope
    @Bean
    public ItemProcessor<Commentary,
            ru.otus.migration.entity.Commentary>
    processorCommentary(ru.otus.migration.service.CommentaryService commentaryService) {
        return commentaryService::convert;
    }

    @StepScope
    @Bean
    public JdbcBatchItemWriter<ru.otus.migration.entity.Commentary> writerCommentary(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<ru.otus.migration.entity.Commentary>()
                .dataSource(dataSource)
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("""
                        insert into commentary (book_id, message)
                        values  (select book_id from book where name = :book.name, :message)
                        """)
                .build();
    }

    @Bean
    public Step transformCommentaryStep(ItemReader<Commentary> readerCommentary,
                                        JdbcBatchItemWriter<ru.otus.migration.entity.Commentary> writerCommentary,
                                        ItemProcessor<Commentary, ru.otus.migration.entity.Commentary>
                                                itemProcessorCommentary) {
        return new StepBuilder("transformCommentaryStep", jobRepository)
                .<Commentary, ru.otus.migration.entity.Commentary>chunk(10, platformTransactionManager)
                .reader(readerCommentary)
                .processor(itemProcessorCommentary)
                .writer(writerCommentary)
                .listener(new ItemReadListener<>() {
                    public void beforeRead() {
                        log.info("Начало чтения");
                    }

                    public void afterRead(@NonNull Commentary o) {
                        log.info("Конец чтения {}", o.getMessage());
                    }

                    public void onReadError(@NonNull Exception e) {
                        log.info("Ошибка чтения");
                    }
                })
                .build();
    }

    @Bean
    public Job importCommentaryJob(Step transformCommentaryStep) {
        return new JobBuilder("importCommentary", jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(transformCommentaryStep)
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
