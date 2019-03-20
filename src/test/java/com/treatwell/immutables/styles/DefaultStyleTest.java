package com.treatwell.immutables.styles;

import static com.treatwell.immutables.styles.features.SimpleStyleFeatures.DEFAULT_IMMUTABLES_NAMING_STRATEGY;
import static com.treatwell.immutables.styles.features.SimpleStyleFeatures.HAS_PRIVATE_NO_ARG_CONSTRUCTOR;
import static com.treatwell.immutables.styles.features.SimpleStyleFeatures.RECOGNIZES_BOOLEAN_GETTERS;
import static com.treatwell.immutables.styles.features.SimpleStyleFeatures.SERIALIZABLE_BY_JACKSON;

import java.util.Collection;
import java.util.Optional;

import org.immutables.value.Value.Immutable;
import org.junit.runners.Parameterized.Parameters;

import com.treatwell.immutables.styles.features.CanPassNullForOptionalEmptyInBuilder;
import com.treatwell.immutables.styles.features.StrictBuilder;
import com.treatwell.immutables.styles.features.StyleFeature;

public class DefaultStyleTest extends AbstractStyleFeaturesTest {

    @Parameters(name = "{0}")
    public static Collection<Object[]> expectedFeatures() {
        return supportsFeatures(
                HAS_PRIVATE_NO_ARG_CONSTRUCTOR,
                RECOGNIZES_BOOLEAN_GETTERS,
                SERIALIZABLE_BY_JACKSON,
                DEFAULT_IMMUTABLES_NAMING_STRATEGY,
                optionalPassNullFeature(),
                strictBuilders()
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

    private static StyleFeature optionalPassNullFeature() {
        final ImmutableDefaultStyleBased.Builder builder = ImmutableDefaultStyleBased.builder().something(true);
        return new CanPassNullForOptionalEmptyInBuilder(
                builder::optionalString,
                () -> builder.build().getOptionalString()
        );
    }

    private static StyleFeature strictBuilders() {
        return new StrictBuilder(ImmutableDefaultStyleBased.builder()::optionalString);
    }

    @Immutable
    @DefaultStyle
    interface DefaultStyleBased {

        boolean isSomething();

        Optional<String> getOptionalString();

    }

}
