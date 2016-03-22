package com.example.myproject.reader;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.myproject.ApplicationConfiguration;
import com.example.myproject.config.TestRunnerConfiguration;
import com.example.myproject.entities.Maintenance;

/**
 * doesnt work atm
 * 
 * @author jbwtan
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@ActiveProfiles("batchtest")
@SpringApplicationConfiguration(classes = {TestRunnerConfiguration.class, ApplicationConfiguration.class})

/**
 * In order to test individual steps, they MUST be wired up in your flow object
 * @author jbwtan
 *
 */
public class UnitTestExample {

	@Test
	public void test() {

	}

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;


	@Autowired
	@Qualifier("PlannedItemReader")
	public ItemReader<Maintenance> reader;
    
    @Test
    public void testStep1() throws Exception {
        JobExecution jobExecution = jobLauncherTestUtils.launchStep("plannedStep");
        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
    }
    
    @Test
    public void testStep2() throws Exception {
        JobExecution jobExecution = jobLauncherTestUtils.launchStep("unplannedStep");
    	assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
    }
    
    @Test
    public void testJob() throws Exception{
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
    	assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
    }
    
    @Test
    public void testReader() throws Exception {
       ((ItemStream) reader).open(new ExecutionContext());

       System.out.println(reader.read());
   }


	
//	@Test
//	public void testRead() throws Exception {
//		reader.setResource(new FileSystemResource("H3GPlannedOutageFeed_X_0.csv"));
//	    reader.open(MetaDataInstanceFactory.createStepExecution().getExecutionContext());
//
////		// provide ExecutionContext necessary to do read operation
//
//		// match read item to expected item
//		//StoreItem expectedStore = new StoreItem("101", "Mango", "Broadway 500", "Shop Nr.1.", "1");
////		assertThat(reader.read(), samePropertyValuesAs(expectedStore));
//	}
}
