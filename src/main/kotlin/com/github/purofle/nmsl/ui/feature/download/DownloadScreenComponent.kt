package com.github.purofle.nmsl.ui.feature.download

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.github.purofle.nmsl.di.AppComponent
import com.github.purofle.nmsl.ui.navigation.Component
import javax.inject.Inject

class DownloadScreenComponent(
    private val componentContext: ComponentContext,
    appComponent: AppComponent
): Component, ComponentContext by componentContext {

    @Inject
    lateinit var viewModel: DownloadViewModel

    init {
        appComponent.inject(this)
    }

    @Composable
    override fun render() {

    }
}