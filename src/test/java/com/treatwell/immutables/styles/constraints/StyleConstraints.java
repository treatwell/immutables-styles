package com.treatwell.immutables.styles.constraints;

public final class StyleConstraints {

    public static final StyleConstraint HAS_PRIVATE_NO_ARG_CONSTRUCTOR = new PrivateNoArgConstructorConstraint();
    public static final StyleConstraint RECOGNIZES_BOOLEAN_GETTERS = new RecognizesBooleanGetters();
    public static final StyleConstraint CAN_PASS_NULL_FOR_OPTIONAL_FIELD = new CanPassNullForOptionalField();
    public static final StyleConstraint SERIALIZABLE_BY_JACKSON = new IsAlwaysSerializableByJackson();

    private StyleConstraints() {
    }

}
