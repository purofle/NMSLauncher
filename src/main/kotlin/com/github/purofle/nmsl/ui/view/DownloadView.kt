package com.github.purofle.nmsl.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

//下载游戏view
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DownloadView() {
   Scaffold(
      topBar = {
         SmallTopAppBar({ Text("游戏下载") })
      },
      content = {
         Column {

         }
      }
   )
}