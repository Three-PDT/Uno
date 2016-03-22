package com.example.myproject;

import java.sql.SQLException;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.separator.DefaultRecordSeparatorPolicy;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.scheduling.config.Task;

import com.example.myproject.entities.Maintenance;
import com.example.myproject.mapper.PlannedFieldSetMapper;
import com.example.myproject.mapper.UnplannedFieldSetMapper;
import com.example.myproject.processor.CustomItemProcessor;

/**
 * This class contains all the configuration of the Spring Batch application. It
 * contains readers, writers, processors, jobs, steps and all the needed beans.
 * 
 * 
 *
 */
@Configuration
@EnableBatchProcessing
@EntityScan(basePackages = "com.example.myproject.entities")
public class ApplicationConfiguration {
	/**
	 * Returns a reader
	 * 
	 * @return
	 */
	// @Bean
	// public ItemReader<InputRecord> reader() {
	// // flat file item reader (using an csv extractor)
	// FlatFileItemReader<InputRecord> reader = new
	// FlatFileItemReader<InputRecord>();
	// reader.setResource(new ClassPathResource("input.csv"));
	// reader.setLineMapper(new DefaultLineMapper<InputRecord>() {
	// {
	// setLineTokenizer(new DelimitedLineTokenizer() {
	// {
	// setNames(new String[]{"id", "description"});
	// }
	// });
	// setFieldSetMapper(new BeanWrapperFieldSetMapper<InputRecord>() {
	// {
	// setTargetType(InputRecord.class);
	// }
	// });
	// }
	// });
	// return reader;
	// }

	@Autowired
	private Environment env;

	@Bean
	@Qualifier("PlannedItemReader")
	public ItemReader<Maintenance> plannedReader() {
		// flat file item reader (using an csv extractor)
		FlatFileItemReader<Maintenance> reader = new FlatFileItemReader<Maintenance>();
		reader.setResource(new ClassPathResource("H3GPlannedOutageFeed_X_0.csv"));
		// skip header
		reader.setLinesToSkip(1);
		reader.setRecordSeparatorPolicy(new DefaultRecordSeparatorPolicy());
		reader.setLineMapper(new DefaultLineMapper<Maintenance>() {
			{
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setNames(new String[]{"CMID", "MBNL_Site_ID", "H3G_Nominal_ID",
								"Planned_Outage_Start_Time", "Planned_Outage_End_Time",
								"Actual_Outage_Start_Time", "Actual_Outage_End_Time", "Network",
								"Network_Type", "Description", "Status", "Linked_Site", "Breach_Code",
								"Expected_Resolution"});
					}
				});
				setFieldSetMapper(new PlannedFieldSetMapper());

			}
		});
		return reader;
	}

	@Bean
	@Qualifier("UnplannedItemReader")
	public ItemReader<Maintenance> unplannedReader() {
		// flat file item reader (using an csv extractor)
		FlatFileItemReader<Maintenance> reader = new FlatFileItemReader<Maintenance>();
		reader.setResource(new ClassPathResource("H3GUnplannedOutageFeed_X_0.csv"));
		// skip header
		reader.setLinesToSkip(1);
		reader.setLineMapper(new DefaultLineMapper<Maintenance>() {
			{
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setNames(new String[]{"PRID - MBNL", "Prid - H3G", "Priority", "Status",
								"Created_Date", "MBNL_ID", "Nominal_ID", "Description", "Modified_Date",
								"Start_Service_Impact_Date", "End_Service_Impact_Date", "Resolved_Date",
								"Closed_Date", "Products_List", "ObjectZone", "Network_Type", "Linked_Site"});
					}
				});
				// setLineTokenizer(new DelimitedLineTokenizer());
				setFieldSetMapper(new UnplannedFieldSetMapper());

			}
		});
		return reader;

	}

	@Bean
	public ItemProcessor<Maintenance, Maintenance> processor() {
		return new CustomItemProcessor();
	}

	@Bean
	public ItemWriter<Maintenance> writer(LocalContainerEntityManagerFactoryBean lcemfb) throws SQLException {
		JpaItemWriter writer = new JpaItemWriter<Maintenance>();
		writer.setEntityManagerFactory(lcemfb.getObject());
		return writer;
	}

	@Bean
	public TaskExecutor TaskExecutor() {
		SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
		taskExecutor.setConcurrencyLimit(10);
		return taskExecutor;
	}

	/**
	 * Returns a job bean
	 */
	@Bean
	public Job importUserJob(JobBuilderFactory jobs, @Qualifier("plannedStep") Step s1,
			@Qualifier("unplannedStep") Step s2, TaskExecutor taskExecutor) {

		// final Flow plannedStepFlow = new
		// FlowBuilder<Flow>("plannedStepFlow").from(s1).end();
		// final Flow unplannedStepFlow = new
		// FlowBuilder<Flow>("unplannedStepFlow").from(s2).end();
		// final Flow splitFlow = new
		// FlowBuilder<Flow>("splitFlow").split(taskExecutor)
		// .add(plannedStepFlow, unplannedStepFlow).build();
		// return jobs.get("importUserJob").incrementer(new
		// RunIdIncrementer()).start(splitFlow).end().build();
		return jobs.get("importUserJob").incrementer(new RunIdIncrementer()).flow(s1).on("*").to(s2).end()
				.build();
		// return jobs.get("importUserJob").incrementer(new
		// RunIdIncrementer()).flow(s1).end().build();
	}

	/**
	 * This job step reads a CSV, maps to pojo, then inserts into database.
	 * infinitely
	 * 
	 * Returns a step. .allowStartIfComplete(true) forces re-execution of a step
	 * even if it completed successfully.
	 */

	@Bean
	@Qualifier("plannedStep")
	public Step plannedStep(StepBuilderFactory stepBuilderFactory,
			@Qualifier("PlannedItemReader") ItemReader<Maintenance> reader, ItemWriter<Maintenance> writer,
			ItemProcessor<Maintenance, Maintenance> processor,TaskExecutor taskExecutor) {
		/* it handles bunches of 10 units */
		return stepBuilderFactory.get("plannedStep").<Maintenance, Maintenance> chunk(50).reader(reader)
				.processor(processor).writer(writer).allowStartIfComplete(true).taskExecutor(taskExecutor).build();

		// return stepBuilderFactory.get("step1").<UnplannedMaintenance,
		// UnplannedMaintenance>
		// chunk(10).reader(reader).processor(processor).writer(writer).build();
	}

	@Bean
	@Qualifier("plannedStep2")
	public Step plannedStep2(StepBuilderFactory stepBuilderFactory,
			@Qualifier("PlannedItemReader") ItemReader<Maintenance> reader, ItemWriter<Maintenance> writer,
			ItemProcessor<Maintenance, Maintenance> processor,TaskExecutor taskExecutor) {
		/* it handles bunches of 10 units */
		return stepBuilderFactory.get("plannedStep2").<Maintenance, Maintenance> chunk(50).reader(reader)
				.processor(processor).writer(writer).allowStartIfComplete(true).taskExecutor(taskExecutor).build();

		// return stepBuilderFactory.get("step1").<UnplannedMaintenance,
		// UnplannedMaintenance>
		// chunk(10).reader(reader).processor(processor).writer(writer).build();
	}

	@Bean
	@Qualifier("unplannedStep")
	public Step unplannedStep(StepBuilderFactory stepBuilderFactory,
			@Qualifier("UnplannedItemReader") ItemReader<Maintenance> reader, ItemWriter<Maintenance> writer,
			ItemProcessor<Maintenance, Maintenance> processor,TaskExecutor taskExecutor) {

		/* it handles bunches of 50 units */
		return stepBuilderFactory.get("unplannedStep").<Maintenance, Maintenance> chunk(50).reader(reader)
				.processor(processor).writer(writer).allowStartIfComplete(true).taskExecutor(taskExecutor).build();
	}


	@Bean
	public TaskExecutor getTaskExecutor(){
		SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = new SimpleAsyncTaskExecutor();
		simpleAsyncTaskExecutor.setConcurrencyLimit(10);
		return simpleAsyncTaskExecutor;
	}
	
	/**
	 * Utilities
	 * 
	 * @throws SQLException
	 */
	// @Bean
	// public LocalContainerEntityManagerFactoryBean
	// entityManagerFactory(DataSource dataSource)
	// throws SQLException {
	// LocalContainerEntityManagerFactoryBean lef = new
	// LocalContainerEntityManagerFactoryBean();
	// lef.setDataSource(dataSource);
	// lef.setJpaVendorAdapter(jpaVendorAdapter());
	// lef.setJpaProperties(new Properties());
	// return lef;
	// }

	// @Bean
	// public JpaVendorAdapter jpaVendorAdapter() {
	// HibernateJpaVendorAdapter jpaVendorAdapter = new
	// HibernateJpaVendorAdapter();
	// jpaVendorAdapter.setDatabase(Database.DB2);
	// jpaVendorAdapter.setGenerateDdl(true);
	// jpaVendorAdapter.setShowSql(false);
	//
	// jpaVendorAdapter.setDatabasePlatform(env.getProperty("spring.jpa.properties.hibernate.dialect"));
	// return jpaVendorAdapter;
	// }

}