package ru.lorderi.pokeapi.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import ru.lorderi.pokeapi.model.ui.PokemonUi
import ru.lorderi.pokeapi.pokeapi.PokeApi
import ru.lorderi.pokeapi.ui.adapter.PokePagingSource
import ru.lorderi.pokeapi.util.Constants.INITIAL_LOAD_PAGE
import ru.lorderi.pokeapi.util.Constants.PAGE_SIZE

class PokeViewModel(private val pokeApi: PokeApi) : ViewModel() {
    val pokemons: Flow<PagingData<PokemonUi>> = selectPokemons()
    private fun selectPokemons(): Flow<PagingData<PokemonUi>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = INITIAL_LOAD_PAGE
            ),
            pagingSourceFactory = { PokePagingSource(pokeApi) }
        ).flow.cachedIn(viewModelScope)
    }
}
