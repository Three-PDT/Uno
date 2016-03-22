package com.example.myproject.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.example.myproject.entities.Maintenance;

public class PlannedFieldSetMapper implements FieldSetMapper<Maintenance> {
	//setNames(new String[]{"Status","MBNL_ID","Nominal_ID","Description","Modified_Date","Start_Service_Impact_Date","End_Service_Impact_Date","Products_List","Network_Type"});

	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
	//TODO: set reader().setNames to correct order in csv. then update fields in this mapper class by index
	@Override
	public Maintenance mapFieldSet(FieldSet fieldSet) throws BindException {
		//System.out.println("MARKER"+Arrays.asList(fieldSet.getValues()));
		//System.out.println("MARKER"+fieldSet.readString(10)+","+fieldSet.readString(11)+","+fieldSet.readString(12)+","+fieldSet.readString(13));
		//System.out.println("planned");
		Maintenance unplannedMaintenance = new Maintenance();
		unplannedMaintenance.setNominalId(fieldSet.readString("H3G_Nominal_ID"));
		try {
		unplannedMaintenance.setStartDate(dateFormat.parse(fieldSet.readString("Planned_Outage_Start_Time")));
		unplannedMaintenance.setFinishDate(dateFormat.parse(fieldSet.readString("Planned_Outage_End_Time")));
		unplannedMaintenance.setCreatedDate(new Date());
		} catch (ParseException pe){
			//TODO:
		}
		//unplannedMaintenance.setDescription(fieldSet.readString("Description"));
		unplannedMaintenance.setNetwork(fieldSet.readString("Network_Type"));
		unplannedMaintenance.setMBNL_Site_ID(fieldSet.readString("MBNL_Site_ID"));
		unplannedMaintenance.setOutage("Planned");
		unplannedMaintenance.setStatus("Completed".equals(fieldSet.readString("Status")) ? fieldSet.readString("Status") : "Closed");
		//unplannedMaintenance.setService(fieldSet.readString("Products_List")); //Products_List
		unplannedMaintenance.setReference(fieldSet.readString("CMID"));
		return unplannedMaintenance;
	}

}