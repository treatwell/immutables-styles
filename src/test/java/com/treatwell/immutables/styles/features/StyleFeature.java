package com.treatwell.immutables.styles.features;

public interface StyleFeature {

    String getHumanReadableFeatureName();

    void assertFeature(Class<?> style, Class<?> annotated, Class<?> generated);

}
