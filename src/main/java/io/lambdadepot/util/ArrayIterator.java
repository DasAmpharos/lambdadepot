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

package io.lambdadepot.util;

import java.util.Iterator;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Simple iterator implementation backed by an array.
 *
 * @param <T> the array element type
 */
public final class ArrayIterator<T> implements Iterator<T> {
    /**
     * Current element index.
     */
    private int index;
    /**
     * Array of elements to iterate over.
     */
    private final T[] array;

    private ArrayIterator(T[] array) {
        this.array = array;
    }

    /**
     * Factory method for creating an {@link Iterator} for a given
     * array of elements.
     *
     * @param elements the array of elements to iterate over
     * @param <T>      the array element type
     * @return a new {@link Iterator} that iterates over the given elements
     */
    @NonNull
    @SafeVarargs
    public static <T> Iterator<T> of(T... elements) {
        return new ArrayIterator<>(elements);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasNext() {
        return array.length > index;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T next() {
        return array[index++];
    }
}
