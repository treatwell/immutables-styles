package com.treatwell.immutables.styles.constraints;

public interface StyleConstraint {

    String getReadableConstraintName();

    void assertOnTarget(Class<?> clazz);

}
