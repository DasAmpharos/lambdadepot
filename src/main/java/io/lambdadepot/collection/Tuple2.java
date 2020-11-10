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

import io.lambdadepot.function.Function1;
import io.lambdadepot.util.ArrayIterator;
import java.util.Iterator;
import java.util.Objects;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A tuple with two components.
 *
 * @param <T1> the type of {@code t1}
 * @param <T2> the type of {@code t2}
 */
@ToString
@EqualsAndHashCode
@SuppressWarnings({"unused", "WeakerAccess"})
public final class Tuple2<T1, T2> implements Iterable<Object> {
    /**
     * The number of components this tuple contains.
     */
    public static final int SIZE = 2;
    /**
     * The first component.
     */
    private final T1 t1;
    /**
     * The second component.
     */
    private final T2 t2;

    private Tuple2(T1 t1, T2 t2) {
        this.t1 = t1;
        this.t2 = t2;
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
     * @return a new {@link Tuple2} instance containing {@code t1}
     * as the first component and {@code t2} as the second component
     */
    public static <T1, T2> Tuple2<T1, T2> of(T1 t1, T2 t2) {
        return new Tuple2<>(t1, t2);
    }

    /**
     * {@inheritDoc}
     */
    @NonNull
    @Override
    public Iterator<Object> iterator() {
        return ArrayIterator.of(t1, t2);
    }

    /**
     * Applies {@code mapper} to {@link #t1} and returns a new {@link Tuple2}
     * instance with the output from {@code mapper} as the {@code t1} component.
     *
     * @param mapper the mapping function to apply to {@link #t1}
     * @param <O>    the output type
     * @return a new {@link Tuple2} with the output of {@code mapper}
     * as the {@code t1} component
     * @throws NullPointerException if {@code mapper} is null
     */
    @NonNull
    public <O> Tuple2<O, T2> mapT1(@NonNull Function1<T1, O> mapper) {
        Objects.requireNonNull(mapper, "mapper");
        return new Tuple2<>(mapper.apply(t1), t2);
    }

    /**
     * Applies {@code mapper} to {@link #t2} and returns a new {@link Tuple2}
     * instance with the output from {@code mapper} as the {@code t2} component.
     *
     * @param mapper the mapping function to apply to {@link #t2}
     * @param <O>    the output type
     * @return a new {@link Tuple2} with the output of {@code mapper}
     * as the {@code t2} component
     * @throws NullPointerException if {@code mapper} is null
     */
    @NonNull
    public <O> Tuple2<T1, O> mapT2(@NonNull Function1<T2, O> mapper) {
        Objects.requireNonNull(mapper, "mapper");
        return new Tuple2<>(t1, mapper.apply(t2));
    }

    /**
     * Applies {@code transformer} to {@code this} and returns the output
     * of {@code transformer}.
     *
     * @param transformer the transformation function to apply to {@code this}
     * @param <O>         the output type
     * @return the output of {@code transformer}
     * @throws NullPointerException if {@code transformer} is null
     */
    public <O> O transform(Function1<Tuple2<T1, T2>, O> transformer) {
        Objects.requireNonNull(transformer, "transformer");
        return transformer.apply(this);
    }

    /**
     * Returns a {@link Tuple2} with the components reversed.
     *
     * @return a new {@link Tuple2} with the components reversed
     */
    public Tuple2<T2, T1> reversed() {
        return new Tuple2<>(t2, t1);
    }

    /**
     * Returns the number of components this tuple can contain.
     *
     * @return {@link #SIZE}
     */
    public int getSize() {
        return SIZE;
    }

    /**
     * Returns the first component.
     *
     * @return {@link #t1}
     */
    public T1 getT1() {
        return t1;
    }

    /**
     * Returns the second component.
     *
     * @return {@link #t2}
     */
    public T2 getT2() {
        return t2;
    }
}
