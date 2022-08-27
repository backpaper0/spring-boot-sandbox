package com.example.misc;

import org.springframework.batch.item.ItemProcessor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CurrentAndPrevItemProcessor implements ItemProcessor<Integer, Integer> {

	private Integer prevItem;

	@Override
	public Integer process(Integer item) throws Exception {

		log.info("previous = {}, current = {}", prevItem, item);

		if (prevItem != null && prevItem > item) {
			throw new Exception("ERROR!");
		}

		prevItem = item;

		return item;
	}
}
