package com.treatwell.common.utils.immutables;

import static org.immutables.value.Value.Style.ImplementationVisibility.PUBLIC;

import javax.persistence.Access;

import org.immutables.value.Value.Style;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.treatwell.common.utils.identity.IdentitySequence;
import com.treatwell.common.utils.object.Mergeable;

/**
 * Style only to be used on objects which require non-strict builders for some specific (and documented!)
 * reason. One particular use-case is on objects that are implementing the {@link Mergeable}
 * interface, and are those which should be treated almost exactly the same as the
 * {@link ValueObjectStyle}, except that non-strict builders are generated, so that we can
 * use from() methods during the merge logic:
 *
 * <code><pre>
 * public OnlineBookingDefaults merge(OnlineBookingDefaults overrides) {
 *     if (overrides == null) {
 *         return OnlineBookingDefaults.copyOf(this);
 *     }
 *     return OnlineBookingDefaults.builder()
 *             .from(this)
 *             .from(overrides).build();
 * }
 * </pre></code>
 *
 * All the other uses described in the {@link ValueObjectStyle} still apply. Any changes
 * made there, should be applied here as well.
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
        passAnnotations = {JsonTypeName.class, JsonPropertyOrder.class, JsonProperty.class,
                JsonSerialize.class, IdentitySequence.class, Access.class },
        // We disable strict builders here
        strictBuilder = false,
        // Ensure Guava collections are not used, since Spring converters do not support them
        jdkOnly = true,
        // We will let Jackson work its normal magic to compute property names, which also provides us the flexibility
        // to go and override them more easily when we want to.
        forceJacksonPropertyNames = false

)
@JsonSerialize // Triggers Jackson integration on all users.
public @interface NonStrictValueObjectStyle {
}
