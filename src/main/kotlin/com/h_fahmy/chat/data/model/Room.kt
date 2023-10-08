package com.h_fahmy.chat.data.model

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class Room(
    val name: String,
    val activeMembers: List<String> = emptyList(),
    @BsonId val _id: String = ObjectId().toString(),
)