package com.treatwell.immutables.styles.constraints;

public final class StyleConstraints {

    public static final StyleConstraint HAS_PRIVATE_NO_ARG_CONSTRUCTOR = new PrivateNoArgConstructorConstraint();

    private StyleConstraints() {
    }

}
