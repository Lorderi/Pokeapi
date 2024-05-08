package ru.lorderi.pokeapi.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import ru.lorderi.pokeapi.R
import ru.lorderi.pokeapi.databinding.CardPokeBinding
import ru.lorderi.pokeapi.model.ui.PokemonUi

class PokeViewHolder(
    private val binding: CardPokeBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(pokemonUi: PokemonUi) {
        binding.name.text = pokemonUi.name
        binding.avatar.setImageResource(R.drawable.ic_launcher_background)
//        binding.avatar = pokemonUi.url
    }
}
