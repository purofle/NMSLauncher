package com.github.purofle.nmsl.ui.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountBox() {

    var edit by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            SmallTopAppBar({ Text("账号管理") })
        },
        floatingActionButton = {
            FloatingActionButton({ edit = !edit }) { Icon(Icons.Filled.Edit, "edit") }
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
                .padding(10.dp)
        ) {
            val stateVertical = rememberScrollState(0)

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(stateVertical)
                    .padding(end = 12.dp, bottom = 12.dp)
            ) {
                Column {
                    AccountList { edit }
                }
            }
            VerticalScrollbar(
                modifier = Modifier.align(Alignment.CenterEnd)
                    .fillMaxHeight(),
                adapter = rememberScrollbarAdapter(stateVertical)
            )
        }
    }
}

@Composable
fun AccountList(edit: () -> Boolean) {
    Column {
        Account({}, edit, "testuser", "离线登录")
        Spacer(Modifier.padding(10.dp))
        Account({}, edit, "testuser", "微软登陆")
    }
}

@Composable
fun Account(
    onClick: () -> Unit,
    edit: () -> Boolean,
    userName: String,
    userType: String
    ) {
    Button(
        onClick = onClick
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column {
                Text(userName, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(userType)
            }
//            Spacer(Modifier.width(20.dp))
            Spacer(Modifier.weight(1f))
                AnimatedVisibility(edit()) {
                    Row(Modifier.align(Alignment.CenterVertically)) {
                    Icon(Icons.Filled.Refresh, "", modifier = Modifier.clickable { })
                    Icon(Icons.Filled.Close, "", modifier = Modifier.clickable { })
                }
            }
        }
    }
}