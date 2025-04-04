package com.example;

public class SpeciesHolder {
    private static final ThreadLocal<String> values = new ThreadLocal<>();

    public static void set(String species) {
        values.set(species);
    }

    public static String get() {
        return values.get();
    }
}
