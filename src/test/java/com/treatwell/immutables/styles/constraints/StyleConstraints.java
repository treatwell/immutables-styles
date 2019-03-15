package com.treatwell.immutables.styles.constraints;

public final class StyleConstraints {

    public static final StyleConstraint HAS_PRIVATE_NO_ARG_CONSTRUCTOR = new PrivateNoArgConstructorConstraint();
    public static final StyleConstraint RECOGNIZES_BOOLEAN_GETTERS = new RecognizesBooleanGetters();

    private StyleConstraints() {
    }

}
