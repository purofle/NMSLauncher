package com.github.purofle.nmsl.di

import com.github.purofle.nmsl.ui.feature.download.DownloadScreenComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [

    ]
)
interface AppComponent {
    fun inject(downloadScreenComponent: DownloadScreenComponent)
}