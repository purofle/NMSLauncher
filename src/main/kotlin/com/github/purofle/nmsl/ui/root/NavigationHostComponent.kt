package com.github.purofle.nmsl.ui.root

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.extensions.compose.jetbrains.Children
import com.arkivanov.decompose.push
import com.arkivanov.decompose.router
import com.github.purofle.nmsl.ui.view.MainView
import com.github.purofle.nmsl.ui.view.DownloadInfoView
import com.github.purofle.nmsl.ui.view.DownloadView
import com.github.purofle.nmsl.ui.view.LoginView

class NavigationHostComponent(
    ctx: ComponentContext
) : AbstractChildrenComponent(ctx) {

    private val navigationRouter = router<ChildrenStates, AbstractChildrenComponent>(
        initialConfiguration = ChildrenStates.MainView
    ) { state, context ->
        when (state) {
            is ChildrenStates.DownloadView -> DownloadView(context, ::switchDownloadInfoView)
            is ChildrenStates.LoginView -> LoginView(context)
            is ChildrenStates.MainView -> MainView(context, ::switchLoginView)
            is ChildrenStates.DownloadInfoView -> DownloadInfoView(context)
        }
    }

    private fun switchDownloadInfoView() {
        navigationRouter.push(ChildrenStates.DownloadInfoView)
    }
    private fun switchLoginView() {
        navigationRouter.push(ChildrenStates.LoginView)
    }

    @Composable
    override fun render() {
        Children(routerState = navigationRouter.state) {
            it.instance.render()
        }
    }
}