package com.github.purofle.nmsl.ui.view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.ComponentContext
import com.github.purofle.nmsl.ui.root.AbstractChildrenComponent
import kotlinx.coroutines.launch

class MainView(ctx: ComponentContext): AbstractChildrenComponent(ctx) {
    @Composable
    override fun render() {
        val scaffoldState = rememberScaffoldState()
        val scope = rememberCoroutineScope()
        Scaffold(
            scaffoldState = scaffoldState,
            //标题栏区域
            topBar = {
                TopAppBar(
                    title = { Text(text = "NMSL-Launcher") },
                    navigationIcon = {
                        IconButton({
                            scope.launch {
                                scaffoldState.drawerState.open()
                            }
                        }, content = {
                            Icon(Icons.Filled.Menu, contentDescription = "open")
                        })
                    }
                )
            },
            floatingActionButtonPosition = FabPosition.End,
            drawerContent = {
                Box {
                    Column {
                        Button({}, Modifier.fillMaxWidth(), content = { Text("下载") })
                        Spacer(modifier = Modifier.height(3.dp))
                        Button({}, Modifier.fillMaxWidth(), content = { Text("下载") })
                    }
                }
            },
        )
        //屏幕内容区域
        {
            Column(Modifier.fillMaxSize().background(Color.Black)) {  }
        }
    }
}