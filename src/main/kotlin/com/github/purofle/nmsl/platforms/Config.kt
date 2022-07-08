package com.github.purofle.nmsl.platforms

import com.github.purofle.nmsl.game.account.AccountManager
import com.github.purofle.nmsl.game.account.AccountManager.AccountList
import com.github.purofle.nmsl.utils.JsonUtils.toJsonString
import com.github.purofle.nmsl.utils.os.OperatingSystem
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlin.io.path.isRegularFile
import kotlin.io.path.readText
import kotlin.io.path.writeText
import kotlin.reflect.KProperty

class Config {
    operator fun getValue(accountManager: AccountManager, property: KProperty<*>): AccountList {
        val filePath = OperatingSystem.getWorkingDirectory("${property.name}.json")
        return if (!filePath.isRegularFile()) {
            AccountList(mutableListOf())
        } else {
            Json.decodeFromString(filePath.readText())
        }
    }

    operator fun setValue(accountManager: AccountManager, property: KProperty<*>, accountList: AccountList) {
        val filePath = OperatingSystem.getWorkingDirectory("${property.name}.json")
        filePath.writeText(accountList.toJsonString())
    }
}