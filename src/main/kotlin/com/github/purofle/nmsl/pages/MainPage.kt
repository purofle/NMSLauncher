package com.github.purofle.nmsl.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Gamepad
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

interface Page {
    @Composable
    fun render()
}

data class DrawerItem(
    val icon: ImageVector,
    val text: String,
    val page: Page
)

@Composable
fun MainPage() {

    val mainPageList = listOf(
        DrawerItem(
            icon = Icons.Default.Gamepad,
            text = "Game",
            page = GamePage()
        ), DrawerItem(
            icon = Icons.Default.CloudDownload,
            text = "Download",
            page = DownloadPage(),
        )
    )

    val launcherPageList = listOf(
        DrawerItem(
            icon = Icons.Default.Settings,
            text = "Settings",
            page = SettingPage(),
        ), DrawerItem(
            icon = Icons.Default.Flag,
            text = "About",
            page = AboutPage(),
        )
    )

    var selected by remember { mutableStateOf("Game") }

    Scaffold {
        PermanentNavigationDrawer(
            drawerContent = {
                PermanentDrawerSheet(Modifier.width(220.dp)) {
                    LazyColumn(modifier = Modifier.padding(horizontal = 28.dp)) {
                        item {
                            Text("NMSLauncher", modifier = Modifier.padding(17.dp))
                        }

                        items(mainPageList) {
                            NavigationDrawerItem(
                                icon = { Icon(it.icon, contentDescription = null) },
                                label = { Text(it.text) },
                                selected = selected == it.text,
                                onClick = {
                                    selected = it.text
                                },
                            )
                        }

                        item {
                            Divider()
                            Text("Launcher", modifier = Modifier.padding(17.dp))
                        }

                        items(launcherPageList) {
                            NavigationDrawerItem(
                                icon = { Icon(it.icon, contentDescription = null) },
                                label = { Text(it.text) },
                                selected = selected == it.text,
                                onClick = {
                                    selected = it.text
                                },
                            )
                        }
                    }
                }
            },
            content = {
                Column(
                    modifier = Modifier.fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    (mainPageList + launcherPageList).first { it.text == selected }.page.render()
                }
            }
        )
    }
}