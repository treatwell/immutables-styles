package com.treatwell.immutables.styles.constants;

import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Style;

/**
 * The types of matchers we use for {@link Immutable}-annotated classes' name to be parsed to generate more specific subclasses than always going the (default)
 * {@code Xyz -> ImmutableXyz} but instead to be able to have {@code AbstractXyz -> Xyz}.
 *
 * @see Style#typeAbstract() for the annotated class name pattern matching
 * @see Style#typeImmutable() for the generated class' name composition pattern
 */
public final class ClassNamePatterns {

    /*
     * Prefix-based
     */
    public static final String PREFIX_IMMUTABLE = "Immutable*";
    public static final String PREFIX_ABSTRACT = "Abstract*";

    /*
     * Suffix-based
     */
    public static final String SUFFIX_EVENT = "*Event";

    // utility class
    private ClassNamePatterns() {
    }

}
