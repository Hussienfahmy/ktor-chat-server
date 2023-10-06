package com.h_fahmy.chat.room

import com.h_fahmy.chat.data.model.Message
import com.h_fahmy.chat.domain.MessageDataSource
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import kotlinx.serialization.json.Json
import java.util.concurrent.ConcurrentHashMap

class RoomController(
    private val messageDataSource: MessageDataSource,
) {
    private val members = ConcurrentHashMap<String, Member>()

    fun onJoin(
        userName: String,
        sessionId: String,
        socket: WebSocketSession,
    ) {
        if (members.containsKey(userName)) {
            throw MemberAlreadyExistsException()
        }

        members[userName] = Member(userName, sessionId, socket)
    }

    suspend fun sendMessage(
        senderUserName: String,
        message: String,
    ) {
        val messageEntity = Message(
            text = message,
            userName = senderUserName,
            timeStamp = System.currentTimeMillis()
        )

        messageDataSource.insertMessage(messageEntity)

        val messageParsed = Json.encodeToString(Message.serializer(), messageEntity)

        members.values.forEach {member ->
            member.socket.send(Frame.Text(messageParsed))
        }
    }

    suspend fun getMessages(): List<Message> {
        return messageDataSource.getAllMessages()
    }

    suspend fun tryDisconnect(userName: String){
        members[userName]?.socket?.close()
        if (members.containsKey(userName)) {
            members.remove(userName)
        }
    }
}