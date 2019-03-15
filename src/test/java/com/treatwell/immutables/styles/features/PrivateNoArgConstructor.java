package com.treatwell.immutables.styles.features;

import static java.lang.reflect.Modifier.isPrivate;
import static java.util.Arrays.stream;
import static org.assertj.core.api.Assertions.assertThat;

public class PrivateNoArgConstructor implements StyleFeature {

    @Override
    public String getHumanReadableFeatureName() {
        return "Has private no-arg constructor";
    }

    @Override
    public void assertFeature(Class<?> style, Class<?> annotated, Class<?> generated) {
        final boolean annotatedHasConstructor = isNoArgPrivateConstructorFound(annotated);
        final boolean generatedHasConstructor = isNoArgPrivateConstructorFound(generated);

        assertThat(annotatedHasConstructor).withFailMessage(
                "Did not expect a private no-arg constructor in annotated class (should have been interface!): %s",
                annotated
        ).isFalse();

        assertThat(generatedHasConstructor).withFailMessage(
                "Did not find expected private no-arg constructor for class %s generated from %s",
                generated,
                annotated
        ).isTrue();
    }

    private boolean isNoArgPrivateConstructorFound(Class<?> generated) {
        return stream(generated.getDeclaredConstructors())
                .filter(constructor -> isPrivate(constructor.getModifiers()))
                .anyMatch(constructor -> constructor.getParameterCount() == 0);
    }

}
