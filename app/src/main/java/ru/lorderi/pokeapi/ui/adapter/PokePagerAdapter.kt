package ru.lorderi.pokeapi.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import ru.lorderi.pokeapi.databinding.CardPokeBinding
import ru.lorderi.pokeapi.model.ui.PokemonUi

class PokePagerAdapter(
    val onDescriptionClicked: (pokemon: PokemonUi?) -> Unit,
) : PagingDataAdapter<PokemonUi, PokeViewHolder>(PokeDiffCallback()) {
    override fun onBindViewHolder(holder: PokeViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CardPokeBinding.inflate(layoutInflater, parent, false)

        val viewHolder = PokeViewHolder(binding)

        binding.root.setOnClickListener {
            onDescriptionClicked(getItem(viewHolder.getBindingAdapterPosition()))
        }

        return viewHolder
    }

}