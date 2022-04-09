package com.example.importconfig;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.misc.Baz;
import com.example.misc.Foo;

@SpringBootTest
public class ImportConfigTest {

	@Autowired
	private Foo foo;

	/**
	 * importした設定だけが適用されていることを確認する。
	 */
	@Test
	void config1() {
		assertEquals("Foo(BarImpl1())", foo.toString());
	}

	@Autowired
	private Baz bazA;
	@Autowired
	private Baz bazB;
	@Autowired
	private Baz bazC;

	/**
	 * 複数回importされても1度しか処理されないことを確認する。
	 */
	@Test
	void config2() {
		assertEquals("Baz(A)", bazA.toString());
		assertEquals("Baz(B)", bazB.toString());
		assertEquals("Baz(C)", bazC.toString());
	}
}
