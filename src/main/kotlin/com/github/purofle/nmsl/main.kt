package com.github.purofle.nmsl

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.github.purofle.nmsl.ui.feature.MainView
import com.kieronquinn.monetcompat.compose.MonetCompatDynamicTheme
import com.kieronquinn.monetcompat.core.MonetCompat
import org.slf4j.LoggerFactory

fun main() {
    application {
        Window(
            title = "NMSL-Launcher",
            onCloseRequest = ::exitApplication
        ) {
            val logger = LoggerFactory.getLogger("NMSLauncher")
            logger.debug("Starting NMSLauncher")
            logger.debug("seedColor: ${seedColor().red * 255}, ${seedColor().green * 255}, ${seedColor().blue * 255}")
            MonetCompatDynamicTheme(MonetCompat(seedColor())) {
                MainView()
            }
        }
    }
}