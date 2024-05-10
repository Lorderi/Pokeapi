package ru.lorderi.pokeapi.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ToolbarViewModel : ViewModel() {
    private val _color = MutableStateFlow(ToolbarUiState())
    val color = _color.asStateFlow()

    fun changeBackgroundColor(color: Int?) {
        _color.update {
            it.copy(backgroundColor = color)
        }
    }

}