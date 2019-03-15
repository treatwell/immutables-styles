package com.treatwell.immutables.styles.constraints;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class IsAlwaysSerializableByJackson implements StyleConstraint {

    @Override
    public String getReadableConstraintName() {
        return "All generated instances are serializable by Jackson";
    }

    @Override
    public void assertValid(Class<?> style, Class<?> annotated, Class<?> generated) {
        assertThat(style.getAnnotation(JsonSerialize.class)).withFailMessage(
                "Styles that guarantee serializability by Jackson must be annotated with @JsonSerialize!"
        ).isNotNull();
    }

}
