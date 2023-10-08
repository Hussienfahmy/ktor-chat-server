package com.h_fahmy.chat.plugins

import com.h_fahmy.chat.room.ChatRoomController
import com.h_fahmy.chat.room.RoomController
import com.h_fahmy.chat.routes.chatSocket
import com.h_fahmy.chat.routes.createRoom
import com.h_fahmy.chat.routes.getAllMessages
import com.h_fahmy.chat.routes.getAllRooms
import com.h_fahmy.chat.routes.home
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.routing.Routing
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val chatRoomController by inject<ChatRoomController>()
    val roomController by inject<RoomController>()

    install(Routing) {
        home()
        chatSocket(chatRoomController = chatRoomController)
        getAllMessages(chatRoomController = chatRoomController)
        createRoom(roomController = roomController)
        getAllRooms(roomController = roomController)
    }
}
