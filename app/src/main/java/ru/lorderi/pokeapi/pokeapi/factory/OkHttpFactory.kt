package ru.lorderi.pokeapi.pokeapi.factory

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import ru.lorderi.pokeapi.BuildConfig
import java.util.concurrent.TimeUnit

object OkHttpFactory {

    val INSTANCE by lazy {
        OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .let {
                if (BuildConfig.DEBUG) {
                    it.addInterceptor(
                        HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        }
                    )
                } else {
                    it
                }
            }
            .build()
    }
}