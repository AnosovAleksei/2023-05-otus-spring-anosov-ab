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
import ru.otus.domain.Genre;


import javax.sql.DataSource;
import java.util.Map;


@SuppressWarnings("unused")
@Slf4j
@Configuration
@RequiredArgsConstructor
public class JobGenreConfig {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;



    @StepScope
    @Bean
    public MongoItemReader<Genre> readerGenre(MongoTemplate mongoTemplate) {
        return new MongoItemReaderBuilder<Genre>()
                .name("genreReader")
                .targetType(Genre.class)
                .template(mongoTemplate)
                .jsonQuery("{}")
                .sorts(Map.of())
                .build();
    }


    @StepScope
    @Bean
    public ItemProcessor<Genre,
                         ru.otus.migration.entity.Genre>
                            processorGenre(ru.otus.migration.service.GenreService genreService) {
        return genreService::convert;
    }

    @StepScope
    @Bean
    public JdbcBatchItemWriter<ru.otus.migration.entity.Genre> writerGenre(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<ru.otus.migration.entity.Genre>()
                .dataSource(dataSource)
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("""
                        insert into genre (name)
                         values (:name);""")

                .build();
    }

    @Bean
    public Step transformGenreStep(ItemReader<Genre> readerGenre,
                                    JdbcBatchItemWriter<ru.otus.migration.entity.Genre> writerGenre,
                                    ItemProcessor<Genre, ru.otus.migration.entity.Genre> itemProcessorGenre) {
        return new StepBuilder("transformPersonsStep", jobRepository)
                .<Genre, ru.otus.migration.entity.Genre>chunk(10, platformTransactionManager)
                .reader(readerGenre)
                .processor(itemProcessorGenre)
                .writer(writerGenre)
                .listener(new ItemReadListener<>() {
                    public void beforeRead() {
                        log.info("Начало чтения");
                    }

                    public void afterRead(@NonNull Genre o) {
                        log.info("Конец чтения {}", o.getName());
                    }

                    public void onReadError(@NonNull Exception e) {
                        log.info("Ошибка чтения");
                    }
                })
                .build();
    }

    @Bean
    public Job importGenreJob(Step transformGenreStep) {
        return new JobBuilder("importGenre", jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(transformGenreStep)
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
