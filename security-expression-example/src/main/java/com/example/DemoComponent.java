package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Component;

@Component
public class DemoComponent {

	private static final Logger logger = LoggerFactory.getLogger(DemoComponent.class);

	@PreAuthorize("hasRole('FOO')")
	public void role() {
	}

	@PreAuthorize("hasAnyRole('FOO', 'BAR')")
	public void anyRole() {
	}

	@PreAuthorize("hasAuthority('FOO')")
	public void authority() {
	}

	@PreAuthorize("hasAnyAuthority('FOO', 'BAR')")
	public void anyAuthority() {
	}

	@PreAuthorize("#fooArg == 'foo'")
	public void preAuthorize(String fooArg) {
	}

	@PostAuthorize("returnObject == 'foo'")
	public String postAuthorize(String a) {
		return a;
	}

	@PreFilter("this.filter(filterObject)")
	public List<String> preFilter(List<String> a) {
		logger.info("{}", a);
		return a;
	}

	@PreFilter(filterTarget = "b", value = "this.filter(filterObject)")
	public List<String> preFilter(List<String> a, List<String> b) {
		logger.info("{} {}", a, b);
		return Stream.concat(a.stream(), b.stream()).collect(Collectors.toList());
	}

	@PostFilter("this.filter(filterObject)")
	public List<String> postFilter(List<String> a) {
		logger.info("{}", a);
		// フィルタリングのためミュータブルなコレクションを返す必要がある
		return new ArrayList<>(a);
	}

	public boolean filter(String a) {
		return a.startsWith("b");
	}
}
