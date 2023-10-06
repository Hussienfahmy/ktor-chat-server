package com.h_fahmy.chat.domain

import com.h_fahmy.chat.data.model.Message

interface MessageDataSource {

    suspend fun getAllMessages(): List<Message>

    suspend fun insertMessage(message: Message)
}