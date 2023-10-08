package com.h_fahmy.chat.domain.model

import io.ktor.websocket.WebSocketSession
import kotlinx.serialization.Serializable

@Serializable
data class Member(
    val roomId: String,
    val userName: String,
    val sessionId: String,
    val socket: WebSocketSession
)