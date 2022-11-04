package com.example.multitm.component;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional("fooTransactionManager")
public class Foo {

	public void foo() {
	}
}