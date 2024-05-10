package ru.lorderi.pokeapi.repository

import ru.lorderi.pokeapi.model.response.PokemonDescription
import ru.lorderi.pokeapi.model.response.PokemonList
import ru.lorderi.pokeapi.pokeapi.PokeApi
import javax.inject.Inject

class PokemonRepository @Inject constructor(private val api: PokeApi) : Repository {
    override suspend fun getPokemonList(limit: Int, offset: Int): PokemonList =
        api.getPokemonList(limit, offset)

    override suspend fun getAllPokemonList(): PokemonList =
        api.getAllPokemonList()

    override suspend fun getPokemonDescription(name: String): PokemonDescription =
        api.getPokemonDescription(name)
}