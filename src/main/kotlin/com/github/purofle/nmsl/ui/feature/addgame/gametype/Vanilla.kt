package com.github.purofle.nmsl.ui.feature.addgame.gametype

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.*
import com.github.purofle.nmsl.ui.component.FilterChipRow

@Composable
fun VanillaScreen() {
    val filtered = listOf("release", "snapshot", "test")
    var selected by remember { mutableStateOf(listOf<String>()) }

    Row {
        FilterChipRow(
            list = filtered,
            key = { it },
            isSelected = { selected.contains(it) },
            onClick = { selected = if (selected.contains(it)) selected - it else selected + it },
        )
    }
}