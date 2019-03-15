package com.treatwell.immutables.styles.features;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultImmutablesNamingStrategy implements StyleFeature {

    private static final String PREFIX = "Immutable";

    @Override
    public String getHumanReadableFeatureName() {
        return "Uses the default naming stratey (Xyz -> ImmutableXyz)";
    }

    @Override
    public void assertFeature(Class<?> style, Class<?> annotated, Class<?> generated) {
        assertThat(generated.getSimpleName()).isEqualTo(PREFIX + annotated.getSimpleName());
    }

}
