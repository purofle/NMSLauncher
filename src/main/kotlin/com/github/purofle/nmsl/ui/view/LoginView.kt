package com.github.purofle.nmsl.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.purofle.nmsl.ui.model.Login
import io.ktor.client.features.logging.*
import kotlinx.coroutines.launch

@Composable
fun LoginView(onBackPressed: () -> Unit, login: Login) {

    var mojang by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()
    val account by login.model
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        RadioButtonGroup(listOf("Mojang", "微软")) { mojang = it }
        if (mojang) {
            var userName by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            TextField(
                value = userName,
                onValueChange = { userName = it },
                label = { Text("用户名") }
            )
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("密码") }
            )
            Button({
//                scope.launch {
//                    if (
//                        com.github.purofle.nmsl.game.auth.yggdrasil.Login(
//                            userName, password, LogLevel.ALL
//                        ).authenticate()) {
//                        print("正版验证成功")
//                    } else {
//                        print("failed")
//                    }
                account.account.name = "test"
                login.onAddClicked()
                println("?")
            }, content = { Text("登陆") })
        } else {
            Button({}, content = { Text("微软登陆") })
        }
        Button(
            onClick = onBackPressed,
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("返回", fontSize = 18.sp)
        }
    }
}

@Composable
fun RadioButtonGroup(
    items: List<String>,
    isMojang: (Boolean) -> Unit,
    ) {
    val state = remember { mutableStateOf("") }
    Row {
        items.forEach { item ->
            Row(modifier = Modifier.padding(8.dp)) {
                RadioButton(
                    selected = state.value == item,
                    onClick = {
                        state.value = item
                        print(state.value)
                        when (state.value) {
                            "Mojang" -> isMojang(true)
                            "微软" -> isMojang(false)
                        }
                    }
                )
                Text(
                    text = item,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
        }
    }
}