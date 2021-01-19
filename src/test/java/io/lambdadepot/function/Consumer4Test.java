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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Consumer4Test extends ConsumerBaseTest {

    @Test
    void testOf() {
        assertNotNull(Consumer4.of(TestConsumers::printOut3));
        assertThrows(NullPointerException.class, () -> Consumer4.of(null));
    }

    @Test
    void testAccept() {
        assertEquals("", outContent.toString());
        Consumer4.of(TestConsumers::printOut3)
                .accept("hello, %s, %s and %s", "A", "B", "C");
        assertEquals("hello, A, B and C", outContent.toString());
    }

    @Test
    void testPartialApply() {
        Consumer4.of(TestConsumers::printOut3)
                .partialApply("hello, %s, %s and %s")
                .accept("A", "B", "C");
        assertEquals("hello, A, B and C", outContent.toString());

        setup();
        Consumer4.of(TestConsumers::printOut3)
                .partialApply("hello, %s, %s and %s", "A")
                .accept("B", "C");
        assertEquals("hello, A, B and C", outContent.toString());

        setup();
        Consumer4.of(TestConsumers::printOut3)
                .partialApply("hello, %s, %s and %s", "A", "B")
                .accept("C");
        assertEquals("hello, A, B and C", outContent.toString());

        setup();
        Consumer4.of(TestConsumers::printOut3)
                .partialApply("hello, %s, %s and %s", "A", "B", "C")
                .accept();
        assertEquals("hello, A, B and C", outContent.toString());
    }

    @Test
    void testAndThen() {
        assertEquals("", outContent.toString());
        assertEquals("", errContent.toString());
        Consumer4.of(TestConsumers::printOut3)
                .thenAccept(TestConsumers::printErr3)
                .accept("hello, %s, %s and %s", "A", "B", "C");
        assertEquals("hello, A, B and C", outContent.toString());
        assertEquals("hello, A, B and C", errContent.toString());

        assertThrows(NullPointerException.class, () -> Consumer4.of(TestConsumers::printOut3)
                .thenAccept(null));
    }

    @Test
    void testReverse() {
        assertEquals("", outContent.toString());
        Consumer4.of(TestConsumers::printOut3).reverse()
                .accept("A", "B", "C", "hello, %s, %s and %s");
        assertEquals("hello, C, B and A", outContent.toString());
    }

}
