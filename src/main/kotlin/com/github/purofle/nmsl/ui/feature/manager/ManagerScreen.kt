package com.github.purofle.nmsl.ui.feature.manager

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.github.purofle.nmsl.game.GameJson

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
                item {
                    if (versionManifest.isEmpty()) {
                        Text("暂无游戏, 请前往下载页面下载")
                    }
                }

                items(versionManifest) { gameJson ->
                    VersionUI(gameJson)
                }
            }
        }
    }
}


@Composable
fun VersionUI(gameJson: GameJson) {
    Column {
        Text(gameJson.id)
        Text(gameJson.releaseTime)
    }
}