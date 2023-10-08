package com.h_fahmy.chat.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Chat(
    val messages: List<Message>,
    val roomId: String
)