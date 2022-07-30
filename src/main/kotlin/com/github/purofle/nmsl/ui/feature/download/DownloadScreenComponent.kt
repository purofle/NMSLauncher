package com.github.purofle.nmsl.ui.feature.download

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.Router
import com.github.purofle.nmsl.di.AppComponent
import com.github.purofle.nmsl.ui.navigation.Component
import com.github.purofle.nmsl.ui.navigation.NavHostComponent
import kotlinx.coroutines.launch
import javax.inject.Inject

class DownloadScreenComponent(
    private val componentContext: ComponentContext,
    appComponent: AppComponent,
    val router: Router<NavHostComponent.Config, Component>
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
            scope.launch {
                viewModel.init(scope)
            }
        }

        DownloadScreen(viewModel, router)
    }
}