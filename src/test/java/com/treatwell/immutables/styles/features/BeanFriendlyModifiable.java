package com.treatwell.immutables.styles.features;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class BeanFriendlyModifiable implements ComplexStyleFeature {

    private final Consumer<String> setter;
    private final Supplier<String> getter;

    public BeanFriendlyModifiable(Consumer<String> setter, Supplier<String> getter) {
        this.setter = setter;
        this.getter = getter;
    }

    @Override
    public String getHumanReadableFeatureName() {
        return "Generated-by-@Modifiable companion classes has Bean-style (setters with void return value, rather than builder-style ones)";
    }

    @Override
    public void additionalCheckWithImplementation() {
        final String value = UUID.randomUUID().toString();
        setter.accept(value);

        assertThat(getter.get()).withFailMessage(
                "The setter call should have resulted in the getter call working as expected."
        ).isEqualTo(value);
    }

}
