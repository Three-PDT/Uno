package com.example.myproject.writer;

import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.batch.item.database.JpaItemWriter;

import com.example.myproject.entities.Record;

public class CustomItemWriter extends JpaItemWriter<Record> {

	@Override
	protected void doWrite(EntityManager entityManager, List<? extends Record> items) {

		for (Record record : items) {

			//System.out.println("Record:" + record);
			Query query = entityManager.createNativeQuery(
					"INSERT INTO coverage_checker(postcode,"+record.getCoverageType()+") VALUES ('"+record.getPostcode()+"',"+record.getCoverage()+")ON DUPLICATE KEY UPDATE "+record.getCoverageType()+"="+record.getCoverage()+";");
			query.executeUpdate();
		}
	}

}
