package ru.lorderi.pokeapi.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.lorderi.pokeapi.model.ui.Status
import ru.lorderi.pokeapi.model.ui.toPokemonUiDescription
import ru.lorderi.pokeapi.repository.Repository
import javax.inject.Inject

@HiltViewModel
class PokeDescriptionViewModel @Inject constructor(private val repository: Repository) :
    ViewModel() {
    private val _state = MutableStateFlow(PokemonDescriptionUiState())
    val state = _state.asStateFlow()
    fun loadPokemon(name: String) {
        viewModelScope.launch {
            try {
                _state.update { it.copy(status = Status.Loading) }
                val pokemon = repository.getPokemonDescription(name)
                val uiPokemon = pokemon.toPokemonUiDescription()
                _state.update {
                    it.copy(pokemonUiDescription = uiPokemon, status = Status.Idle)
                }
            } catch (e: Exception) {
                _state.update { it.copy(status = Status.Error(e)) }
            }
        }
    }

    fun consumeError() {
        _state.update { it.copy(status = Status.Idle) }
    }
}
