package com.github.purofle.nmsl.ui.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton({
                TODO("启动游戏")
            }) { Text("启动游戏") }
        }
    ) {

        Scaffold(topBar = {
            SmallTopAppBar({ Text("NMSLauncher") })
        }) {
            Column(modifier = Modifier.padding(it)) {
                Button({}) { Text("NMSLauncher") }
            }
        }
    }
}
