package com.github.purofle.nmsl.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.github.purofle.nmsl.config.Config
import com.github.purofle.nmsl.config.LauncherConfig
import com.github.purofle.nmsl.download.DownloadProvider

class SettingPage : Page {
    @Composable
    override fun render() {

        var config by Config
        var gameSource by remember { mutableStateOf("${config.launcherConfig.provider}源") }

        Scaffold {
            LazyColumn {
                item {
                    SettingSelectItem(
                        title = "游戏资源下载源",
                        items = DownloadProvider.providers.keys.map {
                            SettingItem("${it}源") {
                                gameSource = "${it}源"
                                config = config.copy(launcherConfig = LauncherConfig(provider = it))
                            }
                        },
                        defaultItem = gameSource
                    )
                }
            }
        }
    }
}

data class SettingItem(
    val title: String,
    val onClick: () -> Unit
)

@Composable
fun SettingSelectItem(
    title: String,
    items: List<SettingItem>,
    defaultItem: String
) {

    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(12.dp)) {
        Text(title)
        Spacer(modifier = Modifier.height(6.dp))
        Row(
            modifier = Modifier
                .height(32.dp)
                .background(MaterialTheme.colorScheme.secondaryContainer, RoundedCornerShape(2.dp))
                .clickable { expanded = !expanded },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(defaultItem, modifier = Modifier.padding(start = 12.dp))
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                modifier = Modifier.padding(start = 48.dp, end = 12.dp)
            )
        }
    }
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        offset = DpOffset(12.dp, 0.dp)
    ) {
        items.forEach {
            DropdownMenuItem(
                text = { Text(it.title) },
                onClick = {
                    expanded = false
                    it.onClick()
                }
            )
        }
    }
}