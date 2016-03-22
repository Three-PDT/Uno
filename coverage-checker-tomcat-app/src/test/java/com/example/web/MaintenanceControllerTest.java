package com.example.web;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.CustomTestAnnotation;
import com.example.exception.ResourceNotFoundException;
import com.example.handler.ControllerExceptionHandler;
import com.example.service.MaintenanceService;
import com.example.web.MaintenanceController;

@CustomTestAnnotation
public class MaintenanceControllerTest {

	@InjectMocks
	private MaintenanceController maintenanceController;

	@Mock
	private MaintenanceService maintenanceService;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = standaloneSetup(maintenanceController).setControllerAdvice(new ControllerExceptionHandler())
				.build();
	}

	// remember that nested method calls cannot be mocked
	@Test
	public void testCoverageNotFound() throws Exception {
		when(maintenanceService.getMaintenanceResult("HA74TS")).thenThrow(new ResourceNotFoundException());
		mockMvc.perform(get("/v1/maintenance").param("postcode", "HA74TS"))
				.andExpect(status().isNotFound())
				.andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Resource Not Found"));
	}
	
	// this won't work because no mapping found
	@Test
	public void testInvalidPostcodeRequestParam() throws Exception {
		mockMvc.perform(get("/v1/maintenance").param("postcode", "")).andExpect(status().isBadRequest());
		mockMvc.perform(get("/v1/maintenance").param("postcode", "L0LL0L")).andExpect(status().isBadRequest());		
		mockMvc.perform(get("/v1/maintenance").param("postcode", "")).andExpect(status().isBadRequest());

	}
	
}
