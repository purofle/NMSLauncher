package com.github.purofle.nmsl.di

import com.github.purofle.nmsl.ui.feature.download.DownloadScreenComponent
import com.github.purofle.nmsl.ui.feature.home.HomeScreenComponent
import com.github.purofle.nmsl.ui.feature.manager.ManagerScreenComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component
interface AppComponent {
    fun inject(homeScreenComponent: HomeScreenComponent)
    fun inject(managerScreenComponent: ManagerScreenComponent)
    fun inject(downloadScreenComponent: DownloadScreenComponent)
}