package com.example.interceptor;

public class ClassNameHolder {

	private static ThreadLocal<String> classNames = new ThreadLocal<>();

	public static String get() {
		return classNames.get();
	}

	public static void set(String className) {
		classNames.set(className);
	}

	public static void remove() {
		classNames.remove();
	}
}
