package com.github.purofle.nmsl.ui

import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.desktop.Window
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.application
import com.github.purofle.nmsl.game.GameDownload
import com.github.purofle.nmsl.game.Version
import com.github.purofle.nmsl.platforms.system
import com.github.purofle.nmsl.ui.view.DownloadView
import io.ktor.client.features.logging.*
import kotlinx.coroutines.launch
import java.awt.datatransfer.UnsupportedFlavorException

@OptIn(ExperimentalComposeUiApi::class)
fun mainView() = application {
    setDefaultExceptionHandler()
    Window(
        title = "NMSL-Launcher"
    ) {
        DesktopMaterialTheme {
            val scaffoldState = rememberScaffoldState()
            Scaffold(
                scaffoldState = scaffoldState,
                drawerContent = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                    }
                },
                //标题栏区域
                topBar = {
                    TopAppBar(
                        title = { Text(text = "NMSL-Launcher") },
                    )
                },
                floatingActionButtonPosition = FabPosition.End

            )

            //屏幕内容区域
            {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    val scope = rememberCoroutineScope()
                    var data by remember { mutableStateOf(listOf(Version("正在获取中", "", "", "", ""))) }
                    scope.launch {
                        data = GameDownload(system().data, LogLevel.ALL).getVersionManifest().versions
                    }
                    DownloadView(data)
                }
            }
        }
    }
}

//从 mirai-compose 那里抄来的异常处理
@ExperimentalComposeUiApi
private fun setDefaultExceptionHandler() {
    Thread.setDefaultUncaughtExceptionHandler { _, exception ->
        if (exception is UnsupportedFlavorException) {
            return@setDefaultUncaughtExceptionHandler
        }
        println(exception.stackTraceToString())
        Window(
            title = "出现错误"
        ) {
            Column {
                Button(
                    {},
                    Modifier.fillMaxWidth(),
                    content = { Text("反馈") }
                )
                SelectionContainer {
                    Text(exception.stackTraceToString())
                }
            }
        }
    }
}