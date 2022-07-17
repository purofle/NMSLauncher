package com.github.purofle.nmsl.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.purofle.nmsl.game.Version

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VersionCard(version: Version) {
    Column {
        Card(
            modifier = Modifier.fillMaxWidth()
                .padding(6.dp)
        ) {
            Text(text = version.id)
            Text(version.type)
        }
    }
}