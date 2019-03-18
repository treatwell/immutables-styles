package com.treatwell.immutables.styles.features;

import static org.assertj.core.api.Assertions.assertThat;

public class AppendEventNamingStrategy implements StyleFeature {

    @Override
    public String getHumanReadableFeatureName() {
        return "Generated class' name is chosen by appending \"Event\" to annotated (Xyz -> XyzEvent)";
    }

    @Override
    public void assertFeature(Class<?> style, Class<?> annotated, Class<?> generated) {
        assertThat(generated.getSimpleName()).isEqualTo(annotated.getSimpleName() + "Event");
    }

}
