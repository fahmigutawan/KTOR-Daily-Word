package com.fahmigutawan.module

import com.fahmigutawan.di.mainModule
import com.fahmigutawan.di.tableModule
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureFrameworks() {
    install(Koin) {
        slf4jLogger()

        modules(
            mainModule(),
            tableModule()
        )
    }
}
