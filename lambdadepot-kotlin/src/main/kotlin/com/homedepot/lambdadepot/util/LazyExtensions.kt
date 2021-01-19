package com.homedepot.lambdadepot.util

import io.lambdadepot.util.Lazy
import kotlin.reflect.KProperty

operator fun <R> Lazy<R>.getValue(thisRef: Any?, property: KProperty<*>): R = get()