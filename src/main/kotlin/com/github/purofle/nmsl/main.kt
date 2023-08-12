package com.github.purofle.nmsl

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.github.purofle.nmsl.pages.MainPage
import com.kieronquinn.monetcompat.compose.MonetCompatDynamicTheme
import com.kieronquinn.monetcompat.core.MonetCompat
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
            MonetCompatDynamicTheme(MonetCompat(seedColor())) {
                MainPage()
            }
        }
    }
}
