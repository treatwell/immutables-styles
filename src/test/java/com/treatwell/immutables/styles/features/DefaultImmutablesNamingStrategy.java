package com.treatwell.immutables.styles.features;

import static com.treatwell.immutables.styles.constants.ClassNameMatchers.PREFIX_IMMUTABLE;

public class DefaultImmutablesNamingStrategy extends NamingFeature {

    protected DefaultImmutablesNamingStrategy() {
        super("", "", PREFIX_IMMUTABLE, "");
    }

}
