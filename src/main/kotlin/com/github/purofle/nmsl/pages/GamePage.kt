package com.github.purofle.nmsl.pages

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.github.purofle.nmsl.game.GameJson
import com.github.purofle.nmsl.game.GameManager

class GamePage: Page {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
    @Composable
    override fun render() {

        var searchGameText by remember { mutableStateOf("") }
        val gameList = remember { mutableStateListOf<GameJson>() }


        SideEffect {
            gameList.addAll(GameManager.getAllGame())
        }

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
}