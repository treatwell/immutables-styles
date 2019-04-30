/*
 * Copyright 2019 Hotspring Ventures Ltd.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

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
