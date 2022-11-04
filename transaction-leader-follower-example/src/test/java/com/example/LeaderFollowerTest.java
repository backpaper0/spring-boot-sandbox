package com.example;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.core.annotation.ReadOnlyTransaction;
import com.example.model.Example;
import com.example.service.Example1Service;

@SpringBootTest
public class LeaderFollowerTest {

	@Autowired
	private Example1Service service;

	/**
	 * リーダーデータソースを使用する。
	 *
	 */
	@Test
	void findAllFromLeader1() {
		List<Example> examples = service.findAllFromLeader1();
		assertEquals(List.of(new Example(1, "foo"), new Example(2, "bar")), examples);
	}

	/**
	 * フォロワーデータソースを使用する。
	 *
	 */
	@Test
	void findAllFromFollower1() {
		List<Example> examples = service.findAllFromFollower1();
		assertEquals(List.of(new Example(3, "baz"), new Example(4, "qux")), examples);
	}

	/**
	 * {@link ReadOnlyTransaction}アノテーションを利用してフォロワーデータソースを使用する。
	 *
	 */
	@Test
	void findAllFromFollower2() {
		List<Example> examples = service.findAllFromFollower2();
		assertEquals(List.of(new Example(3, "baz"), new Example(4, "qux")), examples);
	}

	/**
	 * {@link Transactional}をネストした場合、リーダーデータソースが使われる。
	 */
	@Test
	void nestFindAllFromLeader1() {
		List<Example> examples = service.nestFindAllFromLeader1();
		assertEquals(List.of(new Example(1, "foo"), new Example(2, "bar")), examples);
	}

	/**
	 * {@link Transactional}をネストした場合、ネスト側がreadOnly = trueでもリーダーデータソースが使われる。
	 */
	@Test
	void nestFindAllFromLeader2() {
		List<Example> examples = service.nestFindAllFromLeader2();
		assertEquals(List.of(new Example(1, "foo"), new Example(2, "bar")), examples);
	}

	/**
	 * {@link Transactional}をネストした場合、ネスト側が{@link ReadOnlyTransaction}アノテーションを
	 * 付けていてもリーダーデータソースが使われる。
	 */
	@Test
	void nestFindAllFromLeader3() {
		List<Example> examples = service.nestFindAllFromLeader3();
		assertEquals(List.of(new Example(1, "foo"), new Example(2, "bar")), examples);
	}
}
