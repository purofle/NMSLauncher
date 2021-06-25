package com.github.purofle.nmsl.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.purofle.nmsl.game.GameDownload
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory

@Composable
fun mainView() {
    val logger = LoggerFactory.getLogger("ui")
    var name by remember { mutableStateOf("") }
    MaterialTheme {
        Column(Modifier.fillMaxSize(), Arrangement.spacedBy(5.dp)) {
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("版本号") }
            )
            Button(onClick = {
                val n = GameDownload("~/a/")
                GlobalScope.launch {
                    logger.info("start download VersionManifest")
                    n.getVersionManifest()
                }
            },content={ Text("开始下载") })
        }
    }
}