package com.treatwell.immutables.styles;

import java.util.Arrays;
import java.util.Set;
import java.util.function.BiConsumer;
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
    public BiConsumer<Class<?>, Class<?>> constraintCheck;

    @Test
    public void checkConstraint() {
        constraintCheck.accept(getStyleAnnotatedClass(), getGeneratedClass());
    }

    abstract Class<?> getStyleClass();

    abstract Class<?> getStyleAnnotatedClass();

    abstract Class<?> getGeneratedClass();

    protected static Set<Object[]> prepareParameters(StyleConstraint... constraints) {
        return Arrays.stream(constraints).map(constraint -> new Object[]{
                constraint.getReadableConstraintName(),
                (BiConsumer<Class<?>, Class<?>>) constraint::assertValid
        }).collect(Collectors.toSet());
    }

}
