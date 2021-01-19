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

import io.lambdadepot.function.Function1;
import io.lambdadepot.function.Predicate1;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * A collection of useful predicates.
 */
public final class Predicates {
    private Predicates() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Checks if all of the given {@link Predicate}s evaluate as {@code true}.
     *
     * @param predicates an array of {@link Predicate}s to test type {@code T}
     * @param <T1>       the type of the input to the predicate
     * @return {@link Predicate1} instance testing if all of the given {@link Predicate}s
     * evaluates as {@code true}
     * @throws IllegalArgumentException if no {@link Predicate} is passed
     * @throws IllegalArgumentException if {@code predicates} contains {@code null}
     */
    @SafeVarargs
    public static <T1> Predicate1<T1> allOf(Predicate<? super T1>... predicates) {
        validatePredicates(predicates);
        return t1 -> Arrays.stream(predicates)
            .allMatch(predicate -> predicate.test(t1));
    }

    /**
     * Checks if any of the given {@link Predicate}s evaluate as {@code true}.
     *
     * @param predicates an array of {@link Predicate}s to test type {@code T}
     * @param <T1>       the type of the input to the predicate
     * @return {@link Predicate1} instance testing if any of the given {@link Predicate}s
     * evaluates as {@code true}
     * @throws IllegalArgumentException if no {@link Predicate} is passed
     * @throws IllegalArgumentException if {@code predicates} contains {@code null}
     */
    @SafeVarargs
    public static <T1> Predicate1<T1> anyOf(Predicate<? super T1>... predicates) {
        validatePredicates(predicates);
        return t1 -> Arrays.stream(predicates)
            .anyMatch(predicate -> predicate.test(t1));

    }

    /**
     * Checks if all of the given {@link Predicate}s evaluate as {@code false}.
     *
     * @param predicates an array of {@link Predicate}s to test type {@code T}
     * @param <T1>       the type of the input to the predicate
     * @return {@link Predicate1} instance testing if all of the given {@link Predicate}s
     * evaluates as {@code false}
     * @throws IllegalArgumentException if no {@link Predicate} is passed
     * @throws IllegalArgumentException if {@code predicates} contains {@code null}
     */
    @SafeVarargs
    public static <T1> Predicate1<T1> noneOf(Predicate<? super T1>... predicates) {
        validatePredicates(predicates);
        return anyOf(predicates).negate();
    }

    /**
     * Validates the given {@link Predicate}s.
     *
     * <p>Validation ensures that:
     * <ul>
     * <li>At least one {@link Predicate} exists</li>
     * <li>{@code predicates} does not contain a {@code null} {@link Predicate}</li>
     * </ul>
     *
     * @param predicates an array of {@link Predicate}s to validate
     * @param <T1>       the type of the input to the predicate
     * @throws IllegalArgumentException if no {@link Predicate} is passed
     * @throws IllegalArgumentException if {@code predicates} contains {@code null}
     */
    @SafeVarargs
    public static <T1> void validatePredicates(Predicate<? super T1>... predicates) {
        if (predicates.length <= 0) {
            throw new IllegalArgumentException("no Predicate supplied to evaluate");
        }
        if (Arrays.asList(predicates).contains(null)) {
            throw new IllegalArgumentException("null Predicate supplied");
        }
    }

    /**
     * Returns a {@link Predicate1} instance that checks if the given argument
     * is not {@code null}.
     *
     * @param <T1> the type of the input to the predicate
     * @return a {@link Predicate1} instance that checks if the given argument
     * is not {@code null}
     */
    public static <T1> Predicate1<T1> isNotNull() {
        return Objects::nonNull;
    }

    /**
     * Returns a {@link Predicate1} instance that checks if the given argument
     * is {@code null}.
     *
     * @param <T1> the type of the input to the predicate
     * @return a {@link Predicate1} instance that checks if the given argument
     * is {@code null}
     */
    public static <T1> Predicate1<T1> isNull() {
        return Objects::isNull;
    }

    /**
     * Shorthand for {@link Predicate#negate()}.
     *
     * <p>Shortens
     * <code>
     * Predicate1.of(Objects::isNull).negate().test(o);
     * </code>
     *
     * <p>as
     * <code>
     * Predicates.not(Objects::isNull).test(o);
     * </code>
     *
     * <p>or
     * <code>
     * import static com.homedepot.common.util.functional.function.Predicates.not;
     * not(Objects::isNull).test(o);
     * </code>
     *
     * @param predicate the {@link Predicate} to invert
     * @param <T1>      the type of the input to the predicate
     * @return a predicate that represents the logical negation of the
     * given predicate
     * @throws NullPointerException if {@code predicate} is null
     */
    public static <T1> Predicate1<T1> not(Predicate<T1> predicate) {
        Objects.requireNonNull(predicate, "predicate");
        return t1 -> !predicate.test(t1);
    }

    /**
     * Uses {@link Comparable#compareTo(Object)} to determine if the tested argument
     * is greater than the given argument.
     *
     * @param value the value to check the tested argument against
     * @param <T2>  given argument type
     * @param <T1>  tested argument type
     * @return {@link Predicate1} instance testing whether the tested argument is greater
     * than the given argument
     * @throws NullPointerException if {@code value} is {@code null}
     * @see #isNull()
     * @see #isNotNull()
     */
    public static <T2, T1 extends Comparable<T2>> Predicate1<T1> isGreaterThan(T2 value) {
        Objects.requireNonNull(value, "value");
        return t1 -> t1.compareTo(value) > 0;
    }

    /**
     * Uses {@link Comparator#compare(Object, Object)} to determine if the tested argument
     * is greater than the given argument.
     *
     * @param value      the value to check the tested argument against
     * @param comparator comparator used to determine order
     * @param <T1>       tested argument and given argument type
     * @return {@link Predicate1} instance testing whether the tested argument is greater
     * than the given argument
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws NullPointerException if {@code comparator} is {@code null}
     * @see #isNull()
     * @see #isNotNull()
     */
    public static <T1> Predicate1<T1> isGreaterThan(T1 value, Comparator<T1> comparator) {
        Objects.requireNonNull(value, "value");
        Objects.requireNonNull(comparator, "comparator");
        return t1 -> comparator.compare(t1, value) > 0;
    }

    /**
     * Uses {@link Comparable#compareTo(Object)} to determine if the tested argument
     * is greater than or equal to the given argument.
     *
     * @param value the value to check the tested argument against
     * @param <T2>  given argument type
     * @param <T1>  tested argument type
     * @return {@link Predicate1} instance testing whether the tested argument is greater
     * than or equal to the given argument
     * @throws NullPointerException if {@code value} is {@code null}
     * @see #isNull()
     * @see #isNotNull()
     */
    public static <T2, T1 extends Comparable<T2>> Predicate1<T1> isGreaterThanOrEqualTo(T2 value) {
        Objects.requireNonNull(value, "value");
        return t1 -> t1.compareTo(value) >= 0;
    }

    /**
     * Uses {@link Comparator#compare(Object, Object)} to determine if the tested argument
     * is greater than or equal to the given argument.
     *
     * @param value      the value to check the tested argument against
     * @param comparator comparator used to determine order
     * @param <T1>       tested argument and given argument type
     * @return {@link Predicate1} instance testing whether the tested argument is greater
     * than or equal to the given argument
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws NullPointerException if {@code comparator} is {@code null}
     * @see #isNull()
     * @see #isNotNull()
     */
    public static <T1> Predicate1<T1> isGreaterThanOrEqualTo(T1 value, Comparator<T1> comparator) {
        Objects.requireNonNull(value, "value");
        Objects.requireNonNull(comparator, "comparator");
        return t1 -> comparator.compare(t1, value) >= 0;
    }

    /**
     * Uses {@link Comparable#compareTo(Object)} to determine if the tested argument
     * is less than the given argument.
     *
     * @param value the value to check the tested argument against
     * @param <T2>  given argument type
     * @param <T1>  tested argument type
     * @return {@link Predicate1} instance testing whether the tested argument is less
     * than the given argument
     * @throws NullPointerException if {@code value} is {@code null}
     * @see #isNull()
     * @see #isNotNull()
     */
    public static <T2, T1 extends Comparable<T2>> Predicate1<T1> isLessThan(T2 value) {
        Objects.requireNonNull(value, "value");
        return t1 -> t1.compareTo(value) < 0;
    }

    /**
     * Uses {@link Comparator#compare(Object, Object)} to determine if the tested argument
     * is less than the given argument.
     *
     * @param value      the value to check the tested argument against
     * @param comparator comparator used to determine order
     * @param <T1>       tested argument and given argument type
     * @return {@link Predicate1} instance testing whether the tested argument is less
     * than the given argument
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws NullPointerException if {@code comparator} is {@code null}
     * @see #isNull()
     * @see #isNotNull()
     */
    public static <T1> Predicate1<T1> isLessThan(T1 value, Comparator<T1> comparator) {
        Objects.requireNonNull(value, "value");
        Objects.requireNonNull(comparator, "comparator");
        return t1 -> comparator.compare(t1, value) < 0;
    }

    /**
     * Uses {@link Comparable#compareTo(Object)} to determine if the tested argument
     * is less than or equal to the given argument.
     *
     * @param value the value to check the tested argument against
     * @param <T2>  given argument type
     * @param <T1>  tested argument type
     * @return {@link Predicate1} instance testing whether the tested argument is less
     * than or equal to the given argument
     * @throws NullPointerException if {@code value} is {@code null}
     * @see #isNull()
     * @see #isNotNull()
     */
    public static <T2, T1 extends Comparable<T2>> Predicate1<T1> isLessThanOrEqualTo(T2 value) {
        Objects.requireNonNull(value, "value");
        return t1 -> t1.compareTo(value) <= 0;
    }

    /**
     * Uses {@link Comparator#compare(Object, Object)} to determine if the tested argument
     * is less than or equal to the given argument.
     *
     * @param value      the value to check the tested argument against
     * @param comparator comparator used to determine order
     * @param <T1>       tested argument and given argument type
     * @return {@link Predicate1} instance testing whether the tested argument is less
     * than or equal to the given argument
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws NullPointerException if {@code comparator} is {@code null}
     * @see #isNull()
     * @see #isNotNull()
     */
    public static <T1> Predicate1<T1> isLessThanOrEqualTo(T1 value, Comparator<T1> comparator) {
        Objects.requireNonNull(value, "value");
        Objects.requireNonNull(comparator, "comparator");
        return t1 -> comparator.compare(t1, value) <= 0;
    }

    /**
     * Uses {@link Comparable#compareTo(Object)} to determine if the tested argument
     * is equal to the given argument.
     *
     * @param value the value to check the tested argument against
     * @param <T2>  given argument type
     * @param <T1>  tested argument type
     * @return {@link Predicate1} instance testing whether the tested argument is
     * equal to the given argument
     * @throws NullPointerException if {@code value} is {@code null}
     * @see #isNull()
     * @see #isNotNull()
     */
    public static <T2, T1 extends Comparable<T2>> Predicate1<T1> isEqualTo(T2 value) {

        return t1 -> t1.compareTo(value) == 0;
    }

    /**
     * Uses {@link Comparable#compareTo(Object)} to determine if the tested argument
     * is equal to the given argument.
     *
     * @param value      the value to check the tested argument against
     * @param comparator comparator used to determine order
     * @param <T1>       tested argument and given argument type
     * @return {@link Predicate1} instance testing whether the tested argument is
     * equal to the given argument
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws NullPointerException if {@code comparator} is {@code null}
     * @see #isNull()
     * @see #isNotNull()
     */
    public static <T1> Predicate1<T1> isEqualTo(T1 value, Comparator<T1> comparator) {
        Objects.requireNonNull(comparator, "comparator");
        return t1 -> comparator.compare(t1, value) == 0;
    }

    /**
     * Uses {@link Comparable#compareTo(Object)} to determine if the tested argument
     * is not equal to the given argument.
     *
     * @param value the value to check the tested argument against
     * @param <T2>  given argument type
     * @param <T1>  tested argument type
     * @return {@link Predicate1} instance testing whether the tested argument is
     * not equal to the given argument
     * @throws NullPointerException if {@code value} is {@code null}
     * @see #isNull()
     * @see #isNotNull()
     */
    public static <T2, T1 extends Comparable<T2>> Predicate1<T1> isNotEqualTo(T2 value) {
        Objects.requireNonNull(value, "value");
        return t1 -> t1.compareTo(value) != 0;
    }

    /**
     * Uses {@link Comparable#compareTo(Object)} to determine if the tested argument
     * is not equal to the given argument.
     *
     * @param value      the value to check the tested argument against
     * @param comparator comparator used to determine order
     * @param <T1>       tested argument and given argument type
     * @return {@link Predicate1} instance testing whether the tested argument is
     * not equal to the given argument
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws NullPointerException if {@code comparator} is {@code null}
     * @see #isNull()
     * @see #isNotNull()
     */
    public static <T1> Predicate1<T1> isNotEqualTo(T1 value, Comparator<T1> comparator) {
        Objects.requireNonNull(value, "value");
        Objects.requireNonNull(comparator, "comparator");
        return t1 -> comparator.compare(t1, value) != 0;
    }

    /**
     * A {@link Predicate1} to determine if the tested argument is an instance
     * of the given {@link Class} type.
     *
     * @param clazz the {@link Class} type to check if the tested argument is an instance of
     * @param <T1>  tested argument type
     * @return {@link Predicate1} instance testing whether the tested argument is an instance
     * of the given {@link Class} type
     * @throws NullPointerException if {@code clazz} is {@code null}
     */
    public static <T1> Predicate1<T1> instanceOf(Class<?> clazz) {
        Objects.requireNonNull(clazz, "clazz");
        return clazz::isInstance;
    }

    /**
     * Returns a predicate that extracts a property from {@code T} and tests the extracted property
     * against the given predicate.
     *
     * @param extractor the {@code Function1} instance used to map {@code T} to {@code U}
     * @param predicate the {@code Predicate1} instance to test {@code T} against
     * @param <T1>      the input type
     * @param <R>      the extracted property type to test
     * @return a {@code Predicate1} instance that extracts a property from {@code T} and tests the
     * extracted property against the given predicate.
     * @throws NullPointerException if {@code extractor} is null
     * @throws NullPointerException if {@code predicate} is null
     * @deprecated use {@link #testPropertyOrElse(Function1, Predicate1, boolean)} for clarity
     */
    @Deprecated
    public static <T1, R> Predicate1<T1> testProperty(Function1<T1, R> extractor, Predicate1<R> predicate) {
        Objects.requireNonNull(extractor, "extractor");
        Objects.requireNonNull(predicate, "predicate");
        return testProperty(SafeGetter.of(extractor), predicate);
    }

    /**
     * Returns a predicate that extracts a property from {@code T} and tests the extracted property
     * against the given predicate.
     *
     * @param getter    the {@code SafeGetter} instance used to map {@code T} to {@code U}
     * @param predicate the {@code Predicate1} instance to test {@code T} against
     * @param <T1>      the input type
     * @param <T2>      the extracted property type to test
     * @return a {@code Predicate1} instance that extracts a property from {@code T} and tests the
     * extracted property against the given predicate.
     * @throws NullPointerException if {@code extractor} is null
     * @throws NullPointerException if {@code predicate} is null
     * @deprecated use {@link #testPropertyOrElse(SafeGetter, Predicate1, boolean)} for clarity
     */
    @Deprecated
    public static <T1, T2> Predicate1<T1> testProperty(SafeGetter<T1, T2> getter, Predicate1<T2> predicate) {
        Objects.requireNonNull(getter, "getter");
        Objects.requireNonNull(predicate, "predicate");
        return in -> getter.get(in)
            .filter(predicate)
            .isPresent();
    }

    /**
     * Returns a predicate that extracts a property from {@code T} and tests the extracted property
     * against the given predicate. If the extracted property is null, the default value is returned
     * from the predicate.
     *
     * @param extractor    the {@code Function1} instance used to map {@code T} to {@code U}
     * @param predicate    the {@code Predicate1} instance to test {@code T} against
     * @param defaultValue the default value to return from the predicate if the sub-property is null
     * @param <T1>         the input type
     * @param <T2>         the extracted property type to test
     * @return a {@code Predicate1} instance that extracts a property from {@code T} and tests the
     * extracted property against the given predicate.
     * @throws NullPointerException if {@code extractor} is null
     * @throws NullPointerException if {@code predicate} is null
     */
    public static <T1, T2> Predicate1<T1> testPropertyOrElse(Function1<T1, T2> extractor, Predicate1<T2> predicate, boolean defaultValue) {
        return testPropertyOrElse(SafeGetter.of(extractor), predicate, defaultValue);
    }

    /**
     * Returns a predicate that extracts a property from {@code T} and tests the extracted property
     * against the given predicate. If the extracted property is null, the default value is returned
     * from the predicate.
     *
     * @param getter       the {@code SafeGetter} instance used to map {@code T} to {@code U}
     * @param predicate    the {@code Predicate1} instance to test {@code T} against
     * @param defaultValue the default value to return from the predicate if the sub-property is null
     * @param <T1>         the input type
     * @param <T2>         the extracted property type to test
     * @return a {@code Predicate1} instance that extracts a property from {@code T} and tests the
     * extracted property against the given predicate.
     * @throws NullPointerException if {@code getter} is null
     * @throws NullPointerException if {@code predicate} is null
     */
    public static <T1, T2> Predicate1<T1> testPropertyOrElse(SafeGetter<T1, T2> getter, Predicate1<T2> predicate, boolean defaultValue) {
        Objects.requireNonNull(getter, "getter");
        Objects.requireNonNull(predicate, "predicate");
        return in -> getter.get(in)
            .map(predicate::test)
            .getOrElse(defaultValue);
    }

    /**
     * Returns a predicate that extracts a property from {@code T} and tests the extracted property
     * against the given predicate. If the extracted property is null, the default value returned
     * from the predicate is {@code true}.
     *
     * @param extractor the {@code Function1} instance used to map {@code T} to {@code U}
     * @param predicate the {@code Predicate1} instance to test {@code T} against
     * @param <T1>      the input type
     * @param <T2>      the extracted property type to test
     * @return a {@code Predicate1} instance that extracts a property from {@code T} and tests the
     * extracted property against the given predicate.
     * @throws NullPointerException if {@code extractor} is null
     * @throws NullPointerException if {@code predicate} is null
     */
    public static <T1, T2> Predicate1<T1> testPropertyOrElseTrue(Function1<T1, T2> extractor, Predicate1<T2> predicate) {
        return testPropertyOrElse(extractor, predicate, true);
    }

    /**
     * Returns a predicate that extracts a property from {@code T} and tests the extracted property
     * against the given predicate. If the extracted property is null, the default value returned
     * from the predicate is {@code true}.
     *
     * @param getter    the {@code SafeGetter} instance used to map {@code T} to {@code U}
     * @param predicate the {@code Predicate1} instance to test {@code T} against
     * @param <T1>      the input type
     * @param <T2>      the extracted property type to test
     * @return a {@code Predicate1} instance that extracts a property from {@code T} and tests the
     * extracted property against the given predicate.
     * @throws NullPointerException if {@code getter} is null
     * @throws NullPointerException if {@code predicate} is null
     */
    public static <T1, T2> Predicate1<T1> testPropertyOrElseTrue(SafeGetter<T1, T2> getter, Predicate1<T2> predicate) {
        return testPropertyOrElse(getter, predicate, true);
    }

    /**
     * Returns a predicate that extracts a property from {@code T} and tests the extracted property
     * against the given predicate. If the extracted property is null, the default value returned
     * from the predicate is {@code false}.
     *
     * @param extractor the {@code Function1} instance used to map {@code T} to {@code U}
     * @param predicate the {@code Predicate1} instance to test {@code T} against
     * @param <T1>      the input type
     * @param <T2>      the extracted property type to test
     * @return a {@code Predicate1} instance that extracts a property from {@code T} and tests the
     * extracted property against the given predicate.
     * @throws NullPointerException if {@code extractor} is null
     * @throws NullPointerException if {@code predicate} is null
     */
    public static <T1, T2> Predicate1<T1> testPropertyOrElseFalse(Function1<T1, T2> extractor, Predicate1<T2> predicate) {
        return testPropertyOrElse(extractor, predicate, false);
    }

    /**
     * Returns a predicate that extracts a property from {@code T} and tests the extracted property
     * against the given predicate. If the extracted property is null, the default value returned
     * from the predicate is {@code false}.
     *
     * @param getter    the {@code SafeGetter} instance used to map {@code T} to {@code U}
     * @param predicate the {@code Predicate1} instance to test {@code T} against
     * @param <T1>      the input type
     * @param <T2>      the extracted property type to test
     * @return a {@code Predicate1} instance that extracts a property from {@code T} and tests the
     * extracted property against the given predicate.
     * @throws NullPointerException if {@code getter} is null
     * @throws NullPointerException if {@code predicate} is null
     */
    public static <T1, T2> Predicate1<T1> testPropertyOrElseFalse(SafeGetter<T1, T2> getter, Predicate1<T2> predicate) {
        return testPropertyOrElse(getter, predicate, false);
    }
}
