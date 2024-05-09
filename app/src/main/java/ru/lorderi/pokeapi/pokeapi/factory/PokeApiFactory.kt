package ru.lorderi.pokeapi.pokeapi.factory

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import ru.lorderi.pokeapi.util.Constants.BASE_URL

object PokeApiFactory {
    private val contentType = "application/json".toMediaType()
    private val json = Json { ignoreUnknownKeys = true }

    val INSTANCE by lazy {
        Retrofit.Builder()
            .client(OkHttpFactory.INSTANCE)
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }
}
