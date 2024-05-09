package ru.lorderi.pokeapi.ui.fragment

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.chip.Chip
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
        updateAvatar(binding, pokemonUiDescription.img)

        binding.name.text = pokemonUiDescription.name

        binding.weight.text =
            getString(R.string.weight_value, pokemonUiDescription.weight.toString())
        binding.height.text =
            getString(R.string.height_value, pokemonUiDescription.height.toString())

        binding.hp.setProgress(pokemonUiDescription.hp, true)
        binding.hpValue.text =
            getString(R.string.progress_value, pokemonUiDescription.hp, binding.hp.max)

        binding.attack.setProgress(pokemonUiDescription.attack, true)
        binding.attackValue.text =
            getString(R.string.progress_value, pokemonUiDescription.attack, binding.attack.max)

        binding.defense.setProgress(pokemonUiDescription.defense, true)
        binding.defenseValue.text =
            getString(R.string.progress_value, pokemonUiDescription.defense, binding.defense.max)

        binding.specialAttack.setProgress(pokemonUiDescription.specialAttack, true)
        binding.saValue.text =
            getString(
                R.string.progress_value,
                pokemonUiDescription.specialAttack,
                binding.specialAttack.max
            )

        binding.specialDefense.setProgress(pokemonUiDescription.specialDefense, true)
        binding.sdValue.text =
            getString(
                R.string.progress_value,
                pokemonUiDescription.specialDefense,
                binding.specialDefense.max
            )

        binding.speed.setProgress(pokemonUiDescription.speed, true)
        binding.speedValue.text =
            getString(R.string.progress_value, pokemonUiDescription.speed, binding.speed.max)

        pokemonUiDescription.types.forEach {
            val typeView = Chip(requireContext())
            typeView.isClickable = false
            typeView.text = it
            binding.types.addView(typeView)
        }
    }

    private fun updateAvatar(
        binding: FragmentPokeDescriptionBinding,
        url: String
    ) {
        Glide.with(binding.avatar)
            .load(url)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.progress.isVisible = false
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.progress.isVisible = false
                    val palette = Palette.from(resource.toBitmap()).generate()
                    binding.avatar.setBackgroundColor(palette.getDominantColor(Color.TRANSPARENT))
                    return false
                }
            })
            .into(binding.avatar)
    }
}
