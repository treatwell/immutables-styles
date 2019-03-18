package com.treatwell.immutables.styles.features;

import static com.treatwell.immutables.styles.constants.ClassNameMatchers.PREFIX_ABSTRACT;

public class AbstractStrippingNamingStrategy extends NamingFeature {

    protected AbstractStrippingNamingStrategy() {
        super(PREFIX_ABSTRACT, "", "", "");
    }

}
