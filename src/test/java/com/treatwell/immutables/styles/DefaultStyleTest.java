package com.treatwell.immutables.styles;

import static com.treatwell.immutables.styles.features.StyleFeatures.CAN_PASS_NULL_FOR_OPTIONAL_FIELD;
import static com.treatwell.immutables.styles.features.StyleFeatures.HAS_PRIVATE_NO_ARG_CONSTRUCTOR;
import static com.treatwell.immutables.styles.features.StyleFeatures.RECOGNIZES_BOOLEAN_GETTERS;
import static com.treatwell.immutables.styles.features.StyleFeatures.SERIALIZABLE_BY_JACKSON;

import java.util.Collection;
import java.util.Optional;

import org.immutables.value.Value.Immutable;
import org.junit.runners.Parameterized.Parameters;

import com.treatwell.immutables.styles.features.StyleFeature;

public class DefaultStyleTest extends StyleFeaturesTest {

    @Parameters(name = "{0}")
    public static Collection<Object[]> expectedFeatures() {
        return supportsFeatures(
                HAS_PRIVATE_NO_ARG_CONSTRUCTOR,
                RECOGNIZES_BOOLEAN_GETTERS,
                SERIALIZABLE_BY_JACKSON,
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
        return CAN_PASS_NULL_FOR_OPTIONAL_FIELD.apply(
                builder::optionalString,
                () -> builder.build().getOptionalString()
        );
    }

}
