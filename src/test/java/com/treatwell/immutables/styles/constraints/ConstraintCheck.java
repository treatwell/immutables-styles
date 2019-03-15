package com.treatwell.immutables.styles.constraints;

@FunctionalInterface
public interface ConstraintCheck {

    void checkConstraint(Class<?> styleClass, Class<?> annotated, Class<?> generated);

}
