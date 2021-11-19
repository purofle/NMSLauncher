package com.github.purofle.monet

fun String.run(): String {
    val runtime = Runtime.getRuntime().exec(this)
    runtime.waitFor()
    return runtime.inputStream.reader().readText()
}