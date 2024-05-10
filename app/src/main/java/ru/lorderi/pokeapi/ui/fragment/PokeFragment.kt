package ru.lorderi.pokeapi.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.lorderi.pokeapi.R
import ru.lorderi.pokeapi.databinding.FragmentPokeBinding
import ru.lorderi.pokeapi.ui.adapter.PokeLoaderStateAdapter
import ru.lorderi.pokeapi.ui.adapter.PokePagerAdapter
import ru.lorderi.pokeapi.ui.fragment.PokeDescriptionFragment.Companion.DESCRIPTION_POKE_NAME
import ru.lorderi.pokeapi.ui.itemdecoration.OffsetDecoration
import ru.lorderi.pokeapi.ui.viewmodel.PokeViewModel
import ru.lorderi.pokeapi.ui.viewmodel.ToolbarViewModel
import ru.lorderi.pokeapi.util.toError

@AndroidEntryPoint
class PokeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPokeBinding.inflate(inflater, container, false)

        val toolbarViewModel by activityViewModels<ToolbarViewModel>()


        toolbarViewModel.changeBackgroundColor(null)

        val pokeViewModel by viewModels<PokeViewModel>()

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

        binding.retry.setOnClickListener {
            adapter.retry()
        }

        adapter.addLoadStateListener { loadState ->
            with(binding) {
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }


                list.isVisible = loadState.refresh != LoadState.Loading
                progress.isVisible = loadState.refresh == LoadState.Loading
                binding.errorArea.isVisible = errorState != null

                errorState?.let {
                    binding.errorArea.isVisible = true
//                    binding.list.isVisible = false
                    binding.errorText.text = it.error.toError()
                }
            }
        }

        return binding.root
    }


}
