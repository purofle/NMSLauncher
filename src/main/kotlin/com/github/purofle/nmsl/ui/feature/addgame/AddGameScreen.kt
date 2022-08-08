package com.github.purofle.nmsl.ui.feature.addgame

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.github.purofle.nmsl.ui.feature.addgame.gametype.VanillaScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddGameScreen(viewModel: AddGameViewModel) {

    val gameName by viewModel.gameName.collectAsState()

    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf("原版", "CurseForge")
    val icons = listOf(Icons.Filled.Home, Icons.Filled.List)

    Scaffold(
        topBar = {
            Row {
                OutlinedTextField(
                    modifier = Modifier.padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    label = { Text("Game Name", textAlign = TextAlign.Center) },
                    onValueChange = { viewModel.changeGameName(it) },
                    value = gameName
                )
            }
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            Row {
                NavigationRail(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
                ) {
                    items.forEachIndexed { index, item ->
                        NavigationRailItem(
                            icon = { Icon(icons[index], contentDescription = item) },
                            label = { Text(item) },
                            selected = selectedItem == index,
                            onClick = {
                                selectedItem = index
                            },
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxSize()
                ) {
                    when (selectedItem) {
                        0 -> {
                            VanillaScreen()
                        }

                        1 -> {
                            Text("CurseForge")
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun AddGameScreenPreview() {
    AddGameScreen(AddGameViewModel())
}