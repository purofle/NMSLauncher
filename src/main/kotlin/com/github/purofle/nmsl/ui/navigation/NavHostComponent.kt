package com.github.purofle.nmsl.ui.navigation

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetbrains.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.animation.child.crossfadeScale
import com.arkivanov.decompose.router.router
import com.arkivanov.essenty.parcelable.Parcelable
import com.github.purofle.nmsl.di.AppComponent
import com.github.purofle.nmsl.di.DaggerAppComponent
import com.github.purofle.nmsl.ui.feature.download.DownloadScreenComponent
import com.github.purofle.nmsl.ui.feature.home.HomeScreenComponent

class NavHostComponent(
    private val componentContext: ComponentContext
    ): Component, ComponentContext by componentContext {

    private val appComponent: AppComponent = DaggerAppComponent.create()

    sealed class Config : Parcelable {
        object Home : Config()
        object Download : Config()
    }

    private val router = router<Config, Component>(
        initialConfiguration = Config.Home,
        childFactory = ::createScreenComponent
    )

    private fun createScreenComponent(config: Config, componentContext: ComponentContext): Component = when (config) {
        is Config.Home -> HomeScreenComponent(
            appComponent = appComponent,
            componentContext = componentContext
        )
        is Config.Download -> DownloadScreenComponent(
            appComponent = appComponent,
            componentContext = componentContext)
    }

    @OptIn(ExperimentalDecomposeApi::class)
    @Composable
    override fun render() {
        Children(
            routerState = router.state,
            animation = crossfadeScale()
        ) { child ->
            println(child)
            child.instance.render()
        }
    }
}