package com.example;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * http://www.three.co.uk/maintenance/coveragecheckerlte?text=json&postcode=SE1%
 * 204LS&device_speed=volte&device_name=Galaxy%20S6
 * 
 * @author jbwtan
 * 
 *         possible place to add @configuration and stick random beans here and
 *         there.
 * 
 * @SpringBootApplication contains @componentScan which will scan all lower
 *                        hierarchy packages for components to add to spring
 *                        container
 */
@SpringBootApplication
public class CoverageCheckerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoverageCheckerApplication.class, args);
	}
}