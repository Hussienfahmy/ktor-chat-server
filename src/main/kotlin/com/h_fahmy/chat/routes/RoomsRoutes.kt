package com.h_fahmy.chat.routes

import com.h_fahmy.chat.room.RoomController
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receiveText
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post

fun Route.createRoom(roomController: RoomController) {
    post("/create-room/{roomName}") {
        val roomName = call.parameters["roomName"] ?: call.receiveText()
        roomController.createRoom(roomName)
        call.respond(HttpStatusCode.OK, "Room $roomName created")
    }
}

fun Route.getAllRooms(roomController: RoomController) {
    get("/rooms") {
        val rooms = roomController.getRooms()
        call.respond(HttpStatusCode.OK, rooms)
    }
}