package com.github.purofle.nmsl

import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.github.purofle.nmsl.ui.root.NMSLRoot
import com.github.purofle.nmsl.ui.view.RootView

fun main() {
    application {
        Window(
            title = "NMSL-Launcher",
            onCloseRequest = ::exitApplication
        ) {
            DesktopMaterialTheme {
                RootView(NMSLRoot(DefaultComponentContext(LifecycleRegistry())))
            }
        }
    }
}