package ru.lorderi.pokeapi.model.ui

import ru.lorderi.pokeapi.model.response.PokemonList


data class PokemonUi(
    val id: Int,
    val name: String,
    val img: String,
)

private const val URL_RAW =
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"

fun PokemonList.toPokemonUiList(): List<PokemonUi> {

    val pokemonUiList = this.pokemons.map { pokemon ->
        val arrayUrl = pokemon.url.split("/")
        val id = arrayUrl[arrayUrl.size - 2].toInt()
        with(pokemon) {
            PokemonUi(
                id = id,
                name = name,
                img = "$URL_RAW$id.png",
            )
        }
    }
    return pokemonUiList
}