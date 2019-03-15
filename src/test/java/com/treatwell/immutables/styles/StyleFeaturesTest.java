package com.treatwell.immutables.styles;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.treatwell.immutables.styles.features.StyleFeatureCheck;
import com.treatwell.immutables.styles.features.StyleFeature;

@RunWith(Parameterized.class)
public abstract class StyleFeaturesTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Parameter(0)
    public String featureName;

    @Parameter(1)
    public StyleFeatureCheck featureCheck;

    @Before
    public void beforeEach() {
        logger.info("Feature: {}", featureName);
    }

    @Test
    public void checkFeature() {
        featureCheck.ensureFeature(getStyleClass(), getStyleAnnotatedClass(), getGeneratedClass());
    }

    abstract Class<?> getStyleClass();

    abstract Class<?> getStyleAnnotatedClass();

    abstract Class<?> getGeneratedClass();

    protected static Set<Object[]> supportsFeatures(StyleFeature... styleFeatures) {
        return Arrays.stream(styleFeatures).map(styleFeature -> new Object[]{
                styleFeature.getHumanReadableFeatureName(),
                (StyleFeatureCheck) styleFeature::assertFeature
        }).collect(Collectors.toSet());
    }

}
