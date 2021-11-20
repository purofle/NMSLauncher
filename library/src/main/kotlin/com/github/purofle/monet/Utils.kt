package com.github.purofle.monet

import org.slf4j.LoggerFactory
import java.io.File
import java.net.URI
import java.net.URL

fun String.run(): String {
    val runtime = Runtime.getRuntime().exec(this)
    runtime.waitFor()
    return runtime.inputStream.reader().readText()
}


fun debug(log: String) {
    return LoggerFactory.getLogger(Any::class.java).debug(log)
}