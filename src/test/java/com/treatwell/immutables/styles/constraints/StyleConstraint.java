package com.treatwell.immutables.styles.constraints;

public interface StyleConstraint {

    String getReadableConstraintName();

    void assertValid(Class<?> annotated, Class<?> generated);

}
