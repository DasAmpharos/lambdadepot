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
import java.util.function.BiFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Function2Test {

    @Test
    void testOf() {
        assertNotNull(Function2.of(TestFunctions::concat2));
        assertThrows(NullPointerException.class, () -> Function2.of(null));
    }

    @Test
    void testAsFunction2() {
        BiFunction<String, String, String> javaBiFunction = TestFunctions::concat2;
        assertNotNull(Function2.asFunction2(javaBiFunction));
        assertThrows(NullPointerException.class, () ->
                Function2.asFunction2(null));
    }

    @Test
    void testReverse() {
        assertEquals("AB", Function2.of(TestFunctions::concat2)
                .apply("A", "B"));
        assertEquals("BA", Function2.of(TestFunctions::concat2)
                .reverse()
                .apply("A", "B"));
    }

    @Test
    void testCurry() {
        Function1<String, Function1<String, String>> curried = Function2.of(TestFunctions::concat2)
                .curry();
        assertNotNull(curried);
        assertEquals("AB", curried
                .apply("A")
                .apply("B"));
    }

    @Test
    void testAndThen() {
        assertEquals("AB", Function2.of(TestFunctions::concat2)
                .thenApply(TestFunctions::toUpperCase)
                .apply("a", "b"));

        assertThrows(NullPointerException.class, () -> Function2.of(TestFunctions::concat2)
                .thenApply(null));
    }

    @Test
    void testPartialApply() {
        assertEquals("AB", Function2.of(TestFunctions::concat2)
                .partialApply("A")
                .apply("B"));

        assertEquals("AB", Function2.of(TestFunctions::concat2)
                .partialApply("A", "B")
                .apply());
    }

    @Test
    void testLift() {
        Function2<String, String, String> f2 = Function2.of(TestFunctions::concat2);
        assertTrue(f2.lift().apply("a", "b") instanceof ResultSuccess);
        Assertions.assertEquals("ab", f2.lift().apply("a", "b").getValue());
        Result<String> result = f2.lift().apply(null, "a");
        assertTrue(result instanceof ResultFailure);
        Function2<String, String, String> empty = (s1, s2) -> null;
        Assertions.assertEquals(Result.empty(), empty.lift().apply("", ""));
    }
}
