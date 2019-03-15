package com.treatwell.immutables.styles.features;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.function.Consumer;

public class StrictBuilder implements ComplexStyleFeature {

    private final Consumer<String> builderSetter;

    public StrictBuilder(Consumer<String> builderSetter) {
        this.builderSetter = builderSetter;
    }

    @Override
    public String getHumanReadableFeatureName() {
        return "Generates \"strict\" builders (can't set same value twice).";
    }

    @Override
    public void additionalCheckWithImplementation() {
        builderSetter.accept("first call");
        assertThatThrownBy(() -> builderSetter.accept("second call")).isInstanceOf(IllegalStateException.class);
    }

}
