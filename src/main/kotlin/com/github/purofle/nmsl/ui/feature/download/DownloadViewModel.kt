package com.github.purofle.nmsl.ui.feature.download

import com.github.purofle.nmsl.game.GameJson
import com.github.purofle.nmsl.utils.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class DownloadViewModel @Inject constructor(
): ViewModel() {
    private val _versions = MutableStateFlow(arrayListOf<GameJson>())
    val versions: StateFlow<ArrayList<GameJson>> = _versions

    override fun init(viewModelScope: CoroutineScope) {
        this.viewModelScope = viewModelScope
    }

    fun setVersions(versions: ArrayList<GameJson>) {
        _versions.value = versions
    }
}