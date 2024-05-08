package ru.lorderi.pokeapi.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.lorderi.pokeapi.model.ui.toPokemonUiList
import ru.lorderi.pokeapi.repository.Repository

class PokeViewModel(private val repository: Repository) : ViewModel() {
    private val _state = MutableStateFlow(PokemonUiState())
    val state = _state.asStateFlow()

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            try {
                val pokemons = repository.getAllPokemonList()
                val uiPokemons = pokemons.toPokemonUiList()
                _state.update {
                    it.copy(pokemonList = uiPokemons)
                }
            } catch (e: Exception) {
                println("ERROR WHILE LOADING POKEMONS")
            }
        }
    }
}
