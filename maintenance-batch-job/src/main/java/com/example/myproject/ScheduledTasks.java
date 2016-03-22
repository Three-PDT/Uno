//package com.example.myproject;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.JobParameters;
//import org.springframework.batch.core.JobParametersBuilder;
//import org.springframework.batch.core.JobParametersInvalidException;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
//import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
//import org.springframework.batch.core.repository.JobRestartException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ScheduledTasks {
//
//	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
//	@Autowired
//	private JobLauncher jobLauncher;
//	
//	@Autowired
//	private Job job;
//	@Scheduled(fixedRate = 30000)
//	public void doJob() throws JobExecutionAlreadyRunningException, JobRestartException,
//			JobInstanceAlreadyCompleteException, JobParametersInvalidException {
//
//		System.out.println("The time is now " + dateFormat.format(new Date()));
//
//		/**
//		 * without this you will receive a
//		 * org.springframework.batch.core.repository.
//		 * JobInstanceAlreadyCompleteException: if batch tables exist and job
//		 * has completed
//		 */
//		JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
//				.toJobParameters();
//
//		jobLauncher.run(job, jobParameters);
//	}
//}
