package com.treatwell.immutables.styles;

import static com.treatwell.immutables.styles.constants.ClassNamePatterns.PREFIX_ABSTRACT;
import static com.treatwell.immutables.styles.constants.AccessorNamePatterns.PREFIX_GET;
import static com.treatwell.immutables.styles.constants.AccessorNamePatterns.PREFIX_IS;
import static org.immutables.value.Value.Style.ImplementationVisibility.PUBLIC;

import javax.persistence.Access;

import org.immutables.value.Value.Style;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * This style is strictly the same as {@link ValueObjectStyle} except that it does generate "non-strict" builders.
 * i.e. the builder to create instances of the generated immutable class WILL allow multiple setter calls for the same field.
 *
 * e.g.:
 * <pre>{@code
 *  public MergeableThing merge(MergeableThing overrides) {
 *      if (overrides == null) {
 *          return MergeableThing.copyOf(this);
 *      }
 *      return MergeableThing.builder()
 *              .from(this) // will call relevant setters
 *              .from(overrides) // can call some of them again if overriden ones had values already
 *              .build();
 *  }
 * }</pre>
 *
 * @see ValueObjectStyle for the general information on semantics unrelated to builder usage restrictions
 */
@Style(
        // A. Properties defining how clients use the Immutables

        // Allow for simple boolean types as well.
        get = {PREFIX_IS, PREFIX_GET}, // Allow for simple boolean types as well.
        // We expect the defining type to be an Abstract type
        typeAbstract = PREFIX_ABSTRACT,
        // Remove the 'Abstract' prefix from the public implementation
        typeImmutable = "*",
        // We expect the abstract types to be non-public, and we want the impls to be public
        visibility = PUBLIC,

        // B. Internal implementation details

        optionalAcceptNullable = true, // Optional<Xyz> expressly indicates that empty values are possible !?
        privateNoargConstructor = true, // allow hibernate etc. to instantiate immutables
        // We allow some specific annotations to be passed through when provided on the abstract
        // class/interface, as they may be required on the underlying single public final implementation
        passAnnotations = {JsonTypeName.class, JsonPropertyOrder.class, JsonProperty.class, JsonSerialize.class, Access.class},
        // We disable strict builders here
        strictBuilder = false,
        // Ensure Guava collections are not used, since Spring converters do not support them
        jdkOnly = true,
        // We will let Jackson work its normal magic to compute property names, which also provides us the flexibility
        // to go and override them more easily when we want to.
        forceJacksonPropertyNames = false

)
@JsonSerialize
public @interface NonStrictValueObjectStyle {

}
