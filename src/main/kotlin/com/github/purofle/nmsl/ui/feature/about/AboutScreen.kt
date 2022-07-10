package com.github.purofle.nmsl.ui.feature.about

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.github.purofle.nmsl.ui.component.NameCard

//下载游戏view
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen() {
    Scaffold(
        topBar = {
            SmallTopAppBar(title = { Text("About") })
        }
    ) { pd ->
        Column(modifier = Modifier.padding(pd)) {
            Text("关于", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(10.dp))
            NameCard("http://q1.qlogo.cn/g?b=qq&nk=3272912942&s=640",
                "None's Super Minceraft Launcher",
                "0.0.1"
            )
            NameCard("http://q1.qlogo.cn/g?b=qq&nk=3272912942&s=640",
                "purofle",
                "主开发者"
            )
            Text("鸣谢", fontWeight = FontWeight.Bold)
            NameCard("https://pic1.afdiancdn.com/user/1abb556e211911e9864c52540025c377/avatar/52f8df537dde157bc442d4749ab9dce6_w800_h600_s80.jpg?imageView2/1/w/240/h/240",
                "bangbang93",
                "提供 BMCLAPI 下载源，请赞助支持 BMCLAPI"
            )
            NameCard("https://pic1.afdiancdn.com/user/1abb556e211911e9864c52540025c377/avatar/52f8df537dde157bc442d4749ab9dce6_w800_h600_s80.jpg?imageView2/1/w/240/h/240",
                "bangbang93",
                "提供 BMCLAPI 下载源，请赞助支持 BMCLAPI"
            )
            NameCard("https://bkimg.cdn.bcebos.com/pic/e4dde71190ef76c6a7efaa289344eafaaf51f3dec92c?x-bce-process=image/watermark,image_d2F0ZXIvYmFpa2UyNzI=,g_7,xp_5,yp_5/format,f_auto",
                "JetBrains",
                "提供 IDE 支持",
            )
        }
    }
}