package com.github.purofle.nmsl.ui

import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.desktop.Window
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Text
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.application
import java.awt.datatransfer.UnsupportedFlavorException

@OptIn(ExperimentalComposeUiApi::class)
fun mainView() = application {
    setDefaultExceptionHandler()
    Window(
        title = "NMSL-Launcher"
    ) {
        DesktopMaterialTheme {

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
        Window {
            SelectionContainer {
                Text(exception.stackTraceToString())
            }
        }
    }
}