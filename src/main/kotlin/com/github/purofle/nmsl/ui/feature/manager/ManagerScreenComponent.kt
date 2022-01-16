package com.github.purofle.nmsl.ui.feature.manager

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import com.arkivanov.decompose.ComponentContext
import com.github.purofle.nmsl.di.AppComponent
import com.github.purofle.nmsl.game.download.Version
import com.github.purofle.nmsl.ui.navigation.Component
import javax.inject.Inject

class ManagerScreenComponent(
    appComponent: AppComponent,
    private val componentContext: ComponentContext,
    private val onVersionSelected: (Version) -> Unit,
): Component, ComponentContext by componentContext {

    @Inject
    lateinit var viewModel: ManagerViewModel

    init {
        appComponent.inject(this)
    }
    @Composable
    override fun render() {
        val scope = rememberCoroutineScope()
        LaunchedEffect(viewModel) {
            viewModel.init(scope)
        }

        ManagerScreen(viewModel, onVersionSelected)

    }
}