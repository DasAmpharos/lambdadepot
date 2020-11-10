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

import io.lambdadepot.util.Option;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A collection of methods to assist operating on {@link java.util.Map}s
 * in a functional manner.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public final class Maps {
    private Maps() {
        throw new UnsupportedOperationException();
    }

    /**
     * Puts the key/value in the map if the key is not present, and then returns the value from the
     * supplier. Otherwise the value is returned that is already existing for the provided key.
     *
     * @param key           key whose presence in this map is to be tested
     * @param valueSupplier value supplier to be used if the key is not found in the map
     * @param <K>           key type
     * @param <V>           value type
     * @return the value from the provided supplier if the key is not found in the map, or the value
     * for the key if it is contained in the map.
     */
    public static <K, V> V getOrPut(@NonNull Map<K, V> map, @NonNull K key, @NonNull Supplier<V> valueSupplier) {
        Objects.requireNonNull(map, "map");
        Objects.requireNonNull(key, "key");
        Objects.requireNonNull(valueSupplier, "valueSupplier");
        if (!map.containsKey(key)) {
            map.put(key, valueSupplier.get());
        }
        return map.get(key);
    }

    /**
     * Returns the value mapped to the provided key if the key is present in the map, otherwise the
     * value from the provided supplier is returned.
     *
     * @param key   key whose presence in this map is to be tested
     * @param other value supplier to be used in providing a return value if the map does not contain the provided key
     * @param <K>   key type
     * @param <V>   value type
     * @return the value found related to the provided key 'if present' or the value from the provided supplier.
     */
    public static <K, V> V getOrDefault(@NonNull Map<K, V> map, @NonNull K key, @NonNull Supplier<V> other) {
        Objects.requireNonNull(map, "map");
        Objects.requireNonNull(key, "key");
        Objects.requireNonNull(other, "other");
        return map.containsKey(key) ? map.get(key) : other.get();
    }

    /**
     * Returns an {@link Optional} of the value found for the provided key if present,
     * otherwise {@link Optional#empty()}.
     *
     * @param map which contains the value for the provided key
     * @param key respective to the value which will be returned
     * @param <K> key type
     * @param <V> value type
     * @return {@link Optional#empty()} if the provided key is not found in the provided map or if the value for the
     * key is null, otherwise {@link Optional} of the value.
     */
    @NonNull
    public static <K, V> Option<V> get(@NonNull Map<K, V> map, @NonNull K key) {
        Objects.requireNonNull(map, "map");
        Objects.requireNonNull(key, "key");
        if (!map.containsKey(key)) {
            return Option.empty();
        }
        return Option.of(map.get(key));
    }
}
