package com.github.purofle.nmsl.ui.feature.download

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import com.arkivanov.decompose.ComponentContext
import com.github.purofle.nmsl.di.AppComponent
import com.github.purofle.nmsl.ui.navigation.Component
import javax.inject.Inject

class DownloadScreenComponent(
    appComponent: AppComponent,
    val componentContext: ComponentContext
): Component, ComponentContext by componentContext {

    @Inject
    lateinit var viewModel: DownloadViewModel

    init {
        appComponent.inject(this)
    }
    @Composable
    override fun render() {
        val scope = rememberCoroutineScope()
        LaunchedEffect(viewModel) {
            viewModel.init(scope)
        }

        DownloadScreen(viewModel)

    }
}