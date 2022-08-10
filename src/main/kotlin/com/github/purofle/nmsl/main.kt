package com.github.purofle.nmsl

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.github.purofle.nmsl.ui.feature.MainView
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

            val logger = LogManager.getLogger(this::class.java)
            logger.debug("Starting NMSLauncher")
            logger.debug("seedColor: ${seedColor().red * 255}, ${seedColor().green * 255}, ${seedColor().blue * 255}")
            MonetCompatDynamicTheme(MonetCompat(seedColor())) {
                MainView()
            }
        }
    }
}