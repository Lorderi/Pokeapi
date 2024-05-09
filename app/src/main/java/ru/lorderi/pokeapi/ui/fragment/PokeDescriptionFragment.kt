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
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
            .onEach { pokemonUiDescription ->
                if (pokemonUiDescription != null) {
                    bindDescription(binding, pokemonUiDescription)
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)


        return binding.root
    }

    private fun bindDescription(
        binding: FragmentPokeDescriptionBinding,
        pokemonUiDescription: PokemonUiDescription
    ) {
        Glide.with(binding.avatar)
            .load(pokemonUiDescription.img)
            .into(binding.avatar)

        binding.name.text = pokemonUiDescription.name

        "${pokemonUiDescription.weight} KG".also { binding.weight.text = it }
        "${pokemonUiDescription.height} M".also { binding.height.text = it }

        binding.hp.setProgress(pokemonUiDescription.hp, true)
        binding.attack.setProgress(pokemonUiDescription.attack, true)
        binding.defense.setProgress(pokemonUiDescription.defense, true)
        binding.specialAttack.setProgress(pokemonUiDescription.specialAttack, true)
        binding.specialDefense.setProgress(pokemonUiDescription.specialDefense, true)
        binding.speed.setProgress(pokemonUiDescription.speed, true)
//        binding.types.setProgress(pokemonUiDescription.hp, true)
        pokemonUiDescription.types.forEach {
            val typeView = Chip(requireContext())
            typeView.isClickable = false
            typeView.text = it
            binding.types.addView(typeView)
        }
    }
}
