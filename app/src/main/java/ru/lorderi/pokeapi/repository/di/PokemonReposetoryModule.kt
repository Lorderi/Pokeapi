package ru.lorderi.pokeapi.repository.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.lorderi.pokeapi.repository.PokemonRepository
import ru.lorderi.pokeapi.repository.Repository

@InstallIn(ViewModelComponent::class)
@Module
interface RepositoryModule {
    @Binds
    fun bindsRepository(impl: PokemonRepository): Repository
}
