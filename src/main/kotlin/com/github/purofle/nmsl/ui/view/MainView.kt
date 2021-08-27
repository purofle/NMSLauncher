package com.github.purofle.nmsl.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.ComponentContext
import com.github.purofle.nmsl.ui.model.Login
import com.github.purofle.nmsl.ui.theme.MaterialColors

@Composable
fun MainView(onLoginPressed: () -> Unit, login: Login) {
    val account by login.model
    print(account.account)
        val scaffoldState = rememberScaffoldState()
        Scaffold(
            scaffoldState = scaffoldState,
            //标题栏区域
            topBar = {
                TopAppBar(
                    title = { Text(text = "NMSLauncher") },
                    backgroundColor = MaterialColors.Teal300
                )
            },
            floatingActionButtonPosition = FabPosition.End,
        )
        //屏幕内容区域
        {
            Column(Modifier.fillMaxSize()) {
                Row(
                    Modifier.width(200.dp)
                        .fillMaxHeight()
                )
                {
                    Column(Modifier.fillMaxHeight().background(MaterialColors.Grey300)) {
                        Row {
                            AccountCard(account.account.name, account.account.type, onLoginPressed)
                        }
                        actionButton({}, Icons.Filled.Build, "build")
                    }
                }
            }
        }
    }
    @Composable
    fun AccountCard(name: String, account: String, login: () -> Unit) {
        Card(modifier = Modifier.padding(12.dp)
            .fillMaxWidth()
            .clickable(onClick = login)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(8.dp)
            ) {
                Image(Icons.Filled.AccountCircle, "accountBox")
                Spacer(modifier = Modifier.width(20.dp))
                Column {
                    Text(name, fontWeight = FontWeight.Bold)
                    Text(account, fontWeight = FontWeight.Medium)
                }
            }
        }
    }
@Composable
fun actionButton(onClick: () -> Unit, imageVector: ImageVector, text: String) {
    Button(
        onClick,
        Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(0.dp),
        colors = buttonColors(MaterialColors.Teal400, contentColor = Color.White),
        content = {
            Image(imageVector, null)
            Spacer(Modifier.width(80.dp))
            Text(text, modifier = Modifier.wrapContentWidth(Alignment.End))
        })
}