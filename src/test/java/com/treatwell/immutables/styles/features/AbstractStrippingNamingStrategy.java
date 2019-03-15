package com.treatwell.immutables.styles.features;

import static org.assertj.core.api.Assertions.assertThat;

public class AbstractStrippingNamingStrategy implements StyleFeature {

    private static final String PREFIX = "Abstract";

    @Override
    public String getHumanReadableFeatureName() {
        return "Uses the \"Abstract\"-stripping naming strategy (AbstractXyz -> Xyz)";
    }

    @Override
    public void assertFeature(Class<?> style, Class<?> annotated, Class<?> generated) {
        assertThat(annotated.getSimpleName()).startsWith(PREFIX);
        assertThat(generated.getSimpleName()).isEqualTo(annotated.getSimpleName().replace(PREFIX, ""));
    }

}
