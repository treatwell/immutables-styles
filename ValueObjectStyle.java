package com.treatwell.common.utils.immutables;

import static org.immutables.value.Value.Style.ImplementationVisibility.PUBLIC;

import javax.persistence.Access;

import org.immutables.value.Value.Style;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.treatwell.common.utils.identity.IdentitySequence;

/**
 * Immutables {@link Style} to define value-object classes; that is, classes with a single
 * implementation on which all the fields are immutable. This style means that you define:
 *
 * <code><pre>
 * /* package * / abstract class AbstractValueObject {
 *     Long getId();
 *     @Nullable getName();
 *     boolean isFlagged();
 * }</pre></code>
 *
 * from which a single public implementation class ({@code ValueObject}) will be defined:
 *
 * <code><pre>
 * public final class ValueObject extends AbstractValueObject {
 *     Long getId() {...}
 *     @Nullable getName() {...}
 *     boolean isFlagged() {...}
 *
 *     public static ValueObject.Builder builder();
 * }</pre></code>
 *
 * which is then created via:
 *
 * <code><pre>
 * ValueObject vo = ValueObject.builder()
 *     .id(1234L)
 *     .name("Steve Storey")
 *     .flagged(false)
 *     .build();
 * </pre></code>
 *
 * Changes to this object (other than builder strictness), should also be applied to
 * the {@link MergeableValueObjectStyle} configuration.
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
        // Multiple calls to builder methods usually indicate copy/paste issues or possibly bugs,
        // so we enforce the use of strict builders for our value objects (with the exception of
        // Mergeable implementations, which should use the MergeableValueObjectStyle).
        strictBuilder = true,
        // Ensure Guava collections are not used, since Spring converters do not support them
        jdkOnly = true
)
// FIXME: Disabling this for 224 as the blanket change caused a few regressions, so we'll re-enable
// it as soon as master becomes 225.
// @JsonSerialize // Triggers Jackson integration on all users.
public @interface ValueObjectStyle {
}
