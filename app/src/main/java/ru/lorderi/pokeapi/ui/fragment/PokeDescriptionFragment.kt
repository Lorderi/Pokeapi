package ru.lorderi.pokeapi.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.lorderi.pokeapi.R
import ru.lorderi.pokeapi.databinding.FragmentPokeDescriptionBinding
import ru.lorderi.pokeapi.model.ui.PokemonUiDescription
import ru.lorderi.pokeapi.pokeapi.PokeApi
import ru.lorderi.pokeapi.repository.PokemonRepository
import ru.lorderi.pokeapi.ui.viewmodel.PokeDescriptionViewModel


class PokeDescriptionFragment : Fragment() {
    companion object {
        const val DESCRIPTION_POKE_NAME = "DESCRIPTION_POKE_NAME"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPokeDescriptionBinding.inflate(inflater, container, false)


        val pokeViewModel by viewModels<PokeDescriptionViewModel> {
            viewModelFactory {
                initializer {
                    PokeDescriptionViewModel(PokemonRepository(PokeApi.INSTANCE))
                }
            }
        }

        val name = arguments?.getString(DESCRIPTION_POKE_NAME)

        if (name != null) {
            pokeViewModel.loadPokemon(name)
        } else {
            Toast.makeText(requireContext(), "Pokemon not found", Toast.LENGTH_SHORT).show()
        }

        pokeViewModel.state
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                bindDescription(binding, it)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)


        return binding.root
    }

    private fun bindDescription(
        binding: FragmentPokeDescriptionBinding,
        pokemonUiDescription: PokemonUiDescription
    ) {
        binding.avatar.setImageResource(R.drawable.ic_launcher_background)
        binding.hp.text = pokemonUiDescription.hp.toString()
        binding.attack.text = pokemonUiDescription.attack.toString()
        binding.defense.text = pokemonUiDescription.defense.toString()
        binding.specialAttack.text = pokemonUiDescription.specialAttack.toString()
        binding.specialDefense.text = pokemonUiDescription.specialDefense.toString()
        binding.speed.text = pokemonUiDescription.speed.toString()
        binding.types.text = pokemonUiDescription.types.toString()
        binding.weight.text = pokemonUiDescription.weight.toString()
        binding.height.text = pokemonUiDescription.height.toString()
    }


}
