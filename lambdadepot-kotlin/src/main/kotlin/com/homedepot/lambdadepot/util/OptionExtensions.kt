package com.homedepot.lambdadepot.util

import io.lambdadepot.util.Option

fun <T> T?.toOption(): Option<T> = Option.ofNullable(this)

fun <T> Option<T>.toNullable(): T? = if (isPresent) get() else null
