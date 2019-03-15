package com.treatwell.immutables.styles.constraints;

import static java.lang.reflect.Modifier.isPrivate;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

public class PrivateNoArgConstructorConstraint implements StyleConstraint {

    @Override
    public String getReadableConstraintName() {
        return "Has private no-arg constructor";
    }

    @Override
    public void assertOnTarget(Class<?> clazz) {
        final boolean noArgPrivateConstructorFound = Arrays
                .stream(clazz.getDeclaredConstructors())
                .filter(constructor -> isPrivate(constructor.getModifiers()))
                .anyMatch(constructor -> constructor.getParameterCount() == 0);

        assertThat(noArgPrivateConstructorFound)
                .withFailMessage("Did not find expected private no-arg constructor for class %s", clazz)
                .isTrue();
    }

}
