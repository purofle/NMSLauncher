package com.github.purofle.nmsl.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NameCard(iconUrl: String, text1: String, text2: String, isSvg: Boolean = false) {
    Column {
        Card(
            modifier = Modifier.fillMaxWidth()
                .height(80.dp)
                .align(Alignment.CenterHorizontally)
                .padding(10.dp)
        ) {
            Row(modifier = Modifier.padding(5.dp)) {

                if (isSvg) {
                    val density = LocalDensity.current
                    AsyncImage(
                        load = { loadSvgPainter(iconUrl, density) },
                        painterFor = { it },
                        contentDescription = "logo",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.width(200.dp)
                    )
                }

                AsyncImage(
                    load = { loadImageBitmap(iconUrl) },
                    painterFor = remember { { BitmapPainter(it) } },
                    contentDescription = "avatars",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.width(50.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(text1, fontWeight = FontWeight.Bold)
                    Text(text2, fontWeight = FontWeight.Light)
                }
            }
        }
    }
}