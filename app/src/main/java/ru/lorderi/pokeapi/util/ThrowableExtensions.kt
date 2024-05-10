package ru.lorderi.pokeapi.util

import retrofit2.HttpException
import java.io.IOException

fun Throwable.toError(): String {
    return when (this) {
        is IOException -> {
            "Network error"
        }

        is HttpException -> {
            "Http error"
        }

        else -> {
            "Unknown"
        }
    }
}