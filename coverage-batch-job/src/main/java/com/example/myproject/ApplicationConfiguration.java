package com.example.myproject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
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

import com.example.myproject.entities.Coverage;
import com.example.myproject.entities.Record;
import com.example.myproject.mapper.FourgFieldSetMapper;
import com.example.myproject.mapper.L800FieldSetMapper;
import com.example.myproject.mapper.ThreegFieldSetMapper;
import com.example.myproject.processor.CustomItemProcessor;
import com.example.myproject.writer.CustomItemWriter;

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

	@Autowired
	private Environment env;

	@Bean
	@Qualifier("multiResourceReader")
	public ItemReader<List<Coverage>> getRecordReader() {
		final ItemReader<Record>  l800Reader = l800Reader();
		final ItemReader<Record> fourgReader = fourgReader();
		final ItemReader<Record> threegReader = threegReader();
	    return new ItemReader<List<Coverage>>(){ 
	        public List<Coverage> read() throws UnexpectedInputException, ParseException, NonTransientResourceException, Exception {
	            return mergeRecord(l800Reader.read(), fourgReader.read(), threegReader.read());
	        }
	    };
	}
	
	
	public List<Coverage> mergeRecord(Record l800, Record fourg, Record threeg){
		List<Coverage> list = new ArrayList<Coverage>();
		
		return list;
	}
	@Bean
	@Qualifier("l800Reader")
	public ItemReader<Record> l800Reader() {
		// flat file item reader (using an csv extractor)
		FlatFileItemReader<Record> reader = new FlatFileItemReader<Record>();
		reader.setResource(new ClassPathResource("Postcode_Coverage_WK02_LTE800.csv"));
		// skip header
		reader.setLinesToSkip(1);
		reader.setRecordSeparatorPolicy(new DefaultRecordSeparatorPolicy());
		reader.setLineMapper(new DefaultLineMapper<Record>() {
			{
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setNames(new String[]{"POSTCODE","RANKING"});
					}
				});
				setFieldSetMapper(new L800FieldSetMapper());
				

			}
		});
		return reader;
	}
	
	@Bean
	@Qualifier("fourgReader")
	public ItemReader<Record> fourgReader() {
		// flat file item reader (using an csv extractor)
		FlatFileItemReader<Record> reader = new FlatFileItemReader<Record>();
		reader.setResource(new ClassPathResource("Postcode_Coverage_WK02_LTE1800.csv"));
		// skip header
		reader.setLinesToSkip(1);
		reader.setRecordSeparatorPolicy(new DefaultRecordSeparatorPolicy());
		reader.setLineMapper(new DefaultLineMapper<Record>() {
			{
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setNames(new String[]{"POSTCODE","RANKING"});
					}
				});
				setFieldSetMapper(new FourgFieldSetMapper());
				

			}
		});
		return reader;
	}
	
	@Bean
	@Qualifier("threegReader")
	public ItemReader<Record> threegReader() {
		// flat file item reader (using an csv extractor)
		FlatFileItemReader<Record> reader = new FlatFileItemReader<Record>();
		reader.setResource(new ClassPathResource("Postcode_Coverage_WK08_3G.csv"));
		// skip header
		reader.setLinesToSkip(1);
		reader.setRecordSeparatorPolicy(new DefaultRecordSeparatorPolicy());
		reader.setLineMapper(new DefaultLineMapper<Record>() {
			{
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setNames(new String[]{"Postcode","Coverage"});
					}
				});
				setFieldSetMapper(new ThreegFieldSetMapper());
			}
		});
		return reader;
	}

	
	@Bean
	public ItemProcessor<Record, Record> processor() {
		return new CustomItemProcessor();
	}

	@Bean
	public ItemWriter<Record> writer(LocalContainerEntityManagerFactoryBean lcemfb)
			throws SQLException {
		
//		JpaItemWriter<Record> writer = new JpaItemWriter<Record>();
//		writer.setEntityManagerFactory(lcemfb.getObject());
//		return writer;
		CustomItemWriter writer = new CustomItemWriter();
		writer.setEntityManagerFactory(lcemfb.getObject());
		return writer;
	}

	/**
	 * Returns a job bean
	 */
	@Bean
	public Job importUserJob(JobBuilderFactory jobs, @Qualifier("l800Step") Step s1, @Qualifier("fourgStep") Step s2, @Qualifier("threegStep") Step s3) {
		return jobs.get("importUserJob").incrementer(new RunIdIncrementer()).flow(s1).next(s2).next(s3).end().build();
		//return jobs.get("importUserJob").incrementer(new RunIdIncrementer()).flow(s3).end().build();
		//return jobs.get("importUserJob").incrementer(new RunIdIncrementer()).flow(s1).on("*").to(s2).end()
		//		.build();
	}

	/**
	 * This job step reads a CSV, maps to pojo, then inserts into database.
	 * infinitely
	 * 
	 * Returns a step. .allowStartIfComplete(true) forces re-execution of a step
	 * even if it completed successfully.
	 */
	@Bean
	@Qualifier("l800Step")
	public Step step1(StepBuilderFactory stepBuilderFactory,
			@Qualifier("l800Reader") ItemReader<Record> reader,
			ItemWriter<Record> writer,
			ItemProcessor<Record, Record> processor, TaskExecutor taskExecutor) {
		/* it handles bunches of 10 units */
		return stepBuilderFactory.get("step1").<Record, Record> chunk(500)
				.reader(reader).processor(processor).writer(writer).taskExecutor(taskExecutor).build();
	
	}

	@Bean
	@Qualifier("fourgStep")
	public Step step2(StepBuilderFactory stepBuilderFactory,
			@Qualifier("fourgReader") ItemReader<Record> reader,
			ItemWriter<Record> writer,
			ItemProcessor<Record, Record> processor,TaskExecutor taskExecutor) {
		/* it handles bunches of 10 units */
		return stepBuilderFactory.get("step2").<Record, Record> chunk(500)
				.reader(reader).processor(processor).writer(writer).taskExecutor(taskExecutor).build();
	
	}
	
	@Bean
	@Qualifier("threegStep")
	public Step step3(StepBuilderFactory stepBuilderFactory,
			@Qualifier("threegReader") ItemReader<Record> reader,
			ItemWriter<Record> writer,
			ItemProcessor<Record, Record> processor,TaskExecutor taskExecutor) {
		/* it handles bunches of 10 units */
		return stepBuilderFactory.get("step3").<Record, Record> chunk(500)
				.reader(reader).processor(processor).writer(writer).taskExecutor(taskExecutor).build();

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
//	@Bean
//	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource)
//			throws SQLException {
//		LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
//		lef.setDataSource(dataSource);
//		lef.setJpaVendorAdapter(jpaVendorAdapter());
//		lef.setJpaProperties(new Properties());
//		return lef;
//	}

//	@Bean
//	public JpaVendorAdapter jpaVendorAdapter() {
//		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
//		jpaVendorAdapter.setDatabase(Database.DB2);
//		jpaVendorAdapter.setGenerateDdl(true);
//		jpaVendorAdapter.setShowSql(false);
//
//		jpaVendorAdapter.setDatabasePlatform(env.getProperty("spring.jpa.properties.hibernate.dialect"));
//		return jpaVendorAdapter;
//	}

}