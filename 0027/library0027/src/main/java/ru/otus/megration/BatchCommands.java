package ru.otus.megration;


import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@RequiredArgsConstructor
@ShellComponent
public class BatchCommands {

    private final Job importAuthorJob;

    private final Job importGenreJob;

    private final Job importBookJob;

    private final Job importCommentaryJob;

    private final JobLauncher jobLauncher;
    private final JobOperator jobOperator;
    private final JobExplorer jobExplorer;

    @SuppressWarnings("unused")
    @ShellMethod(value = "startMigrationAuthor", key = "sm-a")
    public void startMigrationAuthor() throws Exception {
        JobExecution execution = jobLauncher.run(importAuthorJob, new JobParametersBuilder()
                .toJobParameters());
    }

    @SuppressWarnings("unused")
    @ShellMethod(value = "startMigrationGenre", key = "sm-g")
    public void startMigrationGenre() throws Exception {
        JobExecution execution = jobLauncher.run(importGenreJob, new JobParametersBuilder()
                .toJobParameters());
    }
    @SuppressWarnings("unused")
    @ShellMethod(value = "startMigrationBook", key = "sm-b")
    public void startMigrationBook() throws Exception {
        JobExecution execution = jobLauncher.run(importBookJob, new JobParametersBuilder()
                .toJobParameters());
    }


    @SuppressWarnings("unused")
    @ShellMethod(value = "startMigrationBook", key = "sm-c")
    public void startMigrationCommentary() throws Exception {
        JobExecution execution = jobLauncher.run(importCommentaryJob, new JobParametersBuilder()
                .toJobParameters());
    }

    @SuppressWarnings("unused")
    @ShellMethod(value = "startMigrationAll", key = "sm-all")
    public void startMigrationAll() throws Exception {
        jobLauncher.run(importAuthorJob, new JobParametersBuilder()
                .toJobParameters());

        jobLauncher.run(importGenreJob, new JobParametersBuilder()
                .toJobParameters());

        jobLauncher.run(importBookJob, new JobParametersBuilder()
                .toJobParameters());

        jobLauncher.run(importCommentaryJob, new JobParametersBuilder()
                .toJobParameters());
    }
}
