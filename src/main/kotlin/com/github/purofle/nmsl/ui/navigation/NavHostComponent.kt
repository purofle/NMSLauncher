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
import com.github.purofle.nmsl.ui.feature.about.AboutScreenComponent
import com.github.purofle.nmsl.ui.feature.addgame.AddGameScreenComponent
import com.github.purofle.nmsl.ui.feature.home.HomeScreenComponent
import com.github.purofle.nmsl.ui.feature.manager.ManagerScreenComponent


class NavHostComponent(
    private val componentContext: ComponentContext,
    private val selectedItem: Int
    ): Component, ComponentContext by componentContext {

    private val appComponent: AppComponent = DaggerAppComponent.create()

    sealed class Config : Parcelable {
        object Home : Config()
        object Manager : Config()
        object AddGame : Config()
        object About : Config()

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

        is Config.Manager -> ManagerScreenComponent(
            appComponent = appComponent,
            componentContext = componentContext,
        )

        is Config.AddGame -> AddGameScreenComponent(
            appComponent = appComponent,
            componentContext = componentContext,
        )

        is Config.About -> AboutScreenComponent(
            componentContext = componentContext
        )
    }

    @OptIn(ExperimentalDecomposeApi::class)
    @Composable
    override fun render() {
        Children(
            routerState = router.state
        ) { child ->
            child.instance.render()
        }
        when (selectedItem) {
            1 -> router.push(Config.Manager)
            2 -> router.push(Config.AddGame)
            3 -> router.push(Config.About)
        }
    }
}