package ru.lorderi.pokeapi.model.ui

import ru.lorderi.pokeapi.model.response.PokemonList


data class PokemonUi(
    val id: Int,
    val name: String,
    val url: String,
)

fun PokemonList.toPokemonUiList(): List<PokemonUi> {

    val pokemonUiList = this.pokemons.map { pokemon ->
        val arrayUrl = pokemon.url.split("/")
        with(pokemon) {
            PokemonUi(
                id = arrayUrl[arrayUrl.size - 2].toInt(),
                name = name,
                url = url,
            )
        }
    }
    return pokemonUiList
}