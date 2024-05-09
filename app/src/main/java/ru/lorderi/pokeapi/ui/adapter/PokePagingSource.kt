package ru.lorderi.pokeapi.ui.adapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.lorderi.pokeapi.model.ui.PokemonUi
import ru.lorderi.pokeapi.model.ui.toPokemonUiList
import ru.lorderi.pokeapi.pokeapi.PokeApi

class PokePagingSource(
    private val pokeApi: PokeApi
) : PagingSource<Int, PokemonUi>() {
    override fun getRefreshKey(state: PagingState<Int, PokemonUi>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonUi> {
        return try {
            val pageSize = params.loadSize

            val pageNumber = params.key ?: 0


            val response = pokeApi.getPokemonList(limit = pageSize, offset = pageNumber)
            val pokemonUi = response.toPokemonUiList()
            val nextPageNumber =
                if (pokemonUi.isEmpty()) null else pageNumber + response.pokemons.size
            val prevPageNumber = if (pageNumber > 1) pageNumber - response.pokemons.size else null
            LoadResult.Page(pokemonUi, prevPageNumber, nextPageNumber)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}