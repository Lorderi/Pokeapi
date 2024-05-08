package ru.lorderi.pokeapi.repository

import ru.lorderi.pokeapi.model.response.Pokemon
import ru.lorderi.pokeapi.model.response.PokemonList

interface Repository {
    suspend fun getPokemonList(limit: Int, offset: Int): PokemonList
    suspend fun getAllPokemonList(limit: Int, offset: Int): PokemonList
    suspend fun getPokemonDescription(name: String): Pokemon
}
