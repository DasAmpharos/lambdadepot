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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.junit.jupiter.api.Test;

import static io.lambdadepot.util.Predicates.instanceOf;
import static io.lambdadepot.util.Predicates.not;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ResultSuccessTest {

    @Test
    void map() {
        Result<Integer> result = Result.success("10").map(Integer::parseInt);
        assertEquals(10, result.getValue());
        assertThrows(NullPointerException.class, () -> result.map(null));
    }

    @Test
    void flatMap() {
        Result<Integer> result = Result.success("10").flatMap(s -> Result.successOrEmpty(s).map(Integer::parseInt));
        assertEquals(10, result.getValue());
        assertThrows(NullPointerException.class, () -> result.flatMap(null));
    }

    @Test
    void filter() {
        List<Integer> list = Arrays.asList(10, 20);
        Result<List<Integer>> result = Result.success(list).filter(not(List::isEmpty));
        assertTrue(result.getValue().containsAll(list));
        assertThrows(NullPointerException.class, () -> result.filter(null));
    }

    @Test
    void ifFailureMap() {
        Result<Integer> result = Result.success(10).ifFailureMap(t -> new IllegalArgumentException(t.getMessage()));
        assertEquals(10, result.getValue());
        assertThrows(NullPointerException.class, () -> result.ifFailureMap(null));
    }

    @Test
    void testIfFailureMap() {
        Result<Integer> result = Result.success(10).ifFailureMap(NullPointerException.class, t -> new IllegalArgumentException(t.getMessage()));
        assertEquals(10, result.getValue());
        assertThrows(NullPointerException.class, () -> result.ifFailureMap(NullPointerException.class, null));
    }

    @Test
    void testIfFailureMap1() {
        Result<Integer> result = Result.success(10).ifFailureMap(instanceOf(NullPointerException.class), t -> new IllegalArgumentException(t.getMessage()));
        assertEquals(10, result.getValue());
        assertThrows(NullPointerException.class, () -> result.ifFailureMap(instanceOf(NullPointerException.class), null));
    }

    @Test
    void ifFailureResume() {
        Result<Integer> result = Result.success(10).ifFailureResume(t -> Result.success(20));
        assertEquals(10, result.getValue());
        assertThrows(NullPointerException.class, () -> result.ifFailureMap(null));
    }

    @Test
    void testIfFailureResume() {
        Result<Integer> result = Result.success(10).ifFailureResume(NullPointerException.class, t -> Result.success(20));
        assertEquals(10, result.getValue());
        assertThrows(NullPointerException.class, () -> result.ifFailureMap(NullPointerException.class, null));
    }

    @Test
    void testIfFailureResume1() {
        Result<Integer> result = Result.success(10).ifFailureResume(instanceOf(NullPointerException.class), t -> Result.success(20));
        assertEquals(10, result.getValue());
        assertThrows(NullPointerException.class, () -> result.ifFailureMap(instanceOf(NullPointerException.class), null));
    }

    @Test
    void ifFailureReturn() {
        Result<Integer> result = Result.success(10).ifFailureReturn(20);
        assertEquals(10, result.getValue());
        assertThrows(NullPointerException.class, () -> {
            Integer fallback = null;
            result.ifFailureReturn(fallback);
        });
    }

    @Test
    void testIfFailureReturn() {
        Result<Integer> result = Result.success(10).ifFailureReturn(NumberFormatException.class, 20);
        assertThrows(NullPointerException.class, () -> {
            Integer fallback = null;
            result.ifFailureReturn(NumberFormatException.class, fallback);
        });
    }

    @Test
    void testIfFailureReturn1() {
        Result<Integer> result = Result.success(10).ifFailureReturn(instanceOf(NumberFormatException.class), 20);
        assertThrows(NullPointerException.class, () -> {
            Integer fallback = null;
            result.ifFailureReturn(instanceOf(NumberFormatException.class), fallback);
        });
    }

    @Test
    void testIfFailureReturn2() {
        Result<Integer> result = Result.success(10).ifFailureReturn(() -> 20);
        assertThrows(NullPointerException.class, () -> {
            Supplier<Integer> fallback = null;
            result.ifFailureReturn(fallback);
        });
    }

    @Test
    void testIfFailureReturn3() {
        Result<Integer> result = Result.success(10).ifFailureReturn(NullPointerException.class, () -> 20);
        assertThrows(NullPointerException.class, () -> {
            Supplier<Integer> fallback = null;
            result.ifFailureReturn(NumberFormatException.class, fallback);
        });
    }

    @Test
    void testIfFailureReturn4() {
        Result<Integer> result = Result.success(10).ifFailureReturn(instanceOf(NullPointerException.class), () -> 20);
        assertThrows(NullPointerException.class, () -> {
            Supplier<Integer> fallback = null;
            result.ifFailureReturn(instanceOf(NullPointerException.class), fallback);
        });
    }

    @Test
    void ifEmptyReturn() {
        Result<Integer> result = Result.success(10).ifEmptyReturn(20);
        assertEquals(10, result.getValue());
        assertThrows(NullPointerException.class, () -> {
            Integer integer = null;
            result.ifEmptyReturn(integer);
        });
    }

    @Test
    void testIfEmptyReturn() {
        Result<Integer> result = Result.success(10).ifEmptyReturn(() -> 20);
        assertEquals(10, result.getValue());
        assertThrows(NullPointerException.class, () -> {
            Supplier<Integer> integer = null;
            result.ifEmptyReturn(integer);
        });
    }

    @Test
    void ifEmptyResume() {
        Result<Integer> result = Result.success(10).ifEmptyResume(Result.success(20));
        assertEquals(10, result.getValue());
        assertThrows(NullPointerException.class, () -> {
            Result<Integer> integerResult = null;
            result.ifEmptyResume(integerResult);
        });
    }

    @Test
    void testIfEmptyResume() {
        Result<Integer> result = Result.success(10).ifEmptyResume(() -> Result.success(20));
        assertEquals(10, result.getValue());
        assertThrows(NullPointerException.class, () -> {
            Supplier<Result<Integer>> integerResult = null;
            result.ifEmptyResume(integerResult);
        });
    }

    @Test
    void peekIfSuccess() {
        List<Integer> list = new ArrayList<>();
        Result<Integer> result = Result.success(10).peekIfSuccess(list::add);
        assertFalse(list.isEmpty());
        assertTrue(list.contains(result.getValue()));
        assertThrows(NullPointerException.class, () -> {
            Consumer<Integer> consumer = null;
            result.peekIfSuccess(consumer);
        });
    }

    @Test
    void peekIfFailure() {
        List<Throwable> list = new ArrayList<>();
        Result<Integer> result = Result.success(10).peekIfFailure(list::add);
        assertTrue(list.isEmpty());
        assertThrows(NullPointerException.class, () -> {
            Consumer<Integer> consumer = null;
            result.peekIfSuccess(consumer);
        });
    }

    @Test
    void testPeekIfFailure() {
        List<Throwable> list = new ArrayList<>();
        Result<Integer> result = Result.success(10).peekIfFailure(NumberFormatException.class, list::add);
        assertTrue(list.isEmpty());
        assertThrows(NullPointerException.class, () -> {
            Consumer<Throwable> consumer = null;
            result.peekIfFailure(NumberFormatException.class, consumer);
        });
    }

    @Test
    void testPeekIfFailure1() {
        List<Throwable> list = new ArrayList<>();
        Result<Integer> result = Result.success(10).peekIfFailure(instanceOf(NumberFormatException.class), list::add);
        assertTrue(list.isEmpty());
        assertThrows(NullPointerException.class, () -> {
            Consumer<Throwable> consumer = null;
            result.peekIfFailure(instanceOf(NumberFormatException.class), consumer);
        });
    }

    @Test
    void peekIfEmpty() {
        List<Integer> list = new ArrayList<>();
        Result<Integer> result = Result.success(10).peekIfEmpty(() -> list.add(20));
        assertTrue(list.isEmpty());
        assertFalse(list.contains(20));
        assertThrows(NullPointerException.class, () -> {
            Runnable runnable = null;
            result.peekIfEmpty(runnable);
        });
    }

    @Test
    void ifSuccess() {
        List<Integer> list = new ArrayList<>();
        Result.success(10).ifSuccess(list::add);
        assertFalse(list.isEmpty());
        assertTrue(list.contains(10));
        assertThrows(NullPointerException.class, () -> {
            Consumer<Integer> consumer = null;
            Result.success(10).ifSuccess(consumer);
        });
    }

    @Test
    void ifSuccessOrFailure() {

        List<Integer> integers = new ArrayList<>();
        List<Throwable> throwables = new ArrayList<>();
        Result.success(10).ifSuccessOrElse(integers::add, throwables::add);
        assertFalse(integers::isEmpty);
        assertTrue(integers.contains(10));
        assertTrue(throwables::isEmpty);
        assertThrows(NullPointerException.class, () -> {
            Consumer<Integer> integerConsumer = null;
            Consumer<Throwable> throwableConsumer = null;
            Result.success(10).ifSuccessOrElse(integerConsumer, throwableConsumer);
        });
    }

    @Test
    void ifSuccessOrEmpty() {
        List<String> list = new ArrayList<>();
        Result.success("success").ifSuccessOrEmpty(list::add, () -> list.add("empty"));
        assertEquals(1, list.size());
        assertEquals("success", list.get(0));
        assertThrows(NullPointerException.class, () -> {
            Consumer<String> consumer = null;
            Runnable runnable = null;
            Result.success("success").ifSuccessOrEmpty(consumer, runnable);
        });
    }

    @Test
    void ifSuccessOrElse() {
        List<String> strings = new ArrayList<>();
        List<Throwable> throwables = new ArrayList<>();
        Result.success("success").ifSuccessOrElse(strings::add, throwables::add, () -> strings.add("empty"));
        assertFalse(strings.contains("empty"));
        assertTrue(throwables.isEmpty());
        assertTrue(strings.contains("success"));
        assertThrows(NullPointerException.class, () -> {
            Consumer<String> consumer = null;
            Consumer<Throwable> consumer1 = null;
            Runnable runnable = null;
            Result.success(";)").ifSuccessOrElse(consumer, consumer1, runnable);
        });
    }

    @Test
    void ifFailure() {
        List<Throwable> throwables = new ArrayList<>();
        Result.success("success").ifFailure(throwables::add);
        assertTrue(throwables::isEmpty);
        assertThrows(NullPointerException.class, () -> {
            Consumer<Throwable> consumer = null;
            Result.success(";)").ifFailure(consumer);
        });
    }

    @Test
    void ifFailureOrEmpty() {
        List<String> strings = new ArrayList<>();
        List<Throwable> throwables = new ArrayList<>();
        Result.success("success").ifFailureOrEmpty(throwables::add, () -> strings.add("empty"));
        assertFalse(strings.contains("empty"));
        assertTrue(throwables.isEmpty());
        assertThrows(NullPointerException.class, () -> {
            Consumer<Throwable> consumer = null;
            Runnable runnable = null;
            Result.success(";)").ifFailureOrEmpty(consumer, runnable);
        });
    }

    @Test
    void ifEmpty() {
        List<Integer> integers = new ArrayList<>();
        Result.success(10).ifEmpty(() -> integers.add(20));
        assertTrue(integers::isEmpty);
        assertThrows(NullPointerException.class, () -> {
            Runnable runnable = null;
            Result.success("success").ifEmpty(runnable);
        });
    }

    @Test
    void isSuccess() {
        assertTrue(Result.success(10).isSuccess());
    }

    @Test
    void isFailure() {
        assertFalse(Result.success(10).isFailure());
    }

    @Test
    void isEmpty() {
        assertFalse(Result.success(10).isEmpty());
    }

    @Test
    void getValue() {
        assertEquals(10, Result.success(10).getValue());
    }

    @Test
    void getError() {
        assertThrows(NoSuchElementException.class, () -> Result.success(10).getException());
    }

    @Test
    void orElseThrow() {
        assertEquals(10, Result.success(10).orElseThrow(() -> new NumberFormatException("fail")));
        assertThrows(NullPointerException.class, () -> {
            Supplier<Throwable> supplier = null;
            Result.success(10).orElseThrow(supplier);
        });
    }

    @Test
    void testOrElseThrow() {
        assertEquals(10, Result.success(10).orElseThrow(() -> new NumberFormatException("fail"), () -> new NullPointerException("fail")));
        assertThrows(NullPointerException.class, () -> {
            Supplier<Throwable> supplier = null;
            Result.success(10).orElseThrow(supplier, supplier);
        });
    }
}