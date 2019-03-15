package com.treatwell.immutables.styles;

import static com.treatwell.immutables.styles.constraints.StyleConstraints.HAS_PRIVATE_NO_ARG_CONSTRUCTOR;

import java.util.Optional;
import java.util.Set;

import org.assertj.core.util.Sets;
import org.immutables.value.Value.Immutable;

import com.treatwell.immutables.styles.constraints.StyleConstraint;

public class DefaultStyleTest extends StyleConstraintsTest {

    @Override
    Set<StyleConstraint> getConstraintsForTestedStyle() {
        return Sets.newLinkedHashSet(
                HAS_PRIVATE_NO_ARG_CONSTRUCTOR
        );
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
