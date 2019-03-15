package com.treatwell.immutables.styles.features;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Modifier;

public class GeneratedClassHasPublicVisibility implements StyleFeature {

    @Override
    public String getHumanReadableFeatureName() {
        return "Generated class has public visibility";
    }

    @Override
    public void assertFeature(Class<?> style, Class<?> annotated, Class<?> generated) {
        assertThat(Modifier.isPublic(generated.getModifiers())).withFailMessage(
                "Expected the generated class %s to have public visibility!",
                generated.getName()
        ).isTrue();
    }

}
