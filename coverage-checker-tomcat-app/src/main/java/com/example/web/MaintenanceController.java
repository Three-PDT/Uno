package com.example.web;

import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.Maintenance;
import com.example.dto.CoverageResult;
import com.example.exception.Validator;
import com.example.repository.MaintenanceRepository;
import com.example.service.MaintenanceService;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * controllers contain web-framework infrastructure code
 * 
 * @author Jason
 *
 */
@RestController("maintenanceControllerV1")
@RequestMapping("/v1/")
public class MaintenanceController {

	@Inject
	private MaintenanceRepository maintenanceRepository;

	@Inject
	private MaintenanceService maintenanceService;

	private static final Logger logger = LoggerFactory.getLogger(MaintenanceService.class);

	// http://www.three.co.uk/maintenance/coveragecheckerlte?text=json&postcode=SE1%204LS&device_speed=volte&device_name=Galaxy%20S6
	// http://localhost:8080/v1/maintenance?postcode=SE1%204LS&device_speed=volte&device_name=Galaxy%20S6
	@RequestMapping(value = "/maintenance", method = RequestMethod.GET)
	@ApiOperation(value = "Computes the results of a given Poll", response = CoverageResult.class)
	public ResponseEntity<CoverageResult> computeResult(@Valid @RequestParam String postcode
			) {
		logger.info("in /maintenance");
		Validator.matchPostcode(postcode);
		CoverageResult result = maintenanceService.getMaintenanceResult(postcode);

		return new ResponseEntity<CoverageResult>(result, HttpStatus.OK);
	}

//	@RequestMapping(value = "/maintenance", method = RequestMethod.GET)
//	@ApiOperation(value = "Computes the results of a given Poll", response = CoverageResult.class)
//	public ResponseEntity<CoverageResult> computeResult(@Valid @RequestParam String postcode,
//			@RequestParam(value = "device_speed", required = false) String deviceSpeed,
//			@RequestParam(value = "device_name", required = false) String deviceName) {
//		logger.info("in /maintenance");
//		Validator.matchPostcode(postcode);
//		CoverageResult result = maintenanceService.computeResult(postcode, deviceSpeed, deviceName);
//
//		return new ResponseEntity<CoverageResult>(result, HttpStatus.OK);
//	}	
	
	// http://localhost:8080/v1/getmaintenances?id=164995
	@RequestMapping(value = "/getmaintenances", method = RequestMethod.GET)
	public ResponseEntity<Iterable<Maintenance>> getAllPolls(@RequestParam("id") String maintenanceId) {
		logger.info("inside get all polls");
		logger.info(maintenanceId);
		Iterable<Maintenance> allPolls = maintenanceRepository.findByMaintenanceId(maintenanceId);
		return new ResponseEntity<Iterable<Maintenance>>(allPolls, HttpStatus.OK);
	}

	// http://localhost:8080/v1/test?postcode=AB21%209BU
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public ResponseEntity<Iterable<Maintenance>> testEndPoint(@RequestParam("postcode") String postcode) {
		logger.info("inside test endpoint");
		Iterable<Maintenance> allPolls = maintenanceRepository.findCurrentIssues(postcode);
		// 1.loop maintenance results
		// 2.extract date from maintenance object for maintenance json object
		// 3.extract planned maintenance objects and put into maintenancePlanned
		// json array
		// 4. extract unplanned maintenance objects and put into
		// maintenanceUnplanned json array

		// X. this copy is generating from fatwire. so generate maintenance
		// message object anytime
		// "maintenanceMessage": {
		// "currentPlannedMessage": "We'll be carrying out crucial works on
		// <em>--STARTDATE--</em>. We're really sorry if they affect your
		// services - they should only last six to eight hours. Once they're
		// done, you'll enjoy a better network experience.",
		// "forecastPlannedMessage": "We're planning some network improvements
		// in your area on <em>--STARTDATE--</em>. We're really sorry if this
		// affects your services. It's crucial work that will improve your
		// network experience.",
		// "resolvedPlannedMessage": "We carried out essential works in your
		// area recently. We're really sorry if you couldn't use all your
		// services. Good news - everything's working fine now.",
		// "currentUnplannedMessage": "We're aware there's been a network
		// problem in your area since <em>--STARTDATE--</em>. We're really sorry
		// if this is affecting your services. We're doing all we can to fix the
		// problem. We update this information every 30 minutes so please check
		// for updates.",
		// "forecastUnplannedMessage": "We're planning some network improvements
		// in your area on <em>--STARTDATE--</em>. We're really sorry if this
		// affects your services. It's crucial work that will improve your
		// network experience.",
		// "resolvedUnplannedMessage": "Unfortunately, we had some network
		// problems in your area earlier. We're really sorry if you couldn't use
		// all your services. We've fixed the problem and are doing all we can
		// to make sure it doesn't happen again."
		// },
		return new ResponseEntity<Iterable<Maintenance>>(allPolls, HttpStatus.OK);
	}

	// http://localhost:8080/v1/testcopy?msg=coverage.checker.maintenance
	@RequestMapping(value = "/testcopy", method = RequestMethod.GET)
	public String testCopy(@RequestParam("msg") String msg) {

		return maintenanceService.getMessage(msg);
	}

	// @RequestMapping(value = "/maintenance", method = RequestMethod.POST)
	// @ApiOperation(value = "Computes the results of a given Poll", response =
	// CoverageResult.class)
	// public ResponseEntity<CoverageResult> computeResultPost(@RequestParam
	// String postcode,
	// @RequestParam(required = false) String device) {
	// return computeResult(postcode, device);
	// }

}