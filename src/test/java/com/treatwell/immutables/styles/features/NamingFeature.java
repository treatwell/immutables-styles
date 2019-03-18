package com.treatwell.immutables.styles.features;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;

public abstract class NamingFeature implements StyleFeature {

    private final String annotatedPrefix;
    private final String annotatedSuffix;

    private final String generatedPrefix;
    private final String generatedSuffix;

    protected NamingFeature(String annotatedPrefix, String annotatedSuffix, String generatedPrefix, String generatedSuffix) {
        this.annotatedPrefix = stripStar(annotatedPrefix);
        this.annotatedSuffix = stripStar(annotatedSuffix);
        this.generatedPrefix = stripStar(generatedPrefix);
        this.generatedSuffix = stripStar(generatedSuffix);
    }

    @Override
    public String getHumanReadableFeatureName() {
        final String output = "Naming strategy follows: %s*%s -> %s*%s";
        return String.format(output, annotatedPrefix, annotatedSuffix, generatedPrefix, generatedSuffix);
    }

    @Override
    public void assertFeature(Class<?> style, Class<?> annotated, Class<?> generated) {
        final String annotatedFullName = annotated.getSimpleName();
        final String annotatedCoreName = extractCoreName(annotatedFullName, annotatedPrefix, annotatedSuffix);

        final String generatedFullName = generated.getSimpleName();
        final String generatedCoreName = extractCoreName(generatedFullName, generatedPrefix, generatedSuffix);

        assertThat(generatedCoreName).isEqualTo(annotatedCoreName);
    }

    private static String stripStar(final String matcher) {
        return matcher.replace("*", "");
    }

    private static String extractCoreName(final String fullName, final String prefix, final String suffix) {
        if (!Objects.equals(prefix, "")) {
            final int prefixPos = fullName.indexOf(prefix);
            assertThat(prefixPos).withFailMessage("Expected %s to start with prefix %s", fullName, prefix).isEqualTo(0);
        }

        if (!Objects.equals(suffix, "")) {
            final int suffixPos = fullName.indexOf(suffix);
            assertThat(suffixPos + suffix.length()).withFailMessage("Expected %s to end with suffix %s", fullName, suffix).isEqualTo(fullName.length());
        }

        final String coreName = fullName.substring(prefix.length(), fullName.length() - suffix.length());
        assertThat(coreName).withFailMessage("Expected %s to be named with format %sXyz%s", fullName, prefix, suffix).isNotEmpty();

        return coreName;
    }

}
