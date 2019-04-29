/*
 * Copyright 2017 - 2019 Hotspring Ventures Ltd.
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
