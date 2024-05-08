package ru.lorderi.pokeapi.model.ui

data class PokemonUiDescription(
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
