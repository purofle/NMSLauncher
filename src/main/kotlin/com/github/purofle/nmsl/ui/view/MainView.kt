package com.github.purofle.nmsl.ui.view

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
        when(selectedItem) {
            0 -> Text("page 1")
            2 -> DownloadView()
            else -> Text("Nothing")
        }
    }
}