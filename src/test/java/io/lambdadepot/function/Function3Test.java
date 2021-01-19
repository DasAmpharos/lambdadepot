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

package io.lambdadepot.function;

import io.lambdadepot.util.Result;
import io.lambdadepot.util.ResultFailure;
import io.lambdadepot.util.ResultSuccess;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Function3Test {

    @Test
    void testOf() {
        assertNotNull(Function3.of(TestFunctions::concat3));
        assertThrows(NullPointerException.class, () ->
                Function3.of(null));
    }

    @Test
    void testApply() {
        assertEquals("ABC", Function3.of(TestFunctions::concat3)
                .apply("A", "B", "C"));
    }

    @Test
    void testPartialApply() {
        assertEquals("ABC", Function3.of(TestFunctions::concat3)
                .partialApply("A")
                .apply("B", "C"));

        assertEquals("ABC", Function3.of(TestFunctions::concat3)
                .partialApply("A", "B")
                .apply("C"));

        assertEquals("ABC", Function3.of(TestFunctions::concat3)
                .partialApply("A", "B", "C")
                .apply());
    }

    @Test
    void testReverse() {
        assertEquals("ABC", Function3.of(TestFunctions::concat3)
                .apply("A", "B", "C"));

        assertEquals("CBA", Function3.of(TestFunctions::concat3)
                .reverse()
                .apply("A", "B", "C"));
    }

    @Test
    void testCurry() {
        Function1<String, Function2<String, String, String>> curried = Function3.of(TestFunctions::concat3)
                .curry();
        assertNotNull(curried);
        assertEquals("ABC", curried
                .apply("A")
                .apply("B", "C"));
        assertEquals("ABC", curried
                .apply("A")
                .curry().apply("B")
                .apply("C"));
    }

    @Test
    void testAndThen() {
        assertEquals("ABC", Function3.of(TestFunctions::concat3)
                .thenApply(TestFunctions::toUpperCase)
                .apply("A", "B", "C"));
        assertThrows(NullPointerException.class, () ->
                Function3.of(TestFunctions::concat3)
                        .thenApply(null));
    }

    @Test
    void testLift() {
        Result<String> result = Function3.of(TestFunctions::concat3Strings).lift().apply("a", "b", "c");
        assertTrue(result instanceof ResultSuccess);
        assertEquals("abc", result.getValue());
        result = Function3.of(TestFunctions::concat3Strings).lift().apply(null, null, null);
        assertTrue(result instanceof ResultFailure);
        Function3<String, String, String, String> empty = (s1, s2, s3) -> null;
        Assertions.assertEquals(Result.empty(), empty.lift().apply("", "", ""));
    }
}
