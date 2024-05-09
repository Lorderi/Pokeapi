package ru.lorderi.pokeapi.util

import java.util.Locale

fun String.replaceFirstChar(): String {
    return this.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
    }
}
