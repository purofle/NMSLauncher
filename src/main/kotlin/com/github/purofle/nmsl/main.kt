package com.github.purofle.nmsl

import androidx.compose.desktop.Window
import androidx.compose.ui.unit.IntSize
import com.github.purofle.nmsl.ui.mainView

fun main() {
    Window(
        "NMSL-Launcher",
        IntSize(500, 500),
    ) {
        mainView()
    }
}