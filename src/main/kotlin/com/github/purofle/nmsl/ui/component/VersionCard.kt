package com.github.purofle.nmsl.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.onClick
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.router.Router
import com.arkivanov.decompose.router.push
import com.github.purofle.nmsl.game.Version
import com.github.purofle.nmsl.ui.navigation.Component
import com.github.purofle.nmsl.ui.navigation.NavHostComponent

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun VersionCard(
    version: Version,
    router: Router<NavHostComponent.Config, Component>
) {
    Column {
        Card(
            modifier = Modifier.fillMaxWidth()
                .onClick { router.push(NavHostComponent.Config.DownloadInfo(version.url)) }
                .padding(6.dp)
        ) {
            Text(text = version.id)
            Text(version.type)
        }
    }
}