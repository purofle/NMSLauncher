package com.github.purofle.monet

import org.slf4j.LoggerFactory


fun debug(log: String) {
    return LoggerFactory.getLogger(Any::class.java).debug(log)
}