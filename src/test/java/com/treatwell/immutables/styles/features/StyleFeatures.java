package com.treatwell.immutables.styles.features;

public final class StyleFeatures {

    public static final StyleFeature HAS_PRIVATE_NO_ARG_CONSTRUCTOR = new PrivateNoArgConstructor();
    public static final StyleFeature RECOGNIZES_BOOLEAN_GETTERS = new RecognizesBooleanGetters();
    public static final StyleFeature CAN_PASS_NULL_FOR_OPTIONAL_FIELD = new CanPassNullForOptionalField();
    public static final StyleFeature SERIALIZABLE_BY_JACKSON = new SerializableByJackson();

    private StyleFeatures() {
    }

}
