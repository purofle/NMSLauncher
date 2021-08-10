package com.github.purofle.nmsl.ui.view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.purofle.nmsl.game.GameDownload
import com.github.purofle.nmsl.game.Version
import com.github.purofle.nmsl.game.version.parse.VersionParser
import com.github.purofle.nmsl.platforms.system
import com.github.purofle.nmsl.utils.date
import com.github.purofle.nmsl.utils.mkdirs
import com.github.purofle.nmsl.utils.toMap
import io.ktor.client.features.logging.*
import kotlinx.coroutines.launch

@Composable
//下载view
fun DownloadView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val scope = rememberCoroutineScope()
        val emptyData = listOf(Version("正在获取中", "", "", "", ""))
        var data by remember { mutableStateOf(emptyData) }
        val gameDownload = GameDownload(LogLevel.NONE)
        system().data.mkdirs(listOf("assets", "libraries", "versions"))
        if (data == emptyData) {
            scope.launch {
                val manifest = gameDownload.getVersionManifest()
                data = manifest.versions
            }
        }
        DownloadViewBody(data, gameDownload)
    }
}

@Composable
fun DownloadViewBody(data: List<Version>, gameDownload: GameDownload) {
    Box(
        modifier = Modifier.fillMaxSize()
            .padding(10.dp)
    ) {
        val state = rememberScrollState(0)
        val scope = rememberCoroutineScope()

        Column(
            modifier = Modifier
                .verticalScroll(state)
                .fillMaxSize()
                .padding(end = 12.dp)
        ) {
            data.forEach {
                TextBox("${it.id} - 发布时间：${it.releaseTime.date()}") {
                    if (!data.toMap().containsKey(it.id)) {
                        TODO("找不到版本：${it.id}")
                    } else {
                        scope.launch {
                            val versionInfo = gameDownload.getVersionInfo(data.toMap()[it.id].toString())
                            println(VersionParser(versionInfo).libraries())
                        }

                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
            }
        }
        VerticalScrollbar(
            modifier = Modifier.align(Alignment.CenterEnd)
                .fillMaxHeight(),
            adapter = rememberScrollbarAdapter(state)
        )
    }
}

@Composable
fun TextBox(text: String = "Item", onClick: () -> Unit) {
        Button(
            onClick = onClick,
            colors = buttonColors(Color(0,0,0,0))
        ) {
            Box(
                modifier = Modifier.height(32.dp)
                    .fillMaxWidth()
                    .padding(start = 10.dp), contentAlignment = Alignment.CenterStart
            ) {
                Text(text = text)
            }
        }
}