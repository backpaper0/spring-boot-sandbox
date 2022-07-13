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
	TableMetadataAutoSetInterceptor.IdSupplier idSupplier;
	@MockBean
	TableMetadataAutoSetInterceptor.LocalDateTimeSupplier localDateTimeSupplier;

	@Test
	void test() {
		String id1 = "aaa";
		String id2 = "bbb";
		when(idSupplier.get()).thenReturn(id1, id2);

		LocalDateTime ldt1 = LocalDateTime.parse("2022-06-11T21:00");
		LocalDateTime ldt2 = LocalDateTime.parse("2022-06-11T21:30");
		when(localDateTimeSupplier.get()).thenReturn(ldt1, ldt2);

		Table6 model = new Table6();
		model.setId(1);
		model.setName("foo");

		// insert前はnull
		assertNull(model.getCreatedBy());
		assertNull(model.getCreatedAt());
		assertNull(model.getUpdatedBy());
		assertNull(model.getUpdatedAt());

		mapper.insert(model);

		// insert時にInterceptorによって値がセットされる
		assertEquals(id1, model.getCreatedBy());
		assertEquals(ldt1, model.getCreatedAt());
		assertEquals(id1, model.getUpdatedBy());
		assertEquals(ldt1, model.getUpdatedAt());

		List<Table6> models = mapper.selectAll();
		assertEquals(List.of(model), models);

		// nameを変更してupdate
		model.setName("bar");
		mapper.update(model);

		// update時はInterceptorによってupdatedByとupdateAtのみ値がセットされる
		assertEquals(id1, model.getCreatedBy());
		assertEquals(ldt1, model.getCreatedAt());
		assertEquals(id2, model.getUpdatedBy());
		assertEquals(ldt2, model.getUpdatedAt());

		models = mapper.selectAll();
		assertEquals(List.of(model), models);
	}
}
