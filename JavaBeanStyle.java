package com.treatwell.common.utils.immutables;

import org.immutables.value.Value;

/**
 * Style to be used in combination with {@link org.immutables.value.Value.Modifiable}. Generated
 * classes with have setters with void return types. Primary use case is for command objects
 * that would be posted using JSF.
 */
@Value.Style(
    // Generate Java bean like setters (void return type)
    beanFriendlyModifiables = true
)
// FIXME: Disabling this for 224 as the blanket change caused a few regressions, so we'll re-enable
// it as soon as master becomes 225.
// @JsonSerialize // Triggers Jackson integration on all users.
public @interface JavaBeanStyle {
}
