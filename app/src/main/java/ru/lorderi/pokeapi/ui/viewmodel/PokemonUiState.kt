package ru.lorderi.pokeapi.ui.viewmodel

import ru.lorderi.pokeapi.model.ui.PokemonUi

data class PokemonUiState(
    val pokemonList: List<PokemonUi> = emptyList()
)