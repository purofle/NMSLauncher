package com.github.purofle.nmsl.ui.feature.addgame.gametype

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.github.purofle.nmsl.download.BMCLAPIDownloadProvider
import com.github.purofle.nmsl.download.DownloadProvider
import com.github.purofle.nmsl.ui.component.FilterChipRow
import com.github.purofle.nmsl.ui.component.VersionCard

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VanillaScreen(
	downloadProvider: DownloadProvider = BMCLAPIDownloadProvider()
) {
    val filtered = listOf("release", "snapshot")
    // copy filtered
    var selected by remember { mutableStateOf(filtered.toList()) }
    val versions by downloadProvider.getVersionList().collectAsState(initial = listOf())
    var searchText by remember { mutableStateOf("") }

    val currentVersion = versions.filter {
        it.id.contains(searchText) && (it.type in selected)
    }

    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            FilterChipRow(
                list = filtered,
                key = { it },
                isSelected = { selected.contains(it) },
                onClick = { selected = if (selected.contains(it)) selected - it else selected + it },
            )
            OutlinedButton(
                onClick = {}
            ) {
                Text("刷新")
            }
        }

        Column {

            LazyColumn {
                item {
                    OutlinedTextField(
                        searchText,
                        { searchText = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("搜索") }
                    )
                }

                items(currentVersion, key = { it.id }) {
                    Column(Modifier.animateItemPlacement()) {
                        VersionCard(
                            it.id,
                            it.time
                        ) {

                        }
                    }
                }
            }
        }
    }
}
