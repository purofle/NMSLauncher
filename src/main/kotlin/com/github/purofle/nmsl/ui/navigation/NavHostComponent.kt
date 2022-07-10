package com.github.purofle.nmsl.ui.navigation

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetbrains.Children
import com.arkivanov.decompose.router.push
import com.arkivanov.decompose.router.router
import com.arkivanov.essenty.parcelable.Parcelable
import com.github.purofle.nmsl.di.AppComponent
import com.github.purofle.nmsl.di.DaggerAppComponent
import com.github.purofle.nmsl.game.download.Version
import com.github.purofle.nmsl.ui.feature.about.AboutScreenComponent
import com.github.purofle.nmsl.ui.feature.download.DownloadScreenComponent
import com.github.purofle.nmsl.ui.feature.home.HomeScreenComponent
import com.github.purofle.nmsl.ui.feature.manager.ManagerScreenComponent
import com.github.purofle.nmsl.utils.Log


class NavHostComponent(
    private val componentContext: ComponentContext,
    private val selectedItem: Int
    ): Component, ComponentContext by componentContext {

    private val appComponent: AppComponent = DaggerAppComponent.create()

    sealed class Config : Parcelable {
        object Home : Config()
        object Manager : Config()

        object About: Config()
        data class Download(
            val version: Version
        ): Config()
    }

    private val router = router<Config, Component>(
        initialConfiguration = Config.Home,
        childFactory = ::createScreenComponent
    )

    private fun onVersionSelected(version: Version) {
        Log.logger.debug("切换到下载页面 version: $version")
        router.push(
            Config.Download(version)
        )
    }

    private fun createScreenComponent(config: Config, componentContext: ComponentContext): Component = when (config) {
        is Config.Home -> HomeScreenComponent(
            appComponent = appComponent,
            componentContext = componentContext
        )
        is Config.Manager -> ManagerScreenComponent(
            appComponent = appComponent,
            componentContext = componentContext,
            onVersionSelected = ::onVersionSelected
        )
        is Config.Download -> DownloadScreenComponent(
            appComponent = appComponent,
            componentContext = componentContext,
            version = config.version
        )
        is Config.About -> AboutScreenComponent(
            componentContext = componentContext
        )
    }

    @OptIn(ExperimentalDecomposeApi::class)
    @Composable
    override fun render() {
        println("state: ${router.state.value}")
        Children(
            routerState = router.state
        ) { child ->
            child.instance.render()
        }
        when (selectedItem) {
            1 -> router.push(Config.Manager)
            2 -> router.push(Config.About)
        }
    }
}