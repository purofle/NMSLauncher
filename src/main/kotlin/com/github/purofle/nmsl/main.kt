package com.github.purofle.nmsl

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.github.purofle.nmsl.pages.MainPage
import org.apache.logging.log4j.LogManager

fun main() {

    Thread.setDefaultUncaughtExceptionHandler { _, e ->
        LogManager.getLogger("error").error(e)
    }

    application {
        Window(
            title = "NMSLauncher",
            onCloseRequest = ::exitApplication
        ) {
//            MonetCompatDynamicTheme(MonetCompat(seedColor())) {
//                MainPage()
//            }
            MaterialTheme(colorScheme = darkColorScheme()) {
                MainPage()
            }
        }
    }
}
