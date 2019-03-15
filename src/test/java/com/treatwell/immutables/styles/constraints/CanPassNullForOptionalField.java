package com.treatwell.immutables.styles.constraints;

public class CanPassNullForOptionalField implements StyleConstraint {

    @Override
    public String getReadableConstraintName() {
        return "Can pass null as value for Optional field and treat it as Optional#empty()";
    }

    @Override
    public void assertValid(Class<?> style, Class<?> annotated, Class<?> generated) {
        return;
    }

}
