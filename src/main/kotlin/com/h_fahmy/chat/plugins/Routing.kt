package com.h_fahmy.chat.plugins

import com.h_fahmy.chat.room.RoomController
import com.h_fahmy.chat.routes.chatSocket
import com.h_fahmy.chat.routes.getAllMessages
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.routing.Routing
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val roomController by inject<RoomController>()
    install(Routing) {
        chatSocket(roomController = roomController)
        getAllMessages(roomController = roomController)
    }
}
