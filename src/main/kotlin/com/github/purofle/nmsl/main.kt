package com.github.purofle.nmsl

import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.github.purofle.nmsl.ui.root.NavigationHostComponent

fun main() {
    application {
        Window(
            title = "NMSL-Launcher",
            onCloseRequest = ::exitApplication
        ) {
            DesktopMaterialTheme {
                val lifecycle = LifecycleRegistry()
                NavigationHostComponent(DefaultComponentContext(lifecycle)).render()
            }
        }
    }
}