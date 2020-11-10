/*
 * Copyright 2020 The Home Depot
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.lambdadepot.collection;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Helper class that contains factory methods for all tuple variants.
 */
public final class Tuples {
    private Tuples() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the singleton instance of {@link Tuple0}.
     *
     * @return {@link Tuple0#empty()}
     */
    @NonNull
    public static Tuple0 of() {
        return Tuple0.empty();
    }

    /**
     * Factory method for creating a {@link Tuple1} containing
     * {@code t1} as the first and only component.
     *
     * @param t1   the first component
     * @param <T1> the first component type
     * @return {@link Tuple1#of(Object)}
     */
    @NonNull
    public static <T1> Tuple1<T1> of(T1 t1) {
        return Tuple1.of(t1);
    }

    /**
     * Factory method for creating a {@link Tuple2} containing
     * {@code t1} as the first component and {@code t2} as the
     * second component.
     *
     * @param t1   the first component
     * @param t2   the second component
     * @param <T1> the type of {@code t1}
     * @param <T2> the type of {@code t2}
     * @return {@link Tuple2#of(Object, Object)}
     */
    @NonNull
    public static <T1, T2> Tuple2<T1, T2> of(T1 t1, T2 t2) {
        return Tuple2.of(t1, t2);
    }

    /**
     * Factory method for creating a {@link Tuple3} containing
     * {@code t1} as the first component, {@code t2} as the
     * second component and {@code t3} as the third component.
     *
     * @param t1   the first component
     * @param t2   the second component
     * @param t3   the third component
     * @param <T1> the type of {@code t1}
     * @param <T2> the type of {@code t2}
     * @param <T3> the type of {@code t3}
     * @return {@link Tuple3#of(Object, Object, Object)}
     */
    @NonNull
    public static <T1, T2, T3> Tuple3<T1, T2, T3> of(T1 t1, T2 t2, T3 t3) {
        return Tuple3.of(t1, t2, t3);
    }

    /**
     * Factory method for creating a {@link Tuple4} containing
     * {@code t1} as the first component, {@code t2} as the
     * second component, {@code t3} as the third component
     * and {@code t4} as the fourth component.
     *
     * @param t1   the first component
     * @param t2   the second component
     * @param t3   the third component
     * @param t4   the fourth component
     * @param <T1> the type of {@code t1}
     * @param <T2> the type of {@code t2}
     * @param <T3> the type of {@code t3}
     * @param <T4> the type of {@code t4}
     * @return {@link Tuple4#of(Object, Object, Object, Object)}
     */
    @NonNull
    public static <T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> of(T1 t1, T2 t2, T3 t3, T4 t4) {
        return Tuple4.of(t1, t2, t3, t4);
    }
}
