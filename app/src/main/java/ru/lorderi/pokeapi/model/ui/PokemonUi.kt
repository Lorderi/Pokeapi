package ru.lorderi.pokeapi.model.ui

import ru.lorderi.pokeapi.model.response.PokemonList
import ru.lorderi.pokeapi.util.Constants.AVATAR_URL_RAW


data class PokemonUi(
    val id: Int,
    val name: String,
    val img: String,
)

fun PokemonList.toPokemonUiList(): List<PokemonUi> {

    val pokemonUiList = this.pokemons.map { pokemon ->
        val arrayUrl = pokemon.url.split("/")
        val id = arrayUrl[arrayUrl.size - 2].toInt()
        with(pokemon) {
            PokemonUi(
                id = id,
                name = name,
                img = "$AVATAR_URL_RAW$id.png",
            )
        }
    }
    return pokemonUiList
}