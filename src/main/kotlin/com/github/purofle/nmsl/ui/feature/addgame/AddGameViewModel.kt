package com.github.purofle.nmsl.ui.feature.addgame

import com.github.purofle.nmsl.game.version.Version
import com.github.purofle.nmsl.utils.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class AddGameViewModel @Inject constructor() : ViewModel() {
    private val _startDownload = MutableStateFlow(false)
    val startDownload: StateFlow<Boolean> = _startDownload

    private val _version: MutableStateFlow<Version?> = MutableStateFlow(null)
    val version: StateFlow<Version?> = _version


    fun changeDownload(value: Boolean) {
        _startDownload.value = value
    }

    fun changeVersion(value: Version) {
        _version.value = value
    }
}