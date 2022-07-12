package com.github.purofle.nmsl.ui.feature.download

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DownloadScreen(
    viewModel: DownloadScreenComponent
) {
    Scaffold(
        topBar = {
            SmallTopAppBar(title = { Text("游戏下载") })
        }
    ) { pd ->
        Column(modifier = Modifier.padding(pd)) {

        }
    }
}