package com.example.hierarchicalroles;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.core.authority.AuthorityUtils;

class RoleHierarchyTest {

	RoleHierarchy sut;

	@BeforeEach
	void init() {
		RoleHierarchyImpl sut = new RoleHierarchyImpl();
		sut.setHierarchy("""
				A > B > C

				D > E
				D > F

				G > H
				H > I
				""");
		this.sut = sut;
	}

	@ParameterizedTest
	@CsvSource(delimiter = '|', value = {
			"A | A, B, C",
			"D | D, E, F",
			"G | G, H, I",
	})
	void test(String explicitAuthorityAsString, String expectedAuthoritiesAsString) throws Exception {
		var explicitAuthorities = AuthorityUtils.createAuthorityList(explicitAuthorityAsString);
		var actualAuthorities = sut.getReachableGrantedAuthorities(explicitAuthorities);
		var expectedAuthorities = AuthorityUtils.createAuthorityList(expectedAuthoritiesAsString.split("\\s*,\\s*"));
		assertEquals(expectedAuthorities, actualAuthorities);
	}
}
