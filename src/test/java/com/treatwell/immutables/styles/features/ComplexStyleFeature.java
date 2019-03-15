package com.treatwell.immutables.styles.features;

public interface ComplexStyleFeature extends StyleFeature {

    @Override
    default void assertFeature(Class<?> style, Class<?> annotated, Class<?> generated) {
        additionalCheckWithImplementation();
    }

    void additionalCheckWithImplementation();

}
