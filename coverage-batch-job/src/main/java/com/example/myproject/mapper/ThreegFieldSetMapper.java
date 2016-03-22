package com.example.myproject.mapper;


import java.text.SimpleDateFormat;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.example.myproject.entities.CoverageType;
import com.example.myproject.entities.Record;

public class ThreegFieldSetMapper implements FieldSetMapper<Record> {

	@Override
	public Record mapFieldSet(FieldSet fieldSet) throws BindException {
		Record record = new Record();
		record.setPostcode(fieldSet.readString("Postcode"));
		record.setCoverage(fieldSet.readInt("Coverage"));
		record.setCoverageType(CoverageType.THREEG);
		return record;
	}

}