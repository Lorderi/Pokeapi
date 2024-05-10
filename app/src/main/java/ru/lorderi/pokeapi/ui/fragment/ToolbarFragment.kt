package ru.lorderi.pokeapi.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.lorderi.pokeapi.R
import ru.lorderi.pokeapi.databinding.FragmentToolbarBinding
import ru.lorderi.pokeapi.ui.viewmodel.ToolbarViewModel

class ToolbarFragment : Fragment() {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentFragmentManager.beginTransaction()
            .setPrimaryNavigationFragment(this)
            .commit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentToolbarBinding.inflate(inflater, container, false)

        val toolbarViewModel by activityViewModels<ToolbarViewModel>()

        val window = requireActivity().window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        val defaultToolbarColor = R.color.poko //binding.toolbar.solidColor
        val defaultAppBarColor = window.statusBarColor

        toolbarViewModel.color
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { color ->
                if (color == null) {
                    binding.toolbar.setBackgroundResource(defaultToolbarColor)
                    window.statusBarColor = defaultAppBarColor
                } else {
                    binding.toolbar.setBackgroundColor(color)
                    window.statusBarColor = color
                }

            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        val navController =
            requireNotNull(childFragmentManager.findFragmentById(R.id.container)).findNavController()

        binding.toolbar.setupWithNavController(navController)

        return binding.root
    }

}