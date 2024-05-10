package ru.lorderi.pokeapi.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.lorderi.pokeapi.model.ui.PokemonUi

class PokeDiffCallback : DiffUtil.ItemCallback<PokemonUi>() {
    override fun areItemsTheSame(oldItem: PokemonUi, newItem: PokemonUi): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: PokemonUi, newItem: PokemonUi): Boolean =
        oldItem == newItem

}