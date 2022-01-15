package com.github.purofle.nmsl.ui.feature.home

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton({
                TODO("启动游戏")
            }) { Text("启动游戏") }
        }
    ) {
        Text("Home")
    }
}
