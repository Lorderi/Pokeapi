package ru.lorderi.pokeapi.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.lorderi.pokeapi.R
import ru.lorderi.pokeapi.databinding.FragmentPokeBinding
import ru.lorderi.pokeapi.pokeapi.PokeApi
import ru.lorderi.pokeapi.repository.PokemonRepository
import ru.lorderi.pokeapi.ui.adapter.PokeAdapter
import ru.lorderi.pokeapi.ui.fragment.PokeDescriptionFragment.Companion.DESCRIPTION_POKE_NAME
import ru.lorderi.pokeapi.ui.itemdecoration.OffsetDecoration
import ru.lorderi.pokeapi.ui.viewmodel.PokeViewModel

class PokeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPokeBinding.inflate(inflater, container, false)

        val pokeViewModel by viewModels<PokeViewModel> {
            viewModelFactory {
                initializer {
                    PokeViewModel(PokemonRepository(PokeApi.INSTANCE))
                }
            }
        }

        val adapter = PokeAdapter {
            requireParentFragment()
                .requireParentFragment()
            findNavController()
                .navigate(
                    R.id.action_pokeFragment_to_pokeDescriptionFragment,
                    bundleOf(DESCRIPTION_POKE_NAME to it.name)
                )
        }

        binding.list.adapter = adapter

        binding.list.addItemDecoration(OffsetDecoration(16))

        pokeViewModel.state
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { state ->
                adapter.submitList(state.pokemonList)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        return binding.root
    }


}
