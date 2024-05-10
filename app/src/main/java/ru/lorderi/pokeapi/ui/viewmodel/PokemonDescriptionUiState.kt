package ru.lorderi.pokeapi.ui.viewmodel

import ru.lorderi.pokeapi.model.ui.PokemonUiDescription
import ru.lorderi.pokeapi.model.ui.Status

data class PokemonDescriptionUiState(
    val pokemonUiDescription: PokemonUiDescription? = null,
    val status: Status = Status.Idle,
) {
    fun isRefresh(): Boolean = status == Status.Loading
    fun isError(): Throwable? = (status as? Status.Error)?.reason.takeIf { it != null }
}
