package ru.lorderi.pokeapi.ui.adapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import ru.lorderi.pokeapi.model.ui.PokemonUi
import ru.lorderi.pokeapi.model.ui.toPokemonUiList
import ru.lorderi.pokeapi.pokeapi.PokeApi
import java.io.IOException

class PokePagingSource(
    private val pokeApi: PokeApi
) : PagingSource<Int, PokemonUi>() {
    override fun getRefreshKey(state: PagingState<Int, PokemonUi>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
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
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }
}