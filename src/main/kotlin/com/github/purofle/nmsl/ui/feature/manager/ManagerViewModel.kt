package com.github.purofle.nmsl.ui.feature.manager

import com.github.purofle.nmsl.game.GameJson
import com.github.purofle.nmsl.utils.Log
import com.github.purofle.nmsl.utils.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class ManagerViewModel @Inject constructor(
    // 现在应该用不着
): ViewModel() {
    private val _versions = MutableStateFlow(arrayListOf<GameJson>())
    val versions: StateFlow<ArrayList<GameJson>> = _versions

    fun refreshData() {
        viewModelScope.launch(context = Dispatchers.Default) {
            Log.logger.debug("刷新数据")
//            _versions.value =  Downloader.getReleases().versions
        }
    }
}