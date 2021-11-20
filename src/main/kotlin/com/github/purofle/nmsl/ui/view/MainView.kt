package com.github.purofle.nmsl.ui.view

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView() {
    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf("Home", "AccountBox", "Download")
    val icons = listOf(Icons.Filled.Home, Icons.Filled.AccountBox, Icons.Filled.KeyboardArrowDown)

    Row {
        NavigationRail {
            items.forEachIndexed { index, item ->
                NavigationRailItem(
                    icon = { Icon(icons[index], contentDescription = item) },
                    label = { Text(item) },
                    selected = selectedItem == index,
                    onClick = {
                        selectedItem = index
                    }
                )
            }
        }
        Crossfade(targetState = selectedItem) { item ->
            when (item) {
                0 -> HomeView()
                2 -> DownloadView()
                else -> Text("Nothing")
            }
        }
    }
}
