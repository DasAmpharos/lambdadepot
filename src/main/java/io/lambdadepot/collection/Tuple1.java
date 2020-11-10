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
 * A tuple with one component.
 *
 * @param <T1> the type of {@code t1}
 */
@ToString
@EqualsAndHashCode
@SuppressWarnings({"unused", "WeakerAccess"})
public final class Tuple1<T1> implements Iterable<Object> {
    /**
     * The number of components this Tuple contains.
     */
    public static final int SIZE = 1;
    /**
     * The first and only component.
     */
    private final T1 t1;

    private Tuple1(T1 t1) {
        this.t1 = t1;
    }

    /**
     * Factory method for create a {@link Tuple1} containing
     * {@code t1} as the first and only component.
     *
     * @param t1   the first and only component
     * @param <T1> the first component type
     * @return a new {@link Tuple1} instance containing {@code t1}
     * as the first and only component
     */
    public static <T1> Tuple1<T1> of(T1 t1) {
        return new Tuple1<>(t1);
    }

    /**
     * {@inheritDoc}
     */
    @NonNull
    @Override
    public Iterator<Object> iterator() {
        return ArrayIterator.of(t1);
    }

    /**
     * Applies {@code mapper} to {@link #t1} and returns a new {@link Tuple1}
     * instance with the output from {@code mapper} as the {@code t1} component.
     *
     * @param mapper the mapping function to apply to {@link #t1}
     * @param <O>    the output type
     * @return a new {@link Tuple1} with the output of {@code mapper}
     * as the {@code t1} component
     * @throws NullPointerException if {@code mapper} is null
     */
    @NonNull
    public <O> Tuple1<O> mapT1(@NonNull Function1<T1, O> mapper) {
        Objects.requireNonNull(mapper, "mapper");
        return new Tuple1<>(mapper.apply(t1));
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
    public <O> O transform(Function1<Tuple1<T1>, O> transformer) {
        Objects.requireNonNull(transformer, "transformer");
        return transformer.apply(this);
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
}
