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
import com.github.purofle.nmsl.auth.microsoft.MicrosoftAuth
import com.github.purofle.nmsl.auth.microsoft.MinecraftAuth
import com.github.purofle.nmsl.config.Config
import com.github.purofle.nmsl.config.Msa
import com.github.purofle.nmsl.config.NmslConfig
import com.github.purofle.nmsl.game.GameManager
import com.github.purofle.nmsl.utils.getGameDownloader
import com.github.purofle.nmsl.utils.startGame
import kotlinx.coroutines.launch
import org.jetbrains.skiko.ClipboardManager
import org.jetbrains.skiko.URIManager

class GamePage : Page {
    @Composable
    override fun render() {

        var searchGameText by remember { mutableStateOf("") }
        var selectedGame by remember { mutableStateOf("") }
        val gameList = remember { mutableStateListOf<String>() }
        var showMicrosoftLoginDialog by remember { mutableStateOf(false) }

        val scope = rememberCoroutineScope()
        gameList.addAll(GameManager.versions)

        if (showMicrosoftLoginDialog) {
            MicrosoftLoginDialog {
                showMicrosoftLoginDialog = false
            }
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

            Scaffold(
                topBar = {
                    Column(Modifier.fillMaxWidth()) {
                        MicrosoftAccount {
                            showMicrosoftLoginDialog = true
                        }
                    }
                },
                floatingActionButton = {
                    if (selectedGame.isNotBlank()) {
                        ExtendedFloatingActionButton(
                            {
                                val gameDownloader = getGameDownloader(selectedGame)
                                scope.launch {
                                    startGame(selectedGame, gameDownloader.getLauncherArgument())
                                }

                            },
                            Modifier.padding(20.dp)
                        ) {
                            Text("启动游戏")
                        }
                    }
                }
            ) { pd ->
                Box(modifier = Modifier.padding(pd).fillMaxSize()) {
                    if (selectedGame.isEmpty()) {
                        Text("Tips：点击左侧浏览游戏详细信息喵", modifier = Modifier.align(Alignment.Center))
                    } else {
                        Text("已选择 $selectedGame")
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
fun MicrosoftAccount(onClick: () -> Unit) {
    val primaryContainer = MaterialTheme.colorScheme.primaryContainer

    var config = Config.config.profile.minecraftProfile.name
    if (config.isEmpty()) {
        config = "Microsoft Account"
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable {
            onClick()
        }.fillMaxWidth()
            .background(MaterialTheme.colorScheme.onSecondary)
    ) {

        Text(config.first().uppercase(), modifier = Modifier.drawBehind {
            drawCircle(primaryContainer, radius = 20f)
        }.padding(30.dp, 15.dp))

        Text(config, modifier = Modifier.padding(5.dp, 0.dp))
    }
}

@Composable
fun MicrosoftLoginDialog(callback: () -> Unit) {

    var deviceCode by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        val deviceFlow = MicrosoftAuth.getDeviceAuthorization()
        deviceCode = deviceFlow.userCode

        launch {
            MicrosoftAuth.authorizationFlow(deviceFlow.deviceCode).collect { auth ->
                Config.createConfig(
                    NmslConfig(
                        Msa(
                            accessToken = auth.accessToken,
                            refreshToken = auth.refreshToken,
                            expiresIn = auth.expiresIn
                        ),
                        MinecraftAuth.authenticate(auth.accessToken),
                    )
                )
                callback()
            }

        }
    }
    AlertDialog(
        onDismissRequest = { callback() },
        title = { Text("Microsoft Login") },
        text = { Text("复制 $deviceCode 并打开 https://www.microsoft.com/link 来登录") },
        confirmButton = {
            Button(onClick = {
                ClipboardManager().setText(deviceCode)
                URIManager().openUri("https://www.microsoft.com/link")
            }) {
                Text("复制并打开浏览器")
            }
        },
        dismissButton = {
            Button(onClick = { callback() }) {
                Text("Cancel")
            }
        }
    )
}