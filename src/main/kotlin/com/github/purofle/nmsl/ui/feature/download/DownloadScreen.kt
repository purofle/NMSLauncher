package com.github.purofle.nmsl.ui.feature.download

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.purofle.nmsl.game.download.Version
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//下载游戏view
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DownloadScreen(viewModel: DownloadViewModel) {
   val versionManifest by viewModel.versions.collectAsState()
   val scope = rememberCoroutineScope()
   Scaffold(
      floatingActionButton = {
         FloatingActionButton({ //刷新
            scope.launch(context = Dispatchers.Default) {
               println(versionManifest)
            }
         }) { Icon(Icons.Filled.Refresh, "refresh") }
      },
      content = {

         val state = rememberLazyListState()

         Box(modifier = Modifier.padding(5.dp)) {
            VerticalScrollbar(
               modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
               adapter = rememberScrollbarAdapter(
                  scrollState = state
               )
            )

            LazyColumn(Modifier.fillMaxSize().padding(end = 12.dp), state) {
               itemsIndexed(versionManifest as List<Version>) { _, version ->
                  Box(
                     modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0, 0, 255))
                  ) {
                        Text(version.id)
                     }
                  Spacer(modifier = Modifier.height(20.dp))
               }
            }
         }
      }
   )
}