package com.fahmigutawan.module

import com.fahmigutawan.data.api.WordApi.wordRouting
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello!")
        }

        wordRouting()
    }
}
