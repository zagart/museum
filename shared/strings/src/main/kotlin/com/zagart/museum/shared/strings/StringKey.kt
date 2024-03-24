package com.zagart.museum.shared.strings

import kotlin.reflect.KClass

data class StringKey(val key : String, val clazz : KClass<out Any>)