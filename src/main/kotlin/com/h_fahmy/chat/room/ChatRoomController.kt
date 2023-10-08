package com.h_fahmy.chat.room

import com.h_fahmy.chat.data.model.Chat
import com.h_fahmy.chat.data.model.Message
import com.h_fahmy.chat.data.model.Room
import com.h_fahmy.chat.domain.MessageDataSource
import com.h_fahmy.chat.domain.model.Member
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import kotlinx.serialization.json.Json
import java.util.concurrent.ConcurrentHashMap

class ChatRoomController(
    private val messageDataSource: MessageDataSource,
) {
    private val rooms = ConcurrentHashMap<String, Room>()
    private val roomMembers = ConcurrentHashMap<String, List<Member>>()

    fun onJoin(
        roomId: String,
        userName: String,
        sessionId: String,
        socket: WebSocketSession,
    ) {
        val member = Member(roomId, userName, sessionId, socket)

        val room = rooms.getOrPut(roomId) {
            Room(roomId, mutableListOf())
        }

        if (room.activeMembers.contains(member.userName)) {
            throw MemberAlreadyExistsException()
        }

        rooms[roomId] = room.copy(activeMembers = room.activeMembers + member.userName)
        roomMembers[roomId] = roomMembers[roomId]?.plus(member) ?: listOf(member)
    }

    suspend fun sendMessage(
        roomId: String,
        senderUserName: String,
        message: String,
    ) {
        val messageEntity = Message(
            text = message,
            userName = senderUserName,
            timeStamp = System.currentTimeMillis()
        )

        messageDataSource.insertMessage(roomId, messageEntity)

        val messageParsed = Json.encodeToString(Message.serializer(), messageEntity)

        roomMembers[roomId]?.forEach { member ->
            member.socket.send(Frame.Text(messageParsed))
        }
    }

    suspend fun getMessages(roomId: String): Chat {
        return messageDataSource.getAllMessages(roomId)
    }

    suspend fun tryDisconnect(roomId: String, userName: String) {
        roomMembers[roomId]?.find { it.userName == userName }?.socket?.close()
        roomMembers[roomId] = roomMembers[roomId]?.filter { it.userName != userName } ?: emptyList()

        val room = rooms[roomId] ?: return
        rooms[roomId] =
            room.copy(activeMembers = room.activeMembers.filter { it != userName })
    }
}