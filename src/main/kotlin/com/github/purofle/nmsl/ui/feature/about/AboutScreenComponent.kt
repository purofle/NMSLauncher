package com.github.purofle.nmsl.ui.feature.about

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.github.purofle.nmsl.ui.navigation.Component

class AboutScreenComponent(
    private val componentContext: ComponentContext,
): Component, ComponentContext by componentContext {

    @Composable
    override fun render() {

        AboutScreen()

    }
}