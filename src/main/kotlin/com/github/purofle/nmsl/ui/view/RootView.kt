package com.github.purofle.nmsl.ui.view

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetbrains.Children
import com.github.purofle.nmsl.ui.root.NMSLRoot


@Composable
fun RootView(root: NMSLRoot) {
    Children(root.routerState) {
        it.instance()
    }
}