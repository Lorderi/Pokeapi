package ru.lorderi.pokeapi.ui.adapter

import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import ru.lorderi.pokeapi.databinding.CardPokeBinding
import ru.lorderi.pokeapi.model.ui.PokemonUi
import ru.lorderi.pokeapi.util.replaceFirstChar

class PokeViewHolder(
    private val binding: CardPokeBinding
) : RecyclerView.ViewHolder(binding.root) {

    private fun updateAvatar(url: String) {

        Glide.with(binding.avatar)
            .load(url)
            .listener(object : RequestListener<Drawable> {

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.progress.isVisible = true
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    val palette = Palette.from(resource.toBitmap()).generate()
                    binding.avatar.setBackgroundColor(palette.getDominantColor(Color.TRANSPARENT))
                    binding.progress.isVisible = false
                    return false
                }
            })
            .into(binding.avatar)
    }

    fun bind(pokemonUi: PokemonUi) {

        "№ ${pokemonUi.id}".also { binding.number.text = it }
        binding.name.text = pokemonUi.name.replaceFirstChar()
        if (pokemonUi.img.isNotEmpty()) {
            binding.avatar.isVisible = true
            updateAvatar(pokemonUi.img)
        } else {
            binding.avatar.isGone = true
        }
    }
}
