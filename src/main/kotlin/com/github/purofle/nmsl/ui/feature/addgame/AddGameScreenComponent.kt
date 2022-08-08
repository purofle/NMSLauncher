package com.github.purofle.nmsl.ui.feature.addgame

import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import com.arkivanov.decompose.ComponentContext
import com.github.purofle.nmsl.di.AppComponent
import com.github.purofle.nmsl.seedColor
import com.github.purofle.nmsl.ui.navigation.Component
import com.kieronquinn.monetcompat.compose.MonetCompatDynamicTheme
import com.kieronquinn.monetcompat.core.MonetCompat
import javax.inject.Inject

class AddGameScreenComponent(
    private val componentContext: ComponentContext,
    appComponent: AppComponent
) : Component, ComponentContext by componentContext {

    init {
        appComponent.inject(this)
    }

    @Inject
    lateinit var addGameViewModel: AddGameViewModel

    @Composable
    override fun render() {
        var closeWindow by remember { mutableStateOf(false) }
        if (!closeWindow)
            Window(
                title = "添加游戏",
                onCloseRequest = { closeWindow = true }
            ) {
                MonetCompatDynamicTheme(MonetCompat(seedColor())) {
                    AddGameScreen(addGameViewModel)
                }
            }
    }
}