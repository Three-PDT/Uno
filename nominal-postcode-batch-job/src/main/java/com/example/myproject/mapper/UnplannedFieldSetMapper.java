package com.example.myproject.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.example.myproject.entities.Maintenance;

public class UnplannedFieldSetMapper implements FieldSetMapper<Maintenance> {
	//setNames(new String[]{"Status","MBNL_ID","Nominal_ID","Description","Modified_Date","Start_Service_Impact_Date","End_Service_Impact_Date","Products_List","Network_Type"});

	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
	//TODO: set reader().setNames to correct order in csv. then update fields in this mapper class by index
	@Override
	public Maintenance mapFieldSet(FieldSet fieldSet) throws BindException {
		//System.out.println("unplanned");

		Maintenance unplannedMaintenance = new Maintenance();
		unplannedMaintenance.setNominalId(fieldSet.readString("Nominal_ID"));
		try {
		unplannedMaintenance.setStartDate(dateFormat.parse(fieldSet.readString("Start_Service_Impact_Date")));
		unplannedMaintenance.setFinishDate(dateFormat.parse(fieldSet.readString("End_Service_Impact_Date")));
		unplannedMaintenance.setCreatedDate(dateFormat.parse(fieldSet.readString("Modified_Date")));
		} catch (ParseException pe){
			//TODO:
		}
		//unplannedMaintenance.setDescription(fieldSet.readString("Description"));
		unplannedMaintenance.setNetwork(fieldSet.readString("Network_Type"));
		unplannedMaintenance.setMBNL_Site_ID(fieldSet.readString("MBNL_ID"));
		unplannedMaintenance.setOutage("Unplanned");
		unplannedMaintenance.setStatus(fieldSet.readString("Status"));
		unplannedMaintenance.setService(fieldSet.readString("Products_List")); //Products_List
		unplannedMaintenance.setReference(fieldSet.readString("PRID - MBNL"));
		return unplannedMaintenance;
	}

}