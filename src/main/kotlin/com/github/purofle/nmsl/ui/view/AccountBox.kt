package com.github.purofle.nmsl.ui.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountBox() {

    var edit by remember { mutableStateOf(false) }

    Scaffold(
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
                    AccountList({ edit })
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
            Column(
                modifier = Modifier.fillMaxHeight()
                    .align(Alignment.CenterVertically)
            ) {
                Text(userName)
                Text(userType)
            }
            Spacer(Modifier.weight(1f))
            AnimatedVisibility(edit()) {
                Row {
                    Icon(Icons.Filled.Close, "", modifier = Modifier.clickable { })
                }
            }
        }
    }
}