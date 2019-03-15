package com.treatwell.immutables.styles;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.treatwell.immutables.styles.constraints.StyleConstraint;

@RunWith(Parameterized.class)
public abstract class StyleConstraintsTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(StyleConstraintsTest.class);

    @Parameter(0)
    public String constraintName;

    @Parameter(1)
    public Consumer<Class<?>> constraintCheck;

    @Test
    public void checkConstraint() {
        constraintCheck.accept(getGeneratedClass());
    }

    abstract Class<?> getGeneratedClass();

    protected static Collection<Object[]> constraints(StyleConstraint... constraints) {
        return prepareParameters(constraints);
    }

    private static Set<Object[]> prepareParameters(StyleConstraint[] constraints) {
        return Arrays.stream(constraints).map(constraint -> new Object[]{
                constraint.getReadableConstraintName(),
                (Consumer<Class<?>>) constraint::assertOnTarget
        }).collect(Collectors.toSet());
    }

}
