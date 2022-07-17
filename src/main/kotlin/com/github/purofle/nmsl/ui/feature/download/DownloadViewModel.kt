package com.github.purofle.nmsl.ui.feature.download

import com.github.purofle.nmsl.download.MojangDownloadProvider
import com.github.purofle.nmsl.game.Version
import com.github.purofle.nmsl.game.VersionList
import com.github.purofle.nmsl.utils.ViewModel
import com.github.purofle.nmsl.utils.io.HttpRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class DownloadViewModel @Inject constructor(
): ViewModel() {

    private val _versions = MutableStateFlow(listOf<Version>())
    private val _searchDialog = MutableStateFlow(false)
    val searchDialog: StateFlow<Boolean> = _searchDialog
    val versions: StateFlow<List<Version>> = _versions

    override fun init(viewModelScope: CoroutineScope) {
        this.viewModelScope = viewModelScope
    }

    fun setVersions(versions: List<Version>) {
        _versions.value = versions
    }

    fun showSearch() {
        _searchDialog.value = !_searchDialog.value
    }

    suspend fun refresh(): List<Version> {
        return HttpRequest.getJson<VersionList>(MojangDownloadProvider().versionListURL).versions
    }
}