package com.github.purofle.nmsl.ui.feature.download

import com.github.purofle.nmsl.game.download.Downloader
import com.github.purofle.nmsl.game.download.Version
import com.github.purofle.nmsl.utils.Log
import com.github.purofle.nmsl.utils.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class DownloadViewModel @Inject constructor(
    // 现在应该用不着
): ViewModel() {
    private val _versions = MutableStateFlow(arrayListOf<Version>())
    val versions: StateFlow<ArrayList<Version>> = _versions

    fun refreshData() {
        viewModelScope.launch(context = Dispatchers.Default) {
            Log.logger.debug("刷新数据")
            _versions.value =  Downloader.getReleases().versions
        }
    }
}