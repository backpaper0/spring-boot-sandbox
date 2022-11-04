package com.example.demo.component;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

class ComponentInterfaceTest {

	@SpringBootTest
	static class MockTest {

		@Autowired
		ComponentInterface component;
		@Autowired
		List<ComponentInterface> components;

		/**
		 * プロファイルを指定しなければdefaultプロファイルにフォールバックされる。
		 * defaultプロファイルだとモック実装が選ばれる。
		 *
		 */
		@Test
		void get() {
			assertEquals("mock", component.get());
		}

		/**
		 * コンポーネント管理されるのはモック実装のみ。
		 * プロダクション実装は無視される。
		 *
		 */
		@Test
		void testName() {
			assertEquals(1, components.size());
		}
	}

	@SpringBootTest
	@ActiveProfiles("production")
	static class ImplTest {

		@Autowired
		ComponentInterface component;
		@Autowired
		List<ComponentInterface> components;

		/**
		 * defaultプロファイル以外だとプロダクション実装が選ばれる。
		 *
		 */
		@Test
		void get() {
			assertEquals("impl", component.get());
		}

		/**
		 * コンポーネント管理されるのはプロダクション実装のみ。
		 * モック実装は無視される。
		 *
		 */
		@Test
		void testName() {
			assertEquals(1, components.size());
		}
	}
}
