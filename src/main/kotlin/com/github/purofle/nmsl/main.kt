package com.github.purofle.nmsl

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.github.purofle.nmsl.config.Config
import com.github.purofle.nmsl.config.NmslConfig
import com.github.purofle.nmsl.pages.MainPage

fun main() {
    runCatching {
        Config.config
    }.onFailure {
        Config.createConfig(NmslConfig())
    }


    application {
        Window(
            title = "NMSLauncher",
            onCloseRequest = ::exitApplication
        ) {
            MaterialTheme(colorScheme = darkColorScheme()) {
                MainPage()
            }
        }
    }
}
