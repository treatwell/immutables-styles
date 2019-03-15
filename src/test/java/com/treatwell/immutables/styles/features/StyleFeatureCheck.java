package com.treatwell.immutables.styles.features;

@FunctionalInterface
public interface StyleFeatureCheck {

    void ensureFeature(Class<?> styleClass, Class<?> annotated, Class<?> generated);

}
