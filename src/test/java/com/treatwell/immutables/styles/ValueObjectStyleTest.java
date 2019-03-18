package com.treatwell.immutables.styles;

import static com.treatwell.immutables.styles.features.SimpleStyleFeatures.ABSTRACT_STRIPPING_NAMING_STRATEGY;
import static com.treatwell.immutables.styles.features.SimpleStyleFeatures.GENERATED_CLASS_IS_PUBLIC;
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

public class ValueObjectStyleTest extends AbstractStyleFeaturesTest {

    @Parameters(name = "{0}")
    public static Collection<Object[]> expectedFeatures() {
        return supportsFeatures(
                HAS_PRIVATE_NO_ARG_CONSTRUCTOR,
                RECOGNIZES_BOOLEAN_GETTERS,
                SERIALIZABLE_BY_JACKSON,
                ABSTRACT_STRIPPING_NAMING_STRATEGY,
                GENERATED_CLASS_IS_PUBLIC,
                optionalPassNullFeature(),
                strictBuilders()
        );
    }

    @Override
    Class<?> getStyleClass() {
        return ValueObjectStyle.class;
    }

    @Override
    Class<?> getStyleAnnotatedClass() {
        return AbstractSomething.class;
    }

    @Override
    Class<?> getGeneratedClass() {
        return Something.class;
    }

    private static StyleFeature optionalPassNullFeature() {
        final Something.Builder builder = Something.builder().something(true);
        return new CanPassNullForOptionalEmptyInBuilder(
                builder::optionalString,
                () -> builder.build().getOptionalString()
        );
    }

    private static StyleFeature strictBuilders() {
        return new StrictBuilder(Something.builder()::optionalString);
    }

    @Immutable
    @ValueObjectStyle
    abstract static class AbstractSomething {

        abstract boolean isSomething();

        abstract Optional<String> getOptionalString();

    }

}
