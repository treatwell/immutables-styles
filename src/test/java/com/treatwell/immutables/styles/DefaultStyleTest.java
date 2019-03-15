package com.treatwell.immutables.styles;

import java.util.Optional;

import org.immutables.value.Value.Immutable;
import org.junit.Test;

import com.treatwell.immutables.styles.constraints.StyleConstraints;

public class DefaultStyleTest {

    private static final Class<?> GENERATED_CLASS = ImmutableDefaultStyleBased.class;

    @Test
    public void hasPrivateNoArgConstructor() {
        StyleConstraints.HAS_PRIVATE_NO_ARG_CONSTRUCTOR.assertOnTarget(GENERATED_CLASS);
    }

    @Immutable
    @DefaultStyle
    interface DefaultStyleBased {

        Optional<String> getOptionalString();

    }

}
