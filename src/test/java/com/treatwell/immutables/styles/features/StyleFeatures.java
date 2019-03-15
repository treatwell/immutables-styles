package com.treatwell.immutables.styles.features;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class StyleFeatures {

    public static final StyleFeature HAS_PRIVATE_NO_ARG_CONSTRUCTOR = new PrivateNoArgConstructor();
    public static final StyleFeature RECOGNIZES_BOOLEAN_GETTERS = new RecognizesBooleanGetters();
    public static final StyleFeature SERIALIZABLE_BY_JACKSON = new SerializableByJackson();

    public static final BiFunction<Consumer<String>, Supplier<Optional<String>>, StyleFeature> CAN_PASS_NULL_FOR_OPTIONAL_FIELD = CanPassNullForOptionalFieldInBuilder::new;

    private StyleFeatures() {
    }

}
