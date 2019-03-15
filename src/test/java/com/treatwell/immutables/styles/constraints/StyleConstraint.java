package com.treatwell.immutables.styles.constraints;

public interface StyleConstraint {

    String getReadableConstraintName();

    void assertValid(Class<?> style, Class<?> annotated, Class<?> generated);

}
