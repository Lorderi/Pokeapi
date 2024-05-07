package ru.lorderi.pokeapi.model.response

data class Move(
    val move: MoveX,
    val version_group_details: List<VersionGroupDetail>
)