package com.treatwell.immutables.styles;

import static com.treatwell.immutables.styles.features.SimpleStyleFeatures.HAS_PRIVATE_NO_ARG_CONSTRUCTOR;
import static com.treatwell.immutables.styles.features.SimpleStyleFeatures.RECOGNIZES_BOOLEAN_GETTERS;
import static com.treatwell.immutables.styles.features.SimpleStyleFeatures.SERIALIZABLE_BY_JACKSON;
import static com.treatwell.immutables.styles.features.SimpleStyleFeatures.USES_STANDARD_NAMING_STRATEGY;

import java.util.Collection;
import java.util.Optional;

import org.immutables.value.Value.Immutable;
import org.junit.runners.Parameterized.Parameters;

import com.treatwell.immutables.styles.features.CanPassNullForOptionalEmptyInBuilder;
import com.treatwell.immutables.styles.features.StyleFeature;

public class DefaultStyleTest extends StyleFeaturesTest {

    @Parameters(name = "{0}")
    public static Collection<Object[]> expectedFeatures() {
        return supportsFeatures(
                HAS_PRIVATE_NO_ARG_CONSTRUCTOR,
                RECOGNIZES_BOOLEAN_GETTERS,
                SERIALIZABLE_BY_JACKSON,
                USES_STANDARD_NAMING_STRATEGY,
                optionalPassNullFeature()
        );
    }

    @Override
    Class<?> getStyleClass() {
        return DefaultStyle.class;
    }

    @Override
    Class<?> getStyleAnnotatedClass() {
        return DefaultStyleBased.class;
    }

    @Override
    Class<?> getGeneratedClass() {
        return ImmutableDefaultStyleBased.class;
    }

    @Immutable
    @DefaultStyle
    interface DefaultStyleBased {

        boolean isSomething();

        Optional<String> getOptionalString();

    }

    private static StyleFeature optionalPassNullFeature() {
        final ImmutableDefaultStyleBased.Builder builder = ImmutableDefaultStyleBased.builder().something(true);
        return new CanPassNullForOptionalEmptyInBuilder(
                builder::optionalString,
                () -> builder.build().getOptionalString()
        );
    }

}
