package com.h_fahmy.chat.di

import com.h_fahmy.chat.data.MessageDataSourceImp
import com.h_fahmy.chat.data.RoomsDataSourceImpl
import com.h_fahmy.chat.domain.MessageDataSource
import com.h_fahmy.chat.domain.RoomsDataSource
import com.h_fahmy.chat.room.ChatRoomController
import com.h_fahmy.chat.room.RoomController
import com.mongodb.kotlin.client.coroutine.MongoClient
import org.koin.dsl.module

val mainModule = module {
    single {
        val DATABASE_USERNAME = System.getenv("DATABASE_USERNAME")
        val DATABASE_PASSWORD = System.getenv("DATABASE_PASSWORD")
        val DATABASE_HOST = System.getenv("DATABASE_HOST")
        val DATABASE_PORT = System.getenv("DATABASE_PORT")

        MongoClient.create(
            connectionString = "mongodb://$DATABASE_USERNAME:$DATABASE_PASSWORD@$DATABASE_HOST:$DATABASE_PORT/"
        ).getDatabase("app_dp")
    }

    single<MessageDataSource> {
        MessageDataSourceImp(get())
    }

    single<RoomsDataSource>{
        RoomsDataSourceImpl(get())
    }

    single {
        ChatRoomController(get(), get())
    }

    single {
        RoomController(get())
    }
}