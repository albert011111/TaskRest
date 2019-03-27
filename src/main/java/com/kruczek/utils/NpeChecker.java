package com.kruczek.utils;

public final class NpeChecker {

    private NpeChecker() {
    }

    public static String getNpeDescription(final String fieldName) {
        return fieldName + " can't be null";
    }
}
