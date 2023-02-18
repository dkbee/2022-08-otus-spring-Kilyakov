package ru.otus.spring.kilyakov.service.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import static ru.otus.spring.kilyakov.config.JobConfig.MIGRATE_LIBRARY_JOB_NAME;

@RequiredArgsConstructor
@ShellComponent
public class BatchCommands {

    private final Job migrateLibraryJob;

    private final JobLauncher jobLauncher;
    private final JobExplorer jobExplorer;

    //http://localhost:8080/h2-console/

    @ShellMethod(value = "startMigrationJobWithJobLauncher", key = "sm-jl")
    public void startMigrationJobWithJobLauncher() throws Exception {
        JobExecution execution = jobLauncher.run(migrateLibraryJob, new JobParametersBuilder()
                .toJobParameters());
        System.out.println(execution);
    }

    @ShellMethod(value = "showInfo", key = "i")
    public void showInfo() {
        System.out.println(jobExplorer.getJobNames());
        System.out.println(jobExplorer.getLastJobInstance(MIGRATE_LIBRARY_JOB_NAME));
    }
}
