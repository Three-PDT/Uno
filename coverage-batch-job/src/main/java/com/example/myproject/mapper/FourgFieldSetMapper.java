package com.example.myproject.mapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.example.myproject.entities.CoverageType;
import com.example.myproject.entities.Record;

public class FourgFieldSetMapper implements FieldSetMapper<Record> {

	@Override
	public Record mapFieldSet(FieldSet fieldSet) throws BindException {
		Record record = new Record();
		record.setPostcode(fieldSet.readString("POSTCODE"));
		record.setCoverage(fieldSet.readInt("RANKING"));
		record.setCoverageType(CoverageType.FOURG);
		return record;
	}

}