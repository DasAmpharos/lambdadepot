package com.homedepot.lambdadepot.collection

import io.lambdadepot.collection.Tuple1
import io.lambdadepot.collection.Tuple2
import io.lambdadepot.collection.Tuple3
import io.lambdadepot.collection.Tuple4

operator fun <T1> Tuple1<T1>.component1(): T1 = t1

operator fun <T1> Tuple2<T1, *>.component1(): T1 = t1
operator fun <T2> Tuple2<*, T2>.component2(): T2 = t2

operator fun <T1> Tuple3<T1, *, *>.component1(): T1 = t1
operator fun <T2> Tuple3<*, T2, *>.component2(): T2 = t2
operator fun <T3> Tuple3<*, *, T3>.component3(): T3 = t3

operator fun <T1> Tuple4<T1, *, *, *>.component1(): T1 = t1
operator fun <T2> Tuple4<*, T2, *, *>.component2(): T2 = t2
operator fun <T3> Tuple4<*, *, T3, *>.component3(): T3 = t3
operator fun <T4> Tuple4<*, *, *, T4>.component4(): T4 = t4
