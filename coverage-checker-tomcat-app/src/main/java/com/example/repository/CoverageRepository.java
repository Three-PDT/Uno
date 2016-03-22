package com.example.repository;

import org.springframework.data.jpa.repository.Query;

import com.example.domain.Coverage;
import com.example.domain.Maintenance;

public interface CoverageRepository extends ReadOnlyRepository<Coverage, String> {
	@Query(value="select * FROM COVERAGE_CHECKER c where c.POSTCODE = ?1", nativeQuery = true)
	public Iterable<Coverage> findByPostcode(String postcode);
}
