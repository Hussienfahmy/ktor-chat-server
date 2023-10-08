package com.h_fahmy.chat.data

import com.h_fahmy.chat.data.model.Chat
import com.h_fahmy.chat.data.model.Message
import com.h_fahmy.chat.domain.MessageDataSource
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.toList

class MessageDataSourceImp(
    db: MongoDatabase
): MessageDataSource {

    private val chatCollection = db.getCollection<Chat>("chat")

    override suspend fun getAllMessages(roomId: String): Chat {
        val chat = chatCollection.find(Filters.eq(Chat::roomId.name, roomId))
            .toList().firstOrNull() ?: return Chat(emptyList(), roomId)
        return chat.copy(messages = chat.messages.sortedByDescending { it.timeStamp })
    }

    override suspend fun insertMessage(roomId: String, message: Message) {
        val chat = chatCollection.find(Filters.eq(Chat::roomId.name, roomId))
            .toList().firstOrNull()

        if (chat == null) {
            chatCollection.insertOne(Chat(listOf(message), roomId))
        } else {
            chatCollection.updateOne(
                filter = Filters.eq(Chat::roomId.name, roomId),
                update = Updates.addToSet(Chat::messages.name, message)
            )
        }

    }
}