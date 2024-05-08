package ru.lorderi.pokeapi.ui.adapter

import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.lorderi.pokeapi.databinding.CardPokeBinding
import ru.lorderi.pokeapi.model.ui.PokemonUi

class PokeViewHolder(
    private val binding: CardPokeBinding
) : RecyclerView.ViewHolder(binding.root) {

    private fun updateAttachment(url: String) {
        Glide.with(binding.avatar)
            .load(url)
            .into(binding.avatar)
    }

    fun bind(pokemonUi: PokemonUi) {
        binding.name.text = pokemonUi.name
        if (pokemonUi.img.isNotEmpty()) {
            binding.avatar.isVisible = true
            updateAttachment(pokemonUi.img)
        } else {
            binding.avatar.isGone = true
        }
    }
}
