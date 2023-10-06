package com.h_fahmy.chat

import com.h_fahmy.chat.di.mainModule
import com.h_fahmy.chat.plugins.configureMonitoring
import com.h_fahmy.chat.plugins.configureRouting
import com.h_fahmy.chat.plugins.configureSecurity
import com.h_fahmy.chat.plugins.configureSerialization
import com.h_fahmy.chat.plugins.configureSockets
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.koin.ktor.plugin.Koin

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(Koin) {
        modules(mainModule)
    }
    configureSockets()
    configureSerialization()
    configureMonitoring()
    configureSecurity()
    configureRouting()
}
