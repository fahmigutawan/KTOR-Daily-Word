package com.fahmigutawan


import com.fahmigutawan.module.configureDatabase
import com.fahmigutawan.module.configureEnv
import com.fahmigutawan.module.configureFrameworks
import com.fahmigutawan.module.configureHTTP
import com.fahmigutawan.module.configureMonitoring
import com.fahmigutawan.module.configureRouting
import com.fahmigutawan.module.configureSerialization
import com.fahmigutawan.util.env
import io.ktor.server.application.*
import org.slf4j.LoggerFactory

val logger = LoggerFactory.getLogger("logger")
var env = env()

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureEnv()
    configureDatabase()
    configureFrameworks()
    configureSerialization()
    configureMonitoring()
    configureHTTP()
    configureRouting()
}
