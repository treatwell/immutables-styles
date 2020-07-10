package com.treatwell.immutables.styles;

import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

public final class StyleFeaturesArgumentProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        StyleFeaturesTest<?, ?, ?> testInstance = (StyleFeaturesTest<?, ?, ?>) context.getRequiredTestInstance();

        Class<?> styleClass = testInstance.getStyleClass();
        Class<?> annotatedClass = testInstance.getAnnotatedClass();
        Class<?> generatedClass = testInstance.getGeneratedClass();

        return testInstance
            .getExpectedStyleFeatures()
            .stream()
            .map(feature -> Arguments.of(
                feature.getHumanReadableFeatureName(),
                feature,
                styleClass,
                annotatedClass,
                generatedClass)
            );
    }

}
