package com.github.purofle.nmsl.ui.feature.download

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.AlertDialog
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.github.purofle.nmsl.ui.component.VersionCard
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun DownloadScreen(
    viewModel: DownloadViewModel
) {

    val scope = rememberCoroutineScope()
    val versions by viewModel.versions.collectAsState()
    val searchDialog by viewModel.searchDialog.collectAsState()

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text("游戏下载") },
                actions = {
                    TextButton({ viewModel.showSearch() }) { Text("搜索") }
                }
            )
        }
    ) { pd ->

        val state = rememberLazyListState()

        if (searchDialog) {
            AlertDialog(
                title = { Text("搜索") },
                text = { Text("请输入搜索关键字") },
                confirmButton = {
                    TextButton({ }) { Text("搜索") }
                },
                onDismissRequest = { viewModel.showSearch() }
            )
        }

        Column(modifier = Modifier.padding(pd)) {

            if (versions.isEmpty()) {

                Text("Loading...", modifier = Modifier.align(Alignment.CenterHorizontally))
                scope.launch {
                    viewModel.setVersions(viewModel.refresh())
                }

            } else {
                Box(modifier = Modifier.fillMaxSize()) {
                    // 把 versions 按照 release,snapshot, old_beta, old_aplha 的顺序排序
                    val grouped = versions.sortedWith(
                        compareBy(
                            { it.type == "old_alpha" },
                            { it.type == "old_beta" },
                            { it.type == "snapshot" },
                            { it.type == "release" },
                        )
                    ).groupBy { it.type }

                    LazyColumn(state = state) {
                        grouped.forEach { (initial, contactsForInitial) ->
                            stickyHeader {
                                SmallTopAppBar(title = { Text(initial) })
                            }
                            items(contactsForInitial) {
                                VersionCard(it)
                            }
                        }
                    }

                    VerticalScrollbar(
                        modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
                        adapter = rememberScrollbarAdapter(
                            scrollState = state
                        )
                    )
                }
            }
        }
    }
}