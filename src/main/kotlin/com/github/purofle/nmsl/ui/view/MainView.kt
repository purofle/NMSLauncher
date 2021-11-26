package com.github.purofle.nmsl.ui.view

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.github.purofle.nmsl.ui.viewmodel.LoginStore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView() {
    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf("Home", "AccountBox", "Manager", "Settings") // 主页，账号管理，游戏管理，设置
    val icons = listOf(Icons.Filled.Home, Icons.Filled.AccountBox,Icons.Filled.List, Icons.Filled.Settings)
    val state = LoginStore.state

    Scaffold(
        topBar = {
            SmallTopAppBar({ Text("NMSLauncher") }, actions = {
                if (selectedItem == 1) { // 当选择的是账号管理时，显示账号管理的菜单
                    IconButton({}) { Icon(Icons.Filled.Add, "Add") }
                }
            })
        }
    ) {
        Row {
            NavigationRail {
                items.forEachIndexed { index, item ->
                    NavigationRailItem(
                        icon = { Icon(icons[index], contentDescription = item) },
                        label = { Text(item) },
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                        },
                        alwaysShowLabel = false
                    )
                }
            }
            Crossfade(targetState = selectedItem) { item ->
                when (item) {
                    0 -> HomeView()
                    1 -> AccountBox(state)
                    2 -> DownloadView()
                }
            }
        }
    }
}