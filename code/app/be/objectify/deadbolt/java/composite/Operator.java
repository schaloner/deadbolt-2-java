/*
 * Copyright 2012-2016 Steve Chaloner
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package be.objectify.deadbolt.java.composite;

import java.util.function.BiFunction;

/**
 * @author Steve Chaloner (steve@objectify.be)
 */
public enum Operator implements BiFunction<Constraint, Constraint, Constraint> {
    AND {
        @Override
        public Constraint apply(final Constraint c1,
                                final Constraint c2) {
            return c1.and(c2);
        }
    },
    OR {
        @Override
        public Constraint apply(final Constraint c1,
                                final Constraint c2) {
            return c1.or(c2);
        }
    }
}
