package com.example.numbering;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "example.date.fixed-value=20220807")
public class NumberingTest {

	@Autowired
	Numbering sut;

	@Test
	void testA() {
		assertEquals("00000000000000000001", sut.numberingA());
		assertEquals("00000000000000000002", sut.numberingA());
		assertEquals("00000000000000000003", sut.numberingA());
	}

	@Test
	void testB() {
		assertEquals("00001", sut.numberingB());
		assertEquals("00002", sut.numberingB());
		assertEquals("00003", sut.numberingB());
	}

	@Test
	void testC() {
		assertEquals("202208070001", sut.numberingC());
		assertEquals("202208070002", sut.numberingC());
		assertEquals("202208070003", sut.numberingC());
	}

	@Test
	void testD() {
		assertEquals("202208070001", sut.numberingD());
		assertEquals("202208070002", sut.numberingD());
		assertEquals("202208070003", sut.numberingD());
	}

	@Test
	void testE() {
		assertEquals(1L, sut.numberingE());
		assertEquals(2L, sut.numberingE());
		assertEquals(3L, sut.numberingE());
	}

	@Test
	void testF() {
		assertEquals(1, sut.numberingF());
		assertEquals(2, sut.numberingF());
		assertEquals(3, sut.numberingF());
	}
}
