package com.treatwell.immutables.styles.features;

public final class SimpleStyleFeatures {

    public static final StyleFeature HAS_PRIVATE_NO_ARG_CONSTRUCTOR = new PrivateNoArgConstructor();
    public static final StyleFeature RECOGNIZES_BOOLEAN_GETTERS = new RecognizesBooleanGetters();
    public static final StyleFeature SERIALIZABLE_BY_JACKSON = new SerializableByJackson();
    public static final StyleFeature USES_STANDARD_NAMING_STRATEGY = new UsesStandardNamingStrategy();

    private SimpleStyleFeatures() {
    }

}
