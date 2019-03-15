package com.treatwell.immutables.styles;

import java.lang.invoke.MethodHandles;
import java.util.Collection;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.treatwell.immutables.styles.constraints.StyleConstraint;

@RunWith(Parameterized.class)
public abstract class StyleConstraintsTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(StyleConstraintsTest.class);

    @Parameters(name = "{0}")
    public static Collection<Object[]> getConstraints() {
        return prepareParameters();
    }

    @Parameter(0)
    public String constraintName;

    @Parameter(1)
    public Consumer<Class<?>> constraintCheck;

    @Test
    public void contraintIsFulfilled() {
        constraintCheck.accept(getGeneratedClass());
    }

    abstract Set<StyleConstraint> getConstraintsForTestedStyle();

    abstract Class<?> getGeneratedClass();

    private static Set<Object[]> prepareParameters() {
        final Collection<StyleConstraint> constraintsToTest = loadConstraints();
        return constraintsToTest.stream().map(constraint -> new Object[]{
                constraint.getReadableConstraintName(),
                (Consumer<Class<?>>) constraint::assertOnTarget
        }).collect(Collectors.toSet());
    }

    private static Collection<StyleConstraint> loadConstraints() {
        final StyleConstraintsTest testClass = buildPreTestInstance();
        return testClass.getConstraintsForTestedStyle();
    }

    private static StyleConstraintsTest buildPreTestInstance() {
        final Class<?> testClass = MethodHandles.lookup().lookupClass();
        try {
            return (StyleConstraintsTest) testClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
