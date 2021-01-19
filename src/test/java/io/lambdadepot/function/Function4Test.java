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

class Function4Test {

    @Test
    void testOf() {
        assertNotNull(Function4.of(TestFunctions::concat4));

        assertThrows(NullPointerException.class, () -> Function4.of(null));
    }

    @Test
    void testApply() {
        assertEquals("ABCD", Function4.of(TestFunctions::concat4)
                .apply("A", "B", "C", "D"));

    }

    @Test
    void testPartialApply() {
        assertEquals("ABCD", Function4.of(TestFunctions::concat4)
                .partialApply("A")
                .apply("B", "C", "D"));

        assertEquals("ABCD", Function4.of(TestFunctions::concat4)
                .partialApply("A", "B")
                .apply("C", "D"));

        assertEquals("ABCD", Function4.of(TestFunctions::concat4)
                .partialApply("A", "B", "C")
                .apply("D"));

        assertEquals("ABCD", Function4.of(TestFunctions::concat4)
                .partialApply("A", "B", "C", "D")
                .apply());
    }

    @Test
    void testReverse() {
        assertEquals("DCBA", Function4.of(TestFunctions::concat4)
                .reverse()
                .apply("A", "B", "C", "D"));
    }

    @Test
    void testCurry() {
        Function1<String, Function3<String, String, String, String>> curried = Function4.of(TestFunctions::concat4)
                .curry();
        assertNotNull(curried);
        assertEquals("ABCD", curried
                .apply("A")
                .apply("B", "C", "D"));
        assertEquals("ABCD", curried
                .apply("A")
                .curry().apply("B")
                .apply("C", "D"));
        assertEquals("ABCD", curried
                .apply("A")
                .curry().apply("B")
                .curry().apply("C")
                .apply("D"));
    }

    @Test
    void testAndThen() {
        assertEquals("ABCD", Function4.of(TestFunctions::concat4)
                .thenApply(TestFunctions::toUpperCase)
                .apply("a", "b", "c", "d"));
        assertThrows(NullPointerException.class, () ->
                Function4.of(TestFunctions::concat4)
                        .thenApply(null));
    }

    @Test
    void testLift() {
        Function4<String, String, String, String, String> fn = Function4.of(TestFunctions::concat4Strings);
        assertTrue(fn.lift().apply("", "", "", "") instanceof ResultSuccess);
        Assertions.assertEquals("abcd", fn.lift().apply("a", "b", "c", "d").getValue());
        assertTrue(fn.lift().apply(null, null, null, null) instanceof ResultFailure);
        Function4<String, String, String, String, String> empty = (a, b, c, d) -> null;
        Assertions.assertEquals(Result.empty(), empty.lift().apply("", "", " d", "asdf"));
    }
}
