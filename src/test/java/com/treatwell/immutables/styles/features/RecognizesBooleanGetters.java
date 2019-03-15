package com.treatwell.immutables.styles.features;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toCollection;
import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class RecognizesBooleanGetters implements StyleFeature {

    @Override
    public String getHumanReadableFeatureName() {
        return "Recognizes \"is*\" methods as accessors/getters";
    }

    @Override
    public void assertFeature(Class<?> style, Class<?> annotated, Class<?> generated) {
        final Set<Method> expected = readBooleanGetters(annotated);
        assertThat(expected).withFailMessage(
                "Tested style has added support for is*-named getter methods, please ensure your annotated test class has at least one."
        ).isNotEmpty();

        final Set<Method> actual = readBooleanGetters(generated);

        actual.forEach(generatedGetter -> expected.removeIf(
                annotatedGetter -> annotatedGetter.getName().equals(generatedGetter.getName())
        ));

        assertThat(expected).isEmpty();
    }

    private Set<Method> readBooleanGetters(Class<?> clazz) {
        return stream(clazz.getDeclaredMethods()).filter(method -> method.getName().startsWith("is")).collect(toCollection(HashSet::new));
    }

}
