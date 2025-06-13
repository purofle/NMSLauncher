package com.github.purofle.nmsl.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import com.github.purofle.nmsl.config.Config
import com.github.purofle.nmsl.config.LauncherConfig
import com.github.purofle.nmsl.download.DownloadProvider

class SettingPage : Page {
    @Composable
    override fun render() {

        var config by Config
        var gameSource by remember { mutableStateOf("${config.launcherConfig.provider}源") }

        Scaffold {
            Column {
                Text("游戏下载源")
                Row {
                    DownloadProvider.providers.keys.forEach {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("${it}源")
                            RadioButton(
                                selected = gameSource == "${it}源",
                                onClick = {
                                    gameSource = "${it}源"
                                    config = config.copy(launcherConfig = LauncherConfig(provider = it))
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}