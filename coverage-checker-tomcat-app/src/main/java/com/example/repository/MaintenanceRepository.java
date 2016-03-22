package com.example.repository;

import org.springframework.data.jpa.repository.Query;

import com.example.domain.Maintenance;


public interface MaintenanceRepository extends ReadOnlyRepository<Maintenance, Long> {
	/**
	 * Params here not treated as pure strings, so safe from SQL injection
	 */
	@Query(value="SELECT pm.PLANNED_MAINTENANCE_ID, pm.START_DATE, pm.FINISH_DATE, pm.outage, pm.network, pm.status, pm.BREACH_ID FROM NOMINAL_POSTCODE np INNER JOIN PLANNED_MAINTENANCE pm ON np.NOMINAL_ID = pm.NOMINAL_ID WHERE pm.FINISH_DATE >= (CURRENT_DATE-900) AND np.POSTCODE_ID = ?1", nativeQuery = true)
	public Iterable<Maintenance> findCurrentIssues(String postcode);
	
	@Query(value="select * FROM PLANNED_MAINTENANCE pm where pm.PLANNED_MAINTENANCE_ID = ?1", nativeQuery = true)
	public Iterable<Maintenance> findByMaintenanceId(String maintenanceId);
}