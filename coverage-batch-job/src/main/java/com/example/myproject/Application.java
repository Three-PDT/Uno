package com.example.myproject;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@SpringBootApplication
public class Application implements CommandLineRunner {

	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);
	}

//	@Autowired
//	JdbcTemplate jdbcTemplate;

	@Override
	public void run(String... strings) throws Exception {
		System.out.println("running...");
	}
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private Job job;
	
	
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    
    
//    @Scheduled(cron="0,30 * * * *")
    @Scheduled(fixedRate = 5000)
    public void doJob() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException{
		
        System.out.println("The time is now " + dateFormat.format(new Date()));

    	/**
		 * without this you will receive a
		 * org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException: if batch tables
		 * exist and job has completed
		 */
		JobParameters jobParameters = new JobParametersBuilder().addLong("time",
				System.currentTimeMillis()).toJobParameters();

		jobLauncher.run(job, jobParameters);
    }

}
