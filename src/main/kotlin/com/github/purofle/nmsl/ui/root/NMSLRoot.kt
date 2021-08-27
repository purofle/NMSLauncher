package com.github.purofle.nmsl.ui.root

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.push
import com.arkivanov.decompose.replaceCurrent
import com.arkivanov.decompose.router
import com.arkivanov.essenty.parcelable.Parcelable
import com.github.purofle.nmsl.ui.model.Login
import com.github.purofle.nmsl.ui.view.LoginView
import com.github.purofle.nmsl.ui.view.MainView
import kotlinx.parcelize.Parcelize

typealias Content = @Composable () -> Unit
fun <T : Any> T.asContent(content: @Composable (T) -> Unit): Content = { content(this) }

class NMSLRoot(
    componentContext: ComponentContext
) : ComponentContext by componentContext {

    private val router =
        router<Config, Content>(
            initialConfiguration = Config.MainView,
            handleBackButton = true,
            childFactory = ::createChild
        )

    val routerState = router.state

    private fun createChild(config: Config, componentContext: ComponentContext): Content =
        when (config) {
            is Config.MainView -> login(componentContext).asContent { MainView(::pushLoginView, it) }
            is Config.LoginView -> login(componentContext).asContent { LoginView(::replaceCurrentLogin, it) }
        }

    private fun login(componentContext: ComponentContext): Login =
        Login(componentContext)

    private fun replaceCurrentLogin() {
        router.replaceCurrent(Config.MainView)
    }

    private fun pushLoginView() {
        router.push(Config.LoginView)
    }

    sealed class Config : Parcelable {
        @Parcelize
        object MainView: Config()
        @Parcelize
        object LoginView: Config()
    }
}