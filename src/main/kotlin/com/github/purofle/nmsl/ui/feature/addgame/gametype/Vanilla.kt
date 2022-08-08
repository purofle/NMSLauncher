package com.github.purofle.nmsl.ui.feature.addgame.gametype

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VanillaScreen() {
    val filtered = listOf("release", "snapshot")
    var selected by remember { mutableStateOf(listOf<Int>()) }
    Row {
        filtered.forEachIndexed { index, name ->
            FilterChip(
                modifier = Modifier.padding(16.dp),
                label = { Text(name) },
                onClick = {
                    selected = selected + index
                    println("selected: $selected, index: $index")
                },
                selected = selected.contains(index)
            )
        }
    }
}