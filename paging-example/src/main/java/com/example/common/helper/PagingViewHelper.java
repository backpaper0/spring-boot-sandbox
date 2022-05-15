package com.example.common.helper;

import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PagingViewHelper {

	public int[] pageNumbers(Page<?> page) {
		int size = 4;
		int start = Math.max(page.getNumber() - size, page.getPageable().first().getPageNumber());
		int end = Math.min(page.getNumber() + size, page.getTotalPages() - 1);
		return IntStream.rangeClosed(start, end).toArray();
	}
}
