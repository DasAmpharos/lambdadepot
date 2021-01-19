package com.homedepot.lambdadepot.function

import io.lambdadepot.function.Consumer0
import io.lambdadepot.function.Consumer1
import io.lambdadepot.function.Consumer2
import io.lambdadepot.function.Consumer3
import io.lambdadepot.function.Consumer4
import io.lambdadepot.function.Predicate0
import io.lambdadepot.function.Predicate1
import io.lambdadepot.function.Predicate2
import io.lambdadepot.function.Predicate3
import io.lambdadepot.function.Predicate4
import io.lambdadepot.function.Function1 as Fn1
import io.lambdadepot.function.Function2 as Fn2
import io.lambdadepot.function.Function3 as Fn3
import io.lambdadepot.function.Function4 as Fn4

fun (() -> Unit).toConsumer(): Consumer0 =
    Consumer0.of(this::invoke)

fun <T1> ((T1) -> Unit).toConsumer(): Consumer1<T1> =
    Consumer1.of(this::invoke)

fun <T1, T2> ((T1, T2) -> Unit).toConsumer(): Consumer2<T1, T2> =
    Consumer2.of(this::invoke)

fun <T1, T2, T3> ((T1, T2, T3) -> Unit).toConsumer(): Consumer3<T1, T2, T3> =
    Consumer3.of(this::invoke)

fun <T1, T2, T3, T4> ((T1, T2, T3, T4) -> Unit).toConsumer(): Consumer4<T1, T2, T3, T4> =
    Consumer4.of(this::invoke)

fun (() -> Boolean).toPredicate(): Predicate0 =
    Predicate0.of(this::invoke)

fun <T1> ((T1) -> Boolean).toPredicate(): Predicate1<T1> =
    Predicate1.of(this::invoke)

fun <T1, T2> ((T1, T2) -> Boolean).toPredicate(): Predicate2<T1, T2> =
    Predicate2.of(this::invoke)

fun <T1, T2, T3> ((T1, T2, T3) -> Boolean).toPredicate(): Predicate3<T1, T2, T3> =
    Predicate3.of(this::invoke)

fun <T1, T2, T3, T4> ((T1, T2, T3, T4) -> Boolean).toPredicate(): Predicate4<T1, T2, T3, T4> =
    Predicate4.of(this::invoke)

fun <T1, R> ((T1) -> R).toFunction(): Fn1<T1, R> =
    Fn1.of(this::invoke)

fun <T1, T2, R> ((T1, T2) -> R).toFunction(): Fn2<T1, T2, R> =
    Fn2.of(this::invoke)

fun <T1, T2, T3, R> ((T1, T2, T3) -> R).toFunction(): Fn3<T1, T2, T3, R> =
    Fn3.of(this::invoke)

fun <T1, T2, T3, T4, R> ((T1, T2, T3, T4) -> R).toFunction(): Fn4<T1, T2, T3, T4, R> =
    Fn4.of(this::invoke)
