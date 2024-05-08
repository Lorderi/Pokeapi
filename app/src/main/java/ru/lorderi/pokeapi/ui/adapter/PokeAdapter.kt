package ru.lorderi.pokeapi.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.lorderi.pokeapi.databinding.CardPokeBinding
import ru.lorderi.pokeapi.model.ui.PokemonUi

class PokeAdapter(
    val onDescriptionClicked: (pokemon: PokemonUi) -> Unit,
) : ListAdapter<PokemonUi, PokeViewHolder>(PokeDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CardPokeBinding.inflate(layoutInflater, parent, false)

        val viewHolder = PokeViewHolder(binding)

        binding.root.setOnClickListener {
            onDescriptionClicked(getItem(viewHolder.adapterPosition))
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: PokeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}