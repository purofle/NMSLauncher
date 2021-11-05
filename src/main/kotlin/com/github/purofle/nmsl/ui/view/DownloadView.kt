package com.github.purofle.nmsl.ui.view

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.purofle.nmsl.game.download.Downloader
import com.github.purofle.nmsl.game.download.Version
import com.github.purofle.nmsl.platforms.OperatingSystem
import com.github.purofle.nmsl.utils.date
import com.github.purofle.nmsl.utils.mkdirs
import io.ktor.client.features.logging.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

//下载view
@Composable
fun DownloadView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val scope = rememberCoroutineScope()
        val emptyData = listOf(Version("正在获取中", "", "", "", ""))
        var data by remember { mutableStateOf(emptyData) }
        OperatingSystem.getLauncherWorkingDirectory().toFile().mkdirs("assets", "libraries", "versions")
        if (data == emptyData) {
            scope.launch(context = Dispatchers.Default) {
                println("开始下载")
                val manifest = Downloader.getReleases()
                data = manifest.versions
                println("下载完成")
            }
        }
        DownloadViewBody(data)
    }
}

@Composable
fun DownloadViewBody(data: List<Version>) {
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
                    scope.launch {
                        println("下载2")
                        Downloader.downloadReleasesToFileSystem()
                        Downloader.downloadClient(Downloader.getReleaseInfo(it.url))
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