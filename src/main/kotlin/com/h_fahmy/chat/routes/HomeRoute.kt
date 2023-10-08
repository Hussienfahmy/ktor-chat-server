package com.h_fahmy.chat.routes

import io.ktor.server.application.call
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.home() {
    get("/") {
        call.respondText("Server is running...")
    }
}