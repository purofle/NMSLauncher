package com.github.purofle.nmsl.ui.feature.download

import com.github.purofle.nmsl.game.download.Version
import com.github.purofle.nmsl.utils.ViewModel
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class DownloadViewModel @Inject constructor(
): ViewModel() {
    lateinit var version: Version
    fun init(viewModelScope: CoroutineScope, version: Version) {
        this.viewModelScope = viewModelScope
        this.version = version
    }
}