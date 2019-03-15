package com.treatwell.immutables.styles.features;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class SerializableByJackson implements StyleFeature {

    @Override
    public String getHumanReadableFeatureName() {
        return "All generated instances are serializable by Jackson";
    }

    @Override
    public void assertFeature(Class<?> style, Class<?> annotated, Class<?> generated) {
        assertThat(style.getAnnotation(JsonSerialize.class)).withFailMessage(
                "Styles that guarantee serializability by Jackson must be annotated with @JsonSerialize!"
        ).isNotNull();
    }

}
