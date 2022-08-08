package com.github.purofle.nmsl.ui.feature.addgame

import com.github.purofle.nmsl.utils.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class AddGameViewModel @Inject constructor() : ViewModel() {
    private val _gameName = MutableStateFlow("")
    val gameName: StateFlow<String> = _gameName

    fun changeGameName(name: String) {
        _gameName.value = name
    }
}