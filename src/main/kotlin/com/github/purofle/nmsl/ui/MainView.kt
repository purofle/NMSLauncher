package com.github.purofle.nmsl.ui

import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.desktop.Window
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.application
import java.awt.datatransfer.UnsupportedFlavorException

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun mainView() = application {
    setDefaultExceptionHandler()
    Window(
        title = "NMSL-Launcher"
    ) {
        DesktopMaterialTheme {
            val scope = rememberCoroutineScope()
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
                floatingActionButtonPosition = FabPosition.End,

                //屏幕内容区域
                content = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {

                    }
                }
            )
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