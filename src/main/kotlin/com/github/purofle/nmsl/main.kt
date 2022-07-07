package com.github.purofle.nmsl

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.github.purofle.nmsl.ui.feature.MainView
import com.kieronquinn.monetcompat.compose.MonetCompatDynamicTheme
import com.kieronquinn.monetcompat.core.MonetCompat

fun main() {
    application {
        Window(
            title = "NMSL-Launcher",
            onCloseRequest = ::exitApplication
        ) {
            MonetCompatDynamicTheme(MonetCompat(102,104,255)) {
                MainView()
            }
        }
    }
}