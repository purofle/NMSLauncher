package com.github.purofle.nmsl.pages

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.unit.dp
import com.github.purofle.nmsl.game.GameJson
import com.github.purofle.nmsl.game.GameManager

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun MainPage() {

    val gameList = remember { mutableStateListOf<GameJson>() }

    SideEffect {
        gameList.addAll(GameManager.getAllGame())
    }

    var searchGameText by remember { mutableStateOf("") }

    Scaffold {
        PermanentNavigationDrawer(
            drawerContent = {
                PermanentDrawerSheet(Modifier.width(220.dp)) {
                    Column(modifier = Modifier.padding(horizontal = 28.dp)) {
                        Text("NMSLauncher", modifier = Modifier.padding(17.dp))
                        NavigationDrawerItem(
                            icon = { Icon(Icons.Default.Gamepad, contentDescription = null) },
                            label = { Text("Game") },
                            selected = true,
                            onClick = {
                            },
                        )
                        NavigationDrawerItem(
                            icon = { Icon(Icons.Default.CloudDownload, contentDescription = null) },
                            label = { Text("Download") },
                            selected = false,
                            onClick = {
                            },
                        )
                        Divider()
                        Text("Launcher", modifier = Modifier.padding(17.dp))
                        NavigationDrawerItem(
                            icon = { Icon(Icons.Default.Settings, contentDescription = null) },
                            label = { Text("Settings") },
                            selected = false,
                            onClick = {
                            },
                        )
                        NavigationDrawerItem(
                            icon = { Icon(Icons.Default.Flag, contentDescription = null) },
                            label = { Text("About") },
                            selected = false,
                            onClick = {
                            },
                        )
                    }
                }
            },
            content = {
                Row {
                    Column(
                        modifier = Modifier.width(250.dp).fillMaxHeight()
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        OutlinedTextField(
                            searchGameText,
                            { searchGameText = it },
                            modifier = Modifier.padding(11.dp),
                            label = { Text("Search Games") },
                            leadingIcon = { Icon(Icons.Default.Search, "search") },
                            shape = RoundedCornerShape(30.dp),
                            singleLine = true
                        )
                        LazyColumn {
                            items(gameList.filter { it.id.contains(searchGameText) }) {
                                AnimatedContent(targetState = it) { _ ->
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.clickable { }.fillMaxWidth()
                                            .background(MaterialTheme.colorScheme.onSecondary)
                                    ) {
                                        val primaryContainer = MaterialTheme.colorScheme.primaryContainer
                                        Text("V", modifier = Modifier.drawBehind {
                                            drawCircle(primaryContainer, radius = 20f)
                                        }.padding(30.dp, 15.dp))
                                        Text(it.id, modifier = Modifier.padding(5.dp, 0.dp))
                                    }
                                }
                            }
                        }
                    }
                    Column(modifier = Modifier.fillMaxSize()) {

                    }
                }
            }
        )
    }
}