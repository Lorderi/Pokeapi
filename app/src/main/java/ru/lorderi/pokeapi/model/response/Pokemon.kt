package ru.lorderi.pokeapi.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonList(
    @SerialName("results")
    val pokemons: List<Pokemon>
)

@Serializable
data class Pokemon(
    @SerialName("name")
    val name: String,
    @SerialName("url")
    val url: String,
)
