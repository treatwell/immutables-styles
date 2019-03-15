package com.treatwell.immutables.styles;

import static com.treatwell.immutables.styles.constraints.StyleConstraints.HAS_PRIVATE_NO_ARG_CONSTRUCTOR;

import java.util.Collection;
import java.util.Optional;

import org.immutables.value.Value.Immutable;
import org.junit.runners.Parameterized.Parameters;

public class DefaultStyleTest extends StyleConstraintsTest {

    @Parameters(name = "{0}")
    public static Collection<Object[]> getConstraints() {
        return prepareParameters(HAS_PRIVATE_NO_ARG_CONSTRUCTOR);
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

        Optional<String> getOptionalString();

    }

}
