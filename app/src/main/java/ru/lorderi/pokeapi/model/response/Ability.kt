package ru.lorderi.pokeapi.model.response

data class Ability(
    val ability: AbilityX,
    val is_hidden: Boolean,
    val slot: Int
)