package com.h_fahmy.chat.data

import com.h_fahmy.chat.data.model.Room
import com.h_fahmy.chat.domain.RoomsDataSource
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.toList

class RoomsDataSourceImpl(
    db: MongoDatabase
) : RoomsDataSource {

    private val rooms = db.getCollection<Room>("rooms")

    override suspend fun createRoom(roomName: String) {
        rooms.insertOne(Room(roomName))
    }

    override suspend fun getRooms(): List<Room> {
        return rooms.find().toList()
    }

    override suspend fun onMemberJoin(roomId: String, username: String) {
        rooms.updateOne(
            filter = Filters.eq(Room::id.name, roomId),
            update = Updates.addToSet(Room::activeMembers.name, username)
        )
    }

    override suspend fun onMemberLeft(roomId: String, username: String) {
        rooms.updateOne(
            filter = Filters.eq(Room::id.name, roomId),
            update = Updates.pull(Room::activeMembers.name, username)
        )
    }
}