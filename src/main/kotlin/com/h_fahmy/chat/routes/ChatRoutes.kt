package com.h_fahmy.chat.routes

import com.h_fahmy.chat.room.ChatRoomController
import com.h_fahmy.chat.room.MemberAlreadyExistsException
import com.h_fahmy.chat.sessions.ChatSession
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.sessions.get
import io.ktor.server.sessions.sessions
import io.ktor.server.websocket.webSocket
import io.ktor.websocket.CloseReason
import io.ktor.websocket.Frame
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.channels.consumeEach

fun Route.chatSocket(chatRoomController: ChatRoomController) {
    webSocket(path = "/chat-socket") {
        val session = call.sessions.get<ChatSession>()
        if (session == null) {
            close(CloseReason(CloseReason.Codes.VIOLATED_POLICY, "No session"))
            return@webSocket
        }

        try {
            chatRoomController.onJoin(
                roomId = session.roomId,
                userName = session.username,
                sessionId = session.sessionId,
                socket = this
            )
            incoming.consumeEach { frame ->
                if (frame is Frame.Text) {
                    chatRoomController.sendMessage(
                        roomId = session.roomId,
                        senderUserName = session.username,
                        message = frame.readText()
                    )
                }
            }
        } catch (e: MemberAlreadyExistsException) {
            call.respond(HttpStatusCode.Conflict, "User already exists")
        } catch (e: Exception) {
            e.printStackTrace()
            call.respond(HttpStatusCode.InternalServerError, "Internal server error")
        } finally {
            chatRoomController.tryDisconnect(session.roomId, session.username)
        }
    }
}

fun Route.getAllMessages(chatRoomController: ChatRoomController) {
    get("/messages/{roomId}") {
        val roomId = call.parameters["roomId"] ?: return@get call.respond(
            status = HttpStatusCode.BadRequest,
            message = "Missing room id"
        )

        val messages = chatRoomController.getMessages(roomId)
        call.respond(status = HttpStatusCode.OK, messages)
    }
}