package com.treatwell.immutables.styles.features;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CanPassNullForOptionalEmptyInBuilder implements ComplexStyleFeature {

    private final Consumer<String> setter;
    private final Supplier<Optional<String>> getter;

    public CanPassNullForOptionalEmptyInBuilder(Consumer<String> setter, Supplier<Optional<String>> getter) {
        this.setter = setter;
        this.getter = getter;
    }

    @Override
    public String getHumanReadableFeatureName() {
        return "Can pass null as value for Optional field and treat it as Optional#empty()";
    }

    @Override
    public void additionalCheckWithImplementation() {
        setter.accept(null);
        assertThat(getter.get()).withFailMessage(
                "Giving null as value in builder should have mapped to an empty optional."
        ).isEmpty();
    }

}
