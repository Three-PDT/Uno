package com.example.myproject.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.example.myproject.entities.Record;

public class CustomItemProcessor implements
		ItemProcessor<Record, Record> {

	private static final Logger logger = LoggerFactory.getLogger(CustomItemProcessor.class);
	private long count = 1L;
	@Override
	public Record process(final Record pojo) throws Exception {
		//one-one
		System.out.println("Processing pojo:"+pojo+":"+count++);
		return pojo;
	}

}