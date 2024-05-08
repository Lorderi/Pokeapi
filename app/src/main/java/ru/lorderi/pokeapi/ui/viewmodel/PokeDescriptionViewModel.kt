package ru.lorderi.pokeapi.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.lorderi.pokeapi.model.ui.PokemonUiDescription
import ru.lorderi.pokeapi.model.ui.toPokemonUiDescription
import ru.lorderi.pokeapi.repository.Repository

class PokeDescriptionViewModel(private val repository: Repository) : ViewModel() {
    private val _state = MutableStateFlow(PokemonUiDescription())
    val state = _state.asStateFlow()
    fun loadPokemon(name: String) {
        viewModelScope.launch {
            try {
                val pokemon = repository.getPokemonDescription(name)
                val uiPokemon = pokemon.toPokemonUiDescription()
                _state.update {
                    uiPokemon
                }
            } catch (e: Exception) {
                println("ERROR WHILE LOADING POKEMONS")
            }
        }
    }
}
