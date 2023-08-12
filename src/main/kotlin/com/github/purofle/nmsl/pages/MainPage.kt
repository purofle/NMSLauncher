package com.github.purofle.nmsl.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Gamepad
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage() {
    val items = listOf(Icons.Default.Favorite, Icons.Default.Face, Icons.Default.Email)
    val selectedItem = remember { mutableStateOf(items[0]) }

    Scaffold {

        PermanentNavigationDrawer(
            drawerContent = {
                PermanentDrawerSheet(Modifier.width(200.dp)) {
                    Column {
                        Text("NMSLauncher")
                        NavigationDrawerItem(
                            icon = { Icon(Icons.Default.Gamepad, contentDescription = null) },
                            label = { Text("Game") },
                            selected = true,
                            onClick = {
                            },
                            modifier = Modifier.padding(horizontal = 0.dp)
                        )
                    }
                }
            },
            content = {

            }
        )
    }
}