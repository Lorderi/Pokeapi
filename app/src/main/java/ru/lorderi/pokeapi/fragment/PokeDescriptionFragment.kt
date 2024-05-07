package ru.lorderi.pokeapi.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.lorderi.pokeapi.databinding.FragmentPokeDescriptionBinding


class PokeDescriptionFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPokeDescriptionBinding.inflate(inflater, container, false)


        return binding.root
    }


}
