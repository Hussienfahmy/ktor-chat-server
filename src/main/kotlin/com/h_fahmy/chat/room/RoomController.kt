package com.h_fahmy.chat.room

import com.h_fahmy.chat.domain.RoomsDataSource

class RoomController(
    private val roomsDataSourceImp: RoomsDataSource,
) {
    suspend fun createRoom(roomName: String) {
        roomsDataSourceImp.createRoom(roomName)
    }

    suspend fun getRooms() = roomsDataSourceImp.getRooms()
}