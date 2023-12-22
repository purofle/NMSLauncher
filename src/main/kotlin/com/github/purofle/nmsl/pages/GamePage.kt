package com.github.purofle.nmsl.pages

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.unit.dp
import com.github.purofle.nmsl.game.GameManager
import com.github.purofle.nmsl.utils.getGameDownloader
import com.github.purofle.nmsl.utils.startGame

class GamePage : Page {
    @Composable
    override fun render() {

        var searchGameText by remember { mutableStateOf("") }
        var selectedGame by remember { mutableStateOf("") }
        val gameList = remember { mutableStateListOf<String>() }

        LaunchedEffect(Unit) {
            gameList.addAll(GameManager.versions)
        }

        Row(Modifier.fillMaxSize()) {
            Row(Modifier.width(250.dp)) {
                Column {
                    OutlinedTextField(
                        searchGameText,
                        { searchGameText = it },
                        modifier = Modifier.padding(11.dp),
                        label = { Text("Search Games") },
                        leadingIcon = { Icon(Icons.Default.Search, "search") },
                        shape = RoundedCornerShape(30.dp),
                        singleLine = true
                    )

                    GameList(gameList, { it.contains(searchGameText) }) {
                        selectedGame = it
                    }
                }
            }
            Box(Modifier.fillMaxSize()) {
                Column(Modifier.fillMaxWidth()) {
                    MicrosoftAccount()
                }

                if (selectedGame.isEmpty()) {
                    Text("请选择游戏版本", modifier = Modifier.align(Alignment.Center))
                } else {

                    ExtendedFloatingActionButton(
                        {

                            val gameDownloader = getGameDownloader(selectedGame)
                            startGame(gameDownloader.getLauncherArgument())

                        },
                        modifier = Modifier.align(Alignment.BottomEnd).padding(20.dp)
                    ) {
                        Text("启动游戏")
                    }
                }
            }
        }
    }
}

@Composable
fun GameList(gameList: List<String>, predicate: (String) -> Boolean, onClick: (String) -> Unit) {
    LazyColumn {
        items(gameList.filter(predicate)) {
            AnimatedContent(targetState = it) { _ ->
                GameItem(it) {
                    onClick(it)
                }
            }
        }
    }
}

@Composable
fun GameItem(text: String, onClick: () -> Unit) {

    val primaryContainer = MaterialTheme.colorScheme.primaryContainer

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { onClick() }.fillMaxWidth()
            .background(MaterialTheme.colorScheme.onSecondary)
    ) {

        Text("V", modifier = Modifier.drawBehind {
            drawCircle(primaryContainer, radius = 20f)
        }.padding(30.dp, 15.dp))

        Text(text, modifier = Modifier.padding(5.dp, 0.dp))
    }
}

@Composable
fun MicrosoftAccount() {
    val primaryContainer = MaterialTheme.colorScheme.primaryContainer
    val secondaryContainer = MaterialTheme.colorScheme.secondaryContainer

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { }.fillMaxWidth()
            .background(MaterialTheme.colorScheme.onSecondary)
    ) {

        Text("M", modifier = Modifier.drawBehind {
            drawCircle(primaryContainer, radius = 20f)
        }.padding(30.dp, 15.dp))

        Text("Microsoft Account", modifier = Modifier.padding(5.dp, 0.dp))
    }
}