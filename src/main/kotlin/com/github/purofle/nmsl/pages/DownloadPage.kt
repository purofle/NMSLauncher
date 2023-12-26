package com.github.purofle.nmsl.pages

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.github.purofle.nmsl.download.DownloadProvider
import com.github.purofle.nmsl.game.GameManager
import com.github.purofle.nmsl.game.Manifest
import com.github.purofle.nmsl.game.VersionType
import com.github.purofle.nmsl.utils.getDefaultProvider
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class DownloadPage : Page {
    private val localManifest = try {
        GameManager.getLocalManifest()
    } catch (_: Exception) {
        null
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun render() {

        LaunchedEffect(Unit) {
            GameManager.downloadManifest(DownloadProvider.getDefaultProvider())
        }

        val remoteManifest: Manifest? by rememberSaveable { mutableStateOf(null) }
        val manifest by lazy { remoteManifest ?: localManifest ?: error("manifest is null") }

        var searchGame by remember { mutableStateOf("") }
        var showDownloadDialog by remember { mutableStateOf(false) }

        if (showDownloadDialog) {
            AlertDialog(
                onDismissRequest = {
                    showDownloadDialog = false
                },
                confirmButton = {
                    Button(
                        onClick = {
                            showDownloadDialog = false
                        }
                    ) {
                        Text("取消下载")
                    }
                },
                title = { Text("下载游戏") },
                text = {
                    Column {
                        Text("下载中...喝杯 java 先")
                        LinearProgressIndicator(
                            modifier = Modifier.padding(10.dp).fillMaxWidth(),
                            progress = 0.5f
                        )
                    }
                }
            )
        }


        if (localManifest == null && remoteManifest == null) {
            Text("loading")
        } else {

            OutlinedTextField(
                searchGame,
                { searchGame = it },
                modifier = Modifier.padding(10.dp).fillMaxWidth(),
                label = { Text("Search Games") },
                leadingIcon = { Icon(Icons.Default.Search, "search") },
                shape = RoundedCornerShape(30.dp),
                singleLine = true
            )

            val state = rememberLazyListState()
            val scope = rememberCoroutineScope()

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ) {
                LazyColumn(Modifier.padding(10.dp), state = state) {

                    scope.launch {
                        state.animateScrollToItem(0)
                    }

                    items(
                        manifest.versions.filter { it.id.contains(searchGame) }.sortedBy { it.type },
                        key = { it.id }
                    ) { version ->
                        Column(Modifier.animateItemPlacement()) {
                            VersionCard(version.id, version.releaseTime, version.type) {
                                showDownloadDialog = true
                            }
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

    @Composable
    fun VersionCard(
        id: String,
        time: String,
        type: VersionType,
        onClick: () -> Unit
    ) {
        Card(
            modifier = Modifier.fillMaxWidth()
                .padding(0.dp, 10.dp, 0.dp, 10.dp)
                .clickable { onClick() }
        ) {

            val formattedTime =
                Instant.parse(time).atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))

            Column(
                modifier = Modifier
                    .padding(15.dp),
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = id, style = TextStyle(fontWeight = FontWeight.Bold))
                    Text(text = type.name, style = TextStyle(fontWeight = FontWeight.Bold))
                }
                Text(
                    text = "发布时间: $formattedTime", style = TextStyle(
                        color = Color.Gray
                    )
                )
            }
        }
    }
}