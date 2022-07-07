package com.github.purofle.nmsl.ui.feature.manager

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.github.purofle.nmsl.game.download.Version

//下载游戏view
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManagerScreen(viewModel: ManagerViewModel,
                  onVersionSelected: (Version) -> Unit) {
    val versionManifest by viewModel.versions.collectAsState()
    Scaffold(
        floatingActionButton = {
            FloatingActionButton({ //刷新
                viewModel.refreshData()
            }) { Icon(Icons.Filled.Refresh, "refresh") }
        }
    ) {

        val state = rememberLazyListState()

        Box {
            VerticalScrollbar(
                modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
                adapter = rememberScrollbarAdapter(
                    scrollState = state
                )
            )
        }

        Row {
            Column(
                modifier =
                Modifier.weight(1f)
                    .fillMaxHeight()
            ) {

            }
            Column(
                modifier =
                Modifier.weight(0.7f)
                    .fillMaxHeight()
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxHeight(),
                    state = state
                ) {
                    items(versionManifest) {
                        VersionItem(version = it, onVersionSelected)
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