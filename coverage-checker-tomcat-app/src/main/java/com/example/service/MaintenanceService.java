package com.example.service;

import java.util.Date;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.example.domain.Coverage;
import com.example.domain.Maintenance;
import com.example.dto.CoverageResult;
import com.example.dto.Headline;
import com.example.dto.MaintenanceMessage;
import com.example.repository.CoverageRepository;
import com.example.repository.MaintenanceRepository;

/**
 * Business logic here
 * 
 * @author Jason
 *
 */
@Service
public class MaintenanceService {

	@Inject
	private CoverageRepository coverageRepository;

	@Inject
	private MaintenanceRepository maintenanceRepository;

	@Inject
	private MessageSource ms;

	private static final Logger logger = LoggerFactory.getLogger(MaintenanceService.class);

	public String getMessage(String key) {
		return ms.getMessage(key, null, null);
	}

	// responsible for mapping entities into dto's
	public CoverageResult getMaintenanceResult(String postcode) {

		Iterable<Maintenance> maintenanceList = findCurrentIssues(postcode);
		Coverage coverage = findCoverageByPostcode(postcode);

		CoverageResult result = new CoverageResult();
		MaintenanceMessage maintenanceMessage = new MaintenanceMessage(ms);
		Headline threegHeadline = new Headline();
		Headline fourgHeadline = new Headline();
		setAvailableAndBodytext(threegHeadline, ms, coverage.getThreeg(), "3G");
		setAvailableAndBodytext(fourgHeadline, ms, coverage.getFourg(), "4G");

		result.setMaintenanceMessage(maintenanceMessage);
		result.setDeviceSpeed(coverage);
		result.setThreeg(threegHeadline);
		result.setFourg(fourgHeadline);
		sortAndSetPlannedUnplannedMaintenance(result, ms, maintenanceList);

		return result;
	}
	public void sortAndSetPlannedUnplannedMaintenance(CoverageResult result, MessageSource ms, Iterable<Maintenance> maintenanceList){
		Date currentDate = new Date();
		for (Maintenance maintenance : maintenanceList) {
			if (maintenance.getOutageType().equals("Planned")) {
				result.getMaintenancePlannedResults().add(maintenance);
				// extract start date / end dates only if start Dates = present
				// date or after
				if (maintenance.getStartDate().compareTo(currentDate) >= 0) {
					StringUtils.replaceOnce(ms.getMessage("CoverageCheckerLTEMaintenanceOnce", null, null),
							"--STARTDATE--", maintenance.getStartDate().toString());
				}

			} else if (maintenance.getOutageType().equals("Unplanned")) {
				result.getMaintenanceUnplannedResults().add(maintenance);
			}
		}
	}
	public void setAvailableAndBodytext(Headline headline, MessageSource ms, int coverage, String coverageType) {
		if (coverage > 0 && coverage < 100) {
			headline.setAvailable(true);
			headline.setHeadline(null);
			headline.setBodytext(ms.getMessage("CoverageCheckerLTE" + coverageType + "Excellent.bodyText", null,
					null));
		}
	}

	public Iterable<Maintenance> findCurrentIssues(String postcode) {
		return maintenanceRepository.findCurrentIssues(postcode);
	}

	public Coverage findCoverageByPostcode(String postcode) {
		Coverage coverage = coverageRepository.findOne(postcode);
		Coverage noCoverage = new Coverage();
		noCoverage.setFourg(-1);
		noCoverage.setL800(-1);
		noCoverage.setThreeg(-1);
		return coverage == null ? noCoverage : coverage;
	}

	// TODO extract data and formulate response?
	// pull fatwire copy

	// public ArrayList<Calendar[]> processDates(final
	// List<MaintenanceSearchResult> results,
	// String outageType) {
	// ArrayList<Calendar[]> plannedMaintenance = new ArrayList<Calendar[]>();
	//
	// Calendar currentTime = Calendar.getInstance();
	// currentTime.set(Calendar.HOUR_OF_DAY, 0);
	// currentTime.set(Calendar.MINUTE, 0);
	// currentTime.set(Calendar.SECOND, 0);
	// currentTime.set(Calendar.MILLISECOND, 0);
	//
	// Iterator<MaintenanceSearchResult> iteratorMaintenance =
	// results.iterator();
	// while (iteratorMaintenance.hasNext()) {
	// MaintenanceSearchResult res = iteratorMaintenance.next();
	// LoggingHelper.debug("LTECoverageChecker - EXISTING LOGIC: " +
	// res.hashCode());
	// if (res.getOutageType() != null &&
	// res.getOutageType().equalsIgnoreCase(outageType)) {
	// Calendar startDate = Calendar.getInstance();
	// startDate.clear();
	// Calendar endDate = Calendar.getInstance();
	// endDate.clear();
	//
	// startDate.setTime(res.getStartDate().getTime());
	// endDate.setTime(res.getFinishDate().getTime());
	// LoggingHelper.debug("Calling compare ");
	// if (endDate.after(currentTime) || endDate.equals(currentTime)) {
	// LoggingHelper.debug("ADDING to plannedMaintenance : " + res.hashCode());
	// Calendar[] cal = {startDate, endDate};
	// plannedMaintenance.add(cal);
	// }
	// }
	// }
	// LoggingHelper.debug("SIZE : " + plannedMaintenance.size());
	// return plannedMaintenance;
	// }
}
