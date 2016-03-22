package com.example.myproject.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.example.myproject.entities.Maintenance;

public class CustomItemProcessor implements
		ItemProcessor<Maintenance, Maintenance> {

	private static final Logger logger = LoggerFactory.getLogger(CustomItemProcessor.class);
	private long count = 1L;
	@Override
	public Maintenance process(final Maintenance pojo) throws Exception {
		//one-one
		System.out.println("Processing Pojo:"+count++);
		return pojo;
	}

}