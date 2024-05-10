package ru.lorderi.pokeapi.model.ui

import ru.lorderi.pokeapi.model.response.PokemonDescription
import ru.lorderi.pokeapi.model.response.Types
import ru.lorderi.pokeapi.util.replaceFirstChar

data class PokemonUiDescription(
    val id: Int,
    val name: String,
    val img: String,
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val specialAttack: Int,
    val specialDefense: Int,
    val speed: Int,
    val types: List<String>,
    val weight: Double,
    val height: Double
)

fun PokemonDescription.toPokemonUiDescription(): PokemonUiDescription {
    val pokemonUiDescription = with(this) {
        PokemonUiDescription(
            id = id,
            name = name.replaceFirstChar(),
            img = sprites.other.officialArtwork.img,
            hp = pokemonDetails[0].statValue,
            attack = pokemonDetails[1].statValue,
            defense = pokemonDetails[2].statValue,
            specialAttack = pokemonDetails[3].statValue,
            specialDefense = pokemonDetails[4].statValue,
            speed = pokemonDetails[5].statValue,
            types = getTypes(types),
            weight = weight / 10.0,
            height = height / 10.0,
        )

    }
    return pokemonUiDescription
}

private fun getTypes(types: List<Types>): List<String> {
    return if (types.size > 1) {
        listOf(types[0].type.name.replaceFirstChar(), types[1].type.name.replaceFirstChar())
    } else {
        listOf(types[0].type.name.replaceFirstChar())
    }
}
