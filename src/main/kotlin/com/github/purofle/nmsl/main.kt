package com.github.purofle.nmsl

import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.github.purofle.nmsl.ui.view.DownloadView

fun main() {
    application {
        Window(
            title = "NMSL-Launcher",
            onCloseRequest = ::exitApplication
        ) {
            DesktopMaterialTheme {
                DownloadView()
            }
        }
    }
}