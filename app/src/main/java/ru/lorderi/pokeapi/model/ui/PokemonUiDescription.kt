package ru.lorderi.pokeapi.model.ui

import ru.lorderi.pokeapi.model.response.PokemonDescription
import ru.lorderi.pokeapi.model.response.Types
import java.util.Locale

data class PokemonUiDescription(
    val id: String = "",
    val name: String = "",
    val img: String = "",
    val hp: Int = 0,
    val attack: Int = 0,
    val defense: Int = 0,
    val specialAttack: Int = 0,
    val specialDefense: Int = 0,
    val speed: Int = 0,
    val types: List<String> = emptyList(),
    val weight: Double = 0.0,
    val height: Double = 0.0
)

fun PokemonDescription.toPokemonUiDescription(): PokemonUiDescription {
    val pokemonUiDescription = with(this) {
        PokemonUiDescription(
            id = "N° ${id.toString().padStart(3, '0')}",
            name = replaceFirstChar(name),
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
        listOf(replaceFirstChar(types[0].type.name), replaceFirstChar(types[1].type.name))
    } else {
        listOf(replaceFirstChar(types[0].type.name))
    }
}

private fun replaceFirstChar(t: String): String {
    return t.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
    }
}