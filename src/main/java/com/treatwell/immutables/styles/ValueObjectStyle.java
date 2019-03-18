package com.treatwell.immutables.styles;

import static org.immutables.value.Value.Style.ImplementationVisibility.PUBLIC;

import javax.persistence.Access;

import org.immutables.value.Value.Style;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Immutables {@link Style} to define value-object classes; that is, classes with a single
 * implementation on which all the fields are immutable. This style means that you define:
 *
 * <pre>{@code
 *   abstract class AbstractValueObject {
 *       Long getId();
 *       @Nullable getName();
 *       boolean isFlagged();
 *   }
 * }</pre>
 * <p>
 * from which a single public implementation class ({@code ValueObject}) will be defined:
 *
 * <pre>{@code
 *   public final class ValueObject extends AbstractValueObject {
 *       Long getId() {...}
 *       @Nullable getName() {...}
 *       boolean isFlagged() {...}
 *
 *       public static ValueObject.Builder builder();
 *   }
 * }</pre>
 * <p>
 * which is then created via:
 *
 * <pre>{@code
 *   ValueObject vo = ValueObject.builder()
 *       .id(1234L)
 *       .name("Steve Storey")
 *       .flagged(false)
 *       .build();
 * }</pre>
 * <p>
 * Changes to this object (other than builder strictness), should also be applied to
 * the {@link NonStrictValueObjectStyle} configuration.
 */
@Style(
        // A. Properties defining how clients use the Immutables

        // Allow for simple boolean types as well.
        get = {"is*", "get*"}, // Allow for simple boolean types as well.
        // We expect the defining type to be an Abstract type
        typeAbstract = "Abstract*",
        // Remove the 'Abstract' prefix from the public implementation
        typeImmutable = "*",
        // We expect the abstract types to be non-public, and we want the impls to be public
        visibility = PUBLIC,

        // B. Internal implementation details

        optionalAcceptNullable = true, // Optional<Xyz> expressly indicates that empty values are possible !?
        privateNoargConstructor = true, // allow hibernate etc. to instantiate immutables
        // We allow some specific annotations to be passed through when provided on the abstract
        // class/interface, as they may be required on the underlying single public final implementation
        passAnnotations = {
                JsonTypeName.class, JsonPropertyOrder.class, JsonProperty.class,
                JsonSerialize.class, Access.class
        },
        // Multiple calls to builder methods usually indicate copy/paste issues or possibly bugs,
        // so we enforce the use of strict builders for our value objects (with the exception of
        // Mergeable implementations, which should use the MergeableValueObjectStyle).
        strictBuilder = true,
        // Ensure Guava collections are not used, since Spring converters do not support them
        jdkOnly = true,
        // We will let Jackson work its normal magic to compute property names, which also provides us the flexibility
        // to go and override them more easily when we want to.
        forceJacksonPropertyNames = false
)
@JsonSerialize // Triggers Jackson integration on all users.
public @interface ValueObjectStyle {

}
