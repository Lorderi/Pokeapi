package ru.lorderi.pokeapi.pokeapi

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.lorderi.pokeapi.model.response.Pokemon
import ru.lorderi.pokeapi.model.response.PokemonList

interface PokeApi {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonList

    @GET("pokemon?limit=1118")
    suspend fun getAllPokemonList(): PokemonList

    @GET("pokemon/{name}")
    suspend fun getPokemonDescription(
        @Path("name") name: String
    ): Pokemon
}
