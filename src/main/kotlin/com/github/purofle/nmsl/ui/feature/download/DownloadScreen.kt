package com.github.purofle.nmsl.ui.feature.download

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.router.Router
import com.github.purofle.nmsl.ui.component.VersionCard
import com.github.purofle.nmsl.ui.navigation.Component
import com.github.purofle.nmsl.ui.navigation.NavHostComponent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun DownloadScreen(
    viewModel: DownloadViewModel,
    router: Router<NavHostComponent.Config, Component>
) {

    val scope = rememberCoroutineScope()
    val versions by viewModel.versions.collectAsState()
    var textValue by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text("游戏下载") },
            )
        }
    ) { pd ->

        val state = rememberLazyListState()

        Column(modifier = Modifier.padding(pd)) {

            TextField(
                value = textValue,
                onValueChange = { textValue = it },
                label = { Text("Enter text") },
                maxLines = 1,
            )

            if (versions.isEmpty()) {

                Text("Loading...", modifier = Modifier.align(Alignment.CenterHorizontally))
                scope.launch {
                    viewModel.setVersions(viewModel.refresh())
                }

            } else {
                Box(modifier = Modifier.fillMaxSize()) {
                    // 把 versions 按照 release,snapshot, old_beta, old_aplha 的顺序排序
                    val grouped = versions
                        .filter { textValue in it.id }
                        .sortedWith(
                            compareBy(
                                { it.type == "old_alpha" },
                                { it.type == "old_beta" },
                                { it.type == "snapshot" },
                                { it.type == "release" },
                            )
                        )
                        .groupBy { it.type }
                    LazyColumn(state = state) {
                        grouped.forEach { (initial, contactsForInitial) ->
                            stickyHeader {
                                SmallTopAppBar(title = { Text(initial) })
                            }
                            items(contactsForInitial) {
                                VersionCard(it, router)
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