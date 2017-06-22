package com.treatwell.common.utils.immutables;

import org.immutables.value.Value;
import org.immutables.value.Value.Style;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.treatwell.common.utils.identity.IdentitySequence;

/**
 * Use {@link DefaultStyle}
 */
@Deprecated
@Style(
        // A. Properties defining how clients use the Immutables

        // Disable validation for easy development and backwards compatibility
        validationMethod = Value.Style.ValidationMethod.NONE,
        // Allow for simple boolean types as well.
        get = {"is*", "get*"},
        // Take the type name (`Xyz`) and prefix the implementation (`ImmutableXyz`)
        typeImmutable = "Immutable*",

        // B. Internal implementation details

        // Optional<Xyz> expressly indicates that empty values are possible, so we should clearly allow
        // for null values to be provided to the unwrapped builder.xyz/withXyz methods.
        optionalAcceptNullable = true,
        // Require for Hibernate and some other libraries to be able to construct instances internally.
        privateNoargConstructor = true,
        // We allow some specific annotations to be passed through when provided on the abstract
        // class/interface, as they may be required on the underlying single public final implementation
        passAnnotations = {JsonTypeName.class, JsonPropertyOrder.class, JsonProperty.class, IdentitySequence.class},
        // Multiple calls to builder methods usually indicate copy/paste issues or possibly bugs,
        // so we enforce the use of strict builders for our immutable objects.
        strictBuilder = true
)
public @interface UnvalidatedDefaultStyle {
}
