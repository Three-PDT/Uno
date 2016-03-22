package com.example.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;

import com.example.CustomTestAnnotation;
import com.example.domain.Coverage;
import com.example.repository.CoverageRepository;
import com.example.repository.MaintenanceRepository;
import com.example.service.MaintenanceService;
@CustomTestAnnotation
public class MaintenanceServiceTest {

    @InjectMocks
    private MaintenanceService maintenanceService;
    
    @Mock
    private MaintenanceRepository maintenanceRepository;
    
    @Mock
    private CoverageRepository coverageRepository;
    
    @Mock
    private MessageSource ms;
    
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

	}
	
	@Test
	public void testCoverageNotFound() throws Exception {
		Coverage coverage = new Coverage();
		coverage.setPostcode("POSTCODE");
		coverage.setL800(-1);
		coverage.setFourg(-1);
		coverage.setThreeg(-1);
		//when(ms.getMessage(Mockito.anyString(), Mockito.any(Object[].class), Mockito.any(Locale.class)))
	    //.thenReturn("");
		when(coverageRepository.findOne("POSTCODE")).thenReturn(coverage);
		assertEquals(maintenanceService.findCoverageByPostcode("POSTCODE"),coverage);
	}
    
}
