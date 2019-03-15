package com.treatwell.immutables.styles.features;

public class CanPassNullForOptionalField implements StyleFeature {

    @Override
    public String getHumanReadableFeatureName() {
        return "Can pass null as value for Optional field and treat it as Optional#empty()";
    }

    @Override
    public void assertFeature(Class<?> style, Class<?> annotated, Class<?> generated) {
        return;
    }

}
