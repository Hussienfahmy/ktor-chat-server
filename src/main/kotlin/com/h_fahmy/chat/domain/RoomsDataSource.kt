package com.h_fahmy.chat.domain

import com.h_fahmy.chat.data.model.Room

interface RoomsDataSource {
    suspend fun createRoom(roomName: String)

    suspend fun getRooms(): List<Room>

    suspend fun onMemberJoin(roomId: String, username: String)

    suspend fun onMemberLeft(roomId: String, username: String)
}