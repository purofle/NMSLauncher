package com.github.purofle.nmsl

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.github.purofle.monet.MonetCompat
import com.github.purofle.monet.MonetCompatDynamicTheme
import com.github.purofle.nmsl.ui.feature.MainView

fun main() {
    application {
        Window(
            title = "NMSL-Launcher",
            onCloseRequest = ::exitApplication
        ) {
            MonetCompatDynamicTheme(MonetCompat) {
                MainView()
            }
        }
    }
}