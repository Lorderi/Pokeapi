package ru.lorderi.pokeapi.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.lorderi.pokeapi.R
import ru.lorderi.pokeapi.databinding.FragmentPokeBinding
import ru.lorderi.pokeapi.pokeapi.PokeApi
import ru.lorderi.pokeapi.ui.adapter.PokeLoaderStateAdapter
import ru.lorderi.pokeapi.ui.adapter.PokePagerAdapter
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
                    PokeViewModel(PokeApi.INSTANCE)
                }
            }
        }

        val adapter = PokePagerAdapter {
            requireParentFragment()
                .requireParentFragment()
            findNavController()
                .navigate(
                    R.id.action_pokeFragment_to_pokeDescriptionFragment,
                    bundleOf(DESCRIPTION_POKE_NAME to it?.name),
                    NavOptions.Builder()
                        .setEnterAnim(R.anim.fade_in)
                        .setExitAnim(R.anim.fade_out)
                        .build()
                )
        }

        binding.list.adapter = adapter.withLoadStateFooter(PokeLoaderStateAdapter())

        binding.list.addItemDecoration(OffsetDecoration(2, 16, true, 0))

        pokeViewModel.pokemons
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { state ->
                adapter.submitData(state)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        adapter.refresh()

        adapter.addLoadStateListener { state ->
            binding.list.isVisible = state.refresh != LoadState.Loading
            binding.progress.isVisible = state.refresh == LoadState.Loading
        }

        return binding.root
    }


}
