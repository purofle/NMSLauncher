package com.github.purofle.nmsl.ui.feature.manager

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.github.purofle.nmsl.game.download.Version
import com.github.purofle.nmsl.ui.component.LoadingAnimation

//下载游戏view
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManagerScreen(
    viewModel: ManagerViewModel
) {
    val versionManifest by viewModel.versions.collectAsState()
    Scaffold(
        floatingActionButton = {
            FloatingActionButton({ //刷新
                viewModel.refreshData()
            }) { Icon(Icons.Filled.Refresh, "refresh") }
        },
        topBar = {
            SmallTopAppBar(title = { Text("游戏管理") })
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            LazyColumn(
                modifier = Modifier.fillMaxHeight(),
            ) {
                if (versionManifest.isNotEmpty()) {
                    viewModel.setLoading(false)
                }

                item {
                    if (versionManifest.isEmpty()) {

                        LoadingAnimation()
                    }
                }
            }
        }
    }
}

@Composable
fun VersionItem(version: Version, onVersionSelected: (Version) -> Unit) {
    Button(
        {
        // 下载的实现
         onVersionSelected(version)
        },
        modifier = Modifier.fillMaxWidth()) {
        Text(version.id)
    }
}