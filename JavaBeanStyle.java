package com.treatwell.common.utils.immutables;

import org.immutables.value.Value;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Style to be used in combination with {@link org.immutables.value.Value.Modifiable}. Generated
 * classes with have setters with void return types. Primary use case is for command objects
 * that would be posted using JSF.
 */
@Value.Style(
    // Generate Java bean like setters (void return type)
    beanFriendlyModifiables = true,
    // We will let Jackson work its normal magic to compute property names, which also provides us the flexibility
    // to go and override them more easily when we want to.
    forceJacksonPropertyNames = false
)
@JsonSerialize // Triggers Jackson integration on all users.
public @interface JavaBeanStyle {
}
