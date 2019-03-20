package com.treatwell.immutables.styles.constants;

import org.immutables.value.Value.Style;

/**
 * This patterns we use to instruct immutables on how to detect accessor methods (for which to generate fields for the generated class).
 * <p>
 * Notably, {@link #PREFIX_IS} allows us to match the Java beans-style of {@code boolean} accessors. (usually {@code isXyz()} for a field named {@code xyz}).
 *
 * @see Style#get()
 */
public final class AccessorNamePatterns {

    public static final String PREFIX_GET = "get*";
    public static final String PREFIX_IS = "is*";

    // utility class
    private AccessorNamePatterns() {
    }

}
