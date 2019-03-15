package com.treatwell.immutables.styles;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.treatwell.immutables.styles.constraints.ConstraintCheck;
import com.treatwell.immutables.styles.constraints.StyleConstraint;

@RunWith(Parameterized.class)
public abstract class StyleConstraintsTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Parameter(0)
    public String constraintName;

    @Parameter(1)
    public ConstraintCheck constraintCheck;

    @Before
    public void beforeEach() {
        logger.info("Constraint: {}", constraintName);
    }

    @Test
    public void checkConstraint() {
        constraintCheck.checkConstraint(getStyleClass(), getStyleAnnotatedClass(), getGeneratedClass());
    }

    abstract Class<?> getStyleClass();

    abstract Class<?> getStyleAnnotatedClass();

    abstract Class<?> getGeneratedClass();

    protected static Set<Object[]> prepareParameters(StyleConstraint... constraints) {
        return Arrays.stream(constraints).map(constraint -> new Object[]{
                constraint.getReadableConstraintName(),
                (ConstraintCheck) constraint::assertValid
        }).collect(Collectors.toSet());
    }

}
