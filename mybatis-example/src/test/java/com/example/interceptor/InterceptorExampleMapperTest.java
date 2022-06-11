package com.example.interceptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class InterceptorExampleMapperTest {

	@Autowired
	InterceptorExampleMapper mapper;

	@MockBean
	LocalDateTimeSupplier localDateTimeSupplier;

	@Test
	void test() {
		LocalDateTime ldt1 = LocalDateTime.parse("2022-06-11T21:00");
		LocalDateTime ldt2 = LocalDateTime.parse("2022-06-11T21:30");
		when(localDateTimeSupplier.now()).thenReturn(ldt1, ldt2);

		Table6 model = new Table6();
		model.setId(1);
		model.setName("foo");

		// insert前はnull
		assertNull(model.getCreatedAt());
		assertNull(model.getUpdatedAt());

		mapper.insert(model);

		// insert時にInterceptorによって値がセットされる
		assertEquals(ldt1, model.getCreatedAt());
		assertEquals(ldt1, model.getUpdatedAt());

		List<Table6> models = mapper.selectAll();
		assertEquals(List.of(model), models);

		// nameを変更してupdate
		model.setName("bar");
		mapper.update(model);

		// update時はInterceptorによってupdatedAtのみ値がセットされる
		assertEquals(ldt1, model.getCreatedAt());
		assertEquals(ldt2, model.getUpdatedAt());

		models = mapper.selectAll();
		assertEquals(List.of(model), models);
	}
}
