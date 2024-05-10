package ru.lorderi.pokeapi.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonDescription(
    @SerialName("id")
    val id: Int,
    @SerialName("height")
    val height: Int,
    @SerialName("name")
    val name: String,
    @SerialName("sprites")
    val sprites: Sprites,
    @SerialName("stats")
    val pokemonDetails: List<Stats>,
    @SerialName("types")
    val types: List<Types>,
    @SerialName("weight")
    val weight: Int,
)

@Serializable
data class Sprites(
    @SerialName("other")
    val other: Other,
)

@Serializable
data class Other(
    @SerialName("official-artwork")
    val officialArtwork: OfficialArtwork,
)

@Serializable
data class OfficialArtwork(
    @SerialName("front_default")
    val img: String,
)

@Serializable
data class Stats(
    @SerialName("base_stat")
    val statValue: Int,
    @SerialName("stat")
    val stat: Stat,
)

@Serializable
data class Stat(
    @SerialName("name")
    val statName: String,
)

@Serializable
data class Types(
    @SerialName("type")
    val type: Type,
)

@Serializable
data class Type(
    @SerialName("name")
    val name: String,
)
