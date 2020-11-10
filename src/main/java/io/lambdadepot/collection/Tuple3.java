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
 * A tuple with three components.
 *
 * @param <T1> the type of {@code t1}
 * @param <T2> the type of {@code t2}
 * @param <T3> the type of {@code t3}
 */
@ToString
@EqualsAndHashCode
@SuppressWarnings({"unused", "WeakerAccess"})
public final class Tuple3<T1, T2, T3> implements Iterable<Object> {
    /**
     * The number of components this tuple contains.
     */
    public static final int SIZE = 3;
    /**
     * The first component.
     */
    private final T1 t1;
    /**
     * The second component.
     */
    private final T2 t2;
    /**
     * The third component.
     */
    private final T3 t3;

    private Tuple3(T1 t1, T2 t2, T3 t3) {
        this.t1 = t1;
        this.t2 = t2;
        this.t3 = t3;
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
     * @return a new {@link Tuple3} instance containing {@code t1}
     * as the first component, {@code t2} as the second component
     * and {@code t3} as the third component.
     */
    public static <T1, T2, T3> Tuple3<T1, T2, T3> of(T1 t1, T2 t2, T3 t3) {
        return new Tuple3<>(t1, t2, t3);
    }

    /**
     * {@inheritDoc}
     */
    @NonNull
    @Override
    public Iterator<Object> iterator() {
        return ArrayIterator.of(t1, t2, t3);
    }

    /**
     * Applies {@code mapper} to {@link #t1} and returns a new {@link Tuple3}
     * instance with the output from {@code mapper} as the {@code t1} component.
     *
     * @param mapper the mapping function to apply to {@link #t1}
     * @param <O>    the output type
     * @return a new {@link Tuple3} with the output of {@code mapper}
     * as the {@code t1} component
     * @throws NullPointerException if {@code mapper} is null
     */
    @NonNull
    public <O> Tuple3<O, T2, T3> mapT1(@NonNull Function1<T1, O> mapper) {
        Objects.requireNonNull(mapper, "mapper");
        return new Tuple3<>(mapper.apply(t1), t2, t3);
    }

    /**
     * Applies {@code mapper} to {@link #t2} and returns a new {@link Tuple3}
     * instance with the output from {@code mapper} as the {@code t2} component.
     *
     * @param mapper the mapping function to apply to {@link #t2}
     * @param <O>    the output type
     * @return a new {@link Tuple3} with the output of {@code mapper}
     * as the {@code t2} component
     * @throws NullPointerException if {@code mapper} is null
     */
    @NonNull
    public <O> Tuple3<T1, O, T3> mapT2(@NonNull Function1<T2, O> mapper) {
        Objects.requireNonNull(mapper, "mapper");
        return new Tuple3<>(t1, mapper.apply(t2), t3);
    }

    /**
     * Applies {@code mapper} to {@link #t3} and returns a new {@link Tuple3}
     * instance with the output from {@code mapper} as the {@code t3} component.
     *
     * @param mapper the mapping function to apply to {@link #t3}
     * @param <O>    the output type
     * @return a new {@link Tuple3} with the output of {@code mapper}
     * as the {@code t3} component
     * @throws NullPointerException if {@code mapper} is null
     */
    @NonNull
    public <O> Tuple3<T1, T2, O> mapT3(@NonNull Function1<T3, O> mapper) {
        Objects.requireNonNull(mapper, "mapper");
        return new Tuple3<>(t1, t2, mapper.apply(t3));
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
    public <O> O transform(Function1<Tuple3<T1, T2, T3>, O> transformer) {
        Objects.requireNonNull(transformer, "transformer");
        return transformer.apply(this);
    }

    /**
     * Returns a {@link Tuple3} with the components reversed.
     *
     * @return a new {@link Tuple3} with the components reversed
     */
    public Tuple3<T3, T2, T1> reversed() {
        return new Tuple3<>(t3, t2, t1);
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

    /**
     * Returns the third component.
     *
     * @return {@link #t3}
     */
    public T3 getT3() {
        return t3;
    }
}
