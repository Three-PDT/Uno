package com.example.web;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.example.CoverageCheckerApplication;

/**
 * This should access an empty database and tear up, tear down.
 * @author jbwtan
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CoverageCheckerApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class MaintenanceControllerIT {

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = webAppContextSetup(webApplicationContext).build();
	}
	@Test
	public void testGetDepartment() throws Exception {
//		mockMvc.perform(get("/v1/departments/1")).andExpect(status().isOk())
//				.andExpect(jsonPath("$.id", is(1)))
//				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
//				.andExpect(jsonPath("$.name", is("DEPT1")));

	}
}
