package ru.lorderi.pokeapi.repository

import ru.lorderi.pokeapi.model.response.Pokemon
import ru.lorderi.pokeapi.model.response.PokemonList
import ru.lorderi.pokeapi.pokeapi.PokeApi

class PokemonRepository(private val api: PokeApi) : Repository {
    override suspend fun getPokemonList(limit: Int, offset: Int): PokemonList =
        api.getPokemonList(limit, offset)

    override suspend fun getAllPokemonList(limit: Int, offset: Int): PokemonList =
        api.getAllPokemonList()

    override suspend fun getPokemonDescription(name: String): Pokemon =
        api.getPokemonDescription(name)
}