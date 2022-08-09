package com.github.purofle.nmsl.ui.component

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.github.purofle.nmsl.utils.StringUtils.formatTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VersionCard(
    id: String,
    time: String,
    onClick: () -> Unit
) {
    val formattedTime = time.formatTime()
    Card(
        modifier = Modifier.fillMaxWidth()
            .padding(0.dp, 10.dp, 0.dp, 10.dp)
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier
                .padding(15.dp),
        ) {
            Text(text = id, style = TextStyle(fontWeight = FontWeight.Bold))
            Text(
                text = "更新时间: $formattedTime", style = TextStyle(
                    color = Color.Gray
                )
            )
        }
    }
}


@Preview
@Composable
fun VersionCardPreview() {
    Column {
        for (i in 0 until 2) {
            VersionCard(
                id = "1.0.0",
                "2022-03-10T09:51:38+00:00"
            ) {}
        }
    }
}