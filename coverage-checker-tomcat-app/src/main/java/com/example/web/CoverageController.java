package com.example.web;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.Coverage;
import com.example.domain.Maintenance;
import com.example.repository.CoverageRepository;

@RestController("coverageControllerV1")
@RequestMapping("/v1/")
public class CoverageController {
	
	@Inject
	public CoverageRepository coverageRepository;
	@RequestMapping(value = "/getcoverage", method = RequestMethod.GET)
	public ResponseEntity<Iterable<Coverage>> getAllPolls(@RequestParam("postcode") String postcode) {
		Iterable<Coverage> allPolls = coverageRepository.findByPostcode(postcode);
		return new ResponseEntity<Iterable<Coverage>>(allPolls, HttpStatus.OK);
	}
}
