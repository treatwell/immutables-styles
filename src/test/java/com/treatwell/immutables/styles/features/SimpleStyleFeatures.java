package com.treatwell.immutables.styles.features;

public final class SimpleStyleFeatures {

    public static final StyleFeature DEFAULT_IMMUTABLES_NAMING_STRATEGY = new DefaultImmutablesNamingStrategy();
    public static final StyleFeature ABSTRACT_STRIPPING_NAMING_STRATEGY = new AbstractStrippingNamingStrategy();

    public static final StyleFeature HAS_PRIVATE_NO_ARG_CONSTRUCTOR = new PrivateNoArgConstructor();
    public static final StyleFeature RECOGNIZES_BOOLEAN_GETTERS = new RecognizesBooleanGetters();
    public static final StyleFeature SERIALIZABLE_BY_JACKSON = new SerializableByJackson();

    private SimpleStyleFeatures() {
    }

}
