package com.h_fahmy.chat.domain

import com.h_fahmy.chat.data.model.Chat
import com.h_fahmy.chat.data.model.Message

interface MessageDataSource {

    suspend fun getAllMessages(roomId: String): Chat

    suspend fun insertMessage(roomId: String, message: Message)
}