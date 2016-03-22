package com.example.myproject.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Dave Syer
 * 
 */
@EnableScheduling
@SpringBootApplication
public class TestRunnerConfiguration {


	
	
	
	@Bean
	public JobLauncherTestUtils utils(Job job, JobRepository jobRepository, JobLauncher jobLauncher) throws Exception {
		JobLauncherTestUtils launcher = new JobLauncherTestUtils();
		launcher.setJob(job);
		launcher.setJobRepository(jobRepository);
		launcher.setJobLauncher(jobLauncher);
		return launcher;
	}

}