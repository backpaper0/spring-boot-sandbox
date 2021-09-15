package com.example;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest
public class GlobalMethodSecurityTest {

	@Autowired
	private DemoComponent sut;

	// hasRole

	@Test
	@WithMockUser(username = "testuser")
	void hasRole1() {
		assertThrows(AccessDeniedException.class, () -> sut.role());
	}

	@Test
	@WithMockUser(username = "testuser", roles = { "FOO" })
	void hasRole2() {
		sut.role();
	}

	@Test
	@WithMockUser(username = "testuser", authorities = { "FOO" })
	void hasRole3() {
		assertThrows(AccessDeniedException.class, () -> sut.role());
	}

	@Test
	@WithMockUser(username = "testuser", authorities = { "ROLE_FOO" })
	void hasRole4() {
		sut.role();
	}

	// hasAnyRole

	@Test
	@WithMockUser(username = "testuser", roles = { "FOO", "BAR", "BAZ" })
	void hasAnyRole1() {
		sut.anyRole();
	}

	@Test
	@WithMockUser(username = "testuser", roles = { "FOO" })
	void hasAnyRole2() {
		sut.anyRole();
	}

	@Test
	@WithMockUser(username = "testuser", roles = { "BAR" })
	void hasAnyRole3() {
		sut.anyRole();
	}

	@Test
	@WithMockUser(username = "testuser", roles = { "BAZ" })
	void hasAnyRole4() {
		assertThrows(AccessDeniedException.class, () -> sut.anyRole());
	}

	// hasAuthority

	@Test
	@WithMockUser(username = "testuser")
	void hasAuthority1() {
		assertThrows(AccessDeniedException.class, () -> sut.authority());
	}

	@Test
	@WithMockUser(username = "testuser", authorities = { "FOO" })
	void hasAuthority2() {
		sut.authority();
	}

	@Test
	@WithMockUser(username = "testuser", roles = { "FOO" })
	void hasAuthority3() {
		assertThrows(AccessDeniedException.class, () -> sut.authority());
	}

	// hasAnyAuthority

	@Test
	@WithMockUser(username = "testuser", authorities = { "FOO", "BAR", "BAZ" })
	void hasAnyAuthority1() {
		sut.anyAuthority();
	}

	@Test
	@WithMockUser(username = "testuser", authorities = { "FOO" })
	void hasAnyAuthority2() {
		sut.anyAuthority();
	}

	@Test
	@WithMockUser(username = "testuser", authorities = { "BAR" })
	void hasAnyAuthority3() {
		sut.anyAuthority();
	}

	@Test
	@WithMockUser(username = "testuser", authorities = { "BAZ" })
	void hasAnyAuthority4() {
		assertThrows(AccessDeniedException.class, () -> sut.anyAuthority());
	}

	// PreAuthorize

	@Test
	@WithMockUser(username = "testuser")
	void preAuthorize1() {
		assertThrows(AccessDeniedException.class, () -> sut.preAuthorize("xxx"));
	}

	@Test
	@WithMockUser(username = "testuser")
	void preAuthorize2() {
		sut.preAuthorize("foo");
	}

	// PostAuthorize

	@Test
	@WithMockUser(username = "testuser")
	void postAuthorize1() {
		assertThrows(AccessDeniedException.class, () -> sut.postAuthorize("xxx"));
	}

	@Test
	@WithMockUser(username = "testuser")
	void postAuthorize2() {
		sut.postAuthorize("foo");
	}

	// PreFilter

	@Test
	@WithMockUser(username = "testuser")
	void preFilter1() {
		List<String> arg = new ArrayList<>(List.of("foo", "bar", "baz", "qux"));
		List<String> returnValue = sut.preFilter(arg);
		assertEquals(List.of("bar", "baz"), returnValue);
		// 引数のコレクションが変更される。
		// フィルタリング後に異なるコレクションインスタンスを渡すようにするとAOPの有無で
		// 渡されるインスタンスが異なってしまうため、これは妥当な仕様だと思う。
		// その一方で、イミュータブルなコレクション(破壊的なメソッドを実装していないコレクション)を
		// 渡せないため、呼び出し元がコレクションの実装クラスを意識する必要があり微妙。
		// @see org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler.filterCollection(Collection<T>, Expression, EvaluationContext, MethodSecurityExpressionOperations)
		assertSame(arg, returnValue);
	}

	@Test
	@WithMockUser(username = "testuser")
	void preFilter2() {
		List<String> stream1 = new ArrayList<>(List.of("foo", "bar"));
		List<String> stream2 = new ArrayList<>(List.of("baz", "qux"));
		List<String> returnValue = sut.preFilter(stream1, stream2);
		assertEquals(List.of("foo", "bar", "baz"), returnValue);
	}

	@Test
	@WithMockUser(username = "testuser")
	void postFilter1() {
		List<String> arg = List.of("foo", "bar", "baz", "qux");
		List<String> returnValue = sut.postFilter(arg);
		assertEquals(List.of("bar", "baz"), returnValue);
	}
}
