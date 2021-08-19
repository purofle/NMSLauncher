package com.github.purofle.nmsl.ui.root

import com.arkivanov.essenty.parcelable.Parcelable

sealed class ChildrenStates : Parcelable {
    object DownloadView : ChildrenStates()
    object MainView : ChildrenStates()
    object LoginView : ChildrenStates()
    object DownloadInfoView : ChildrenStates()
}