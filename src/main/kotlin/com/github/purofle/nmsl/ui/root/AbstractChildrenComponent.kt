package com.github.purofle.nmsl.ui.root

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext

abstract class AbstractChildrenComponent(
    ctx: ComponentContext
) : Component, ComponentContext by ctx {
    @Composable
    abstract override fun render()
}