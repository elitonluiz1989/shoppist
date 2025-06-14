package com.elitonluiz1989.shoppist.shared.models

open class Route(val value: String) {
    fun withArgs(vararg args: String): String {
        return buildString {
            append(value)

            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}