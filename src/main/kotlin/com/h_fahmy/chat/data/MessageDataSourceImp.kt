package com.h_fahmy.chat.data

import com.h_fahmy.chat.data.model.Message
import com.h_fahmy.chat.domain.MessageDataSource
import com.mongodb.client.model.Sorts
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.toList

class MessageDataSourceImp(
    db: MongoDatabase
): MessageDataSource {

    private val messages = db.getCollection<Message>("messages")

    override suspend fun getAllMessages(): List<Message> {
        return messages.find()
            .sort(Sorts.descending(Message::timeStamp.name))
            .toList()
    }

    override suspend fun insertMessage(message: Message) {
        messages.insertOne(message)
    }
}