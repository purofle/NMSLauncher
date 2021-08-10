package com.github.purofle.nmsl.ui

import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import com.github.purofle.nmsl.ui.view.DownloadView

@OptIn(ExperimentalComposeUiApi::class)
fun mainView() = application {
    ExceptionWindows()
    Window(
        title = "NMSL-Launcher",
        onCloseRequest = ::exitApplication
    ) {
        DesktopMaterialTheme {
            val scaffoldState = rememberScaffoldState()
            Scaffold(
                scaffoldState = scaffoldState,
                drawerContent = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("test")
                    }
                },
                //标题栏区域
                topBar = {
                    TopAppBar(
                        title = { Text(text = "NMSL-Launcher") },
                    )
                },
                floatingActionButtonPosition = FabPosition.End

            )

            //屏幕内容区域
            {
                DownloadView()
            }
        }
    }
}

//从 mirai-compose 那里抄来的异常处理
@ExperimentalComposeUiApi
@Composable
fun ApplicationScope.ExceptionWindows(
    onException: (throwable: Throwable) -> Unit = { it.stackTraceToString() },
    state: WindowState = rememberWindowState(size = WindowSize(1280.dp, 768.dp))
) {
    var exception: Throwable? by remember { mutableStateOf(null) }
    var showException: Boolean by remember { mutableStateOf(false) }

    SideEffect {
        Thread.setDefaultUncaughtExceptionHandler { _, throwable ->
            onException(throwable)
            exception = throwable
            showException = true
        }
    }

    if (showException)
        Window(
            state = state,
            onCloseRequest = ::exitApplication
        ) {
            exception?.let {
                SelectionContainer {
                    Text(it.stackTraceToString())
                }
            }
        }
}