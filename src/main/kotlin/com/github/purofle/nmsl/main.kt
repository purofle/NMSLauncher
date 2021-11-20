package com.github.purofle.nmsl

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.github.purofle.monet.MonetCompat
import com.github.purofle.monet.MonetCompatDynamicTheme
import com.github.purofle.nmsl.ui.view.MainView

fun main() {
    application {
        Window(
            title = "NMSL-Launcher",
            onCloseRequest = ::exitApplication
        ) {
//            MaterialTheme(darkColorScheme()) {
//                MainView()
//            }
            MonetCompatDynamicTheme(MonetCompat) {
                MainView()
            }
        }
    }
}