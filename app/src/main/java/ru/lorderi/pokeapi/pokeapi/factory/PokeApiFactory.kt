package ru.lorderi.pokeapi.pokeapi.factory

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

object PokeApiFactory {
    private val contentType = "application/json".toMediaType()
    private val json = Json { ignoreUnknownKeys = true }
    private const val BASE_URL = "https://pokeapi.co/api/v2/"

    val INSTANCE by lazy {
        Retrofit.Builder()
            .client(OkHttpFactory.INSTANCE)
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }
}
