package com.h_fahmy.chat.di

import com.h_fahmy.chat.DATABASE_HOST
import com.h_fahmy.chat.DATABASE_PASSWORD
import com.h_fahmy.chat.DATABASE_PORT
import com.h_fahmy.chat.DATABASE_USERNAME
import com.h_fahmy.chat.data.MessageDataSourceImp
import com.h_fahmy.chat.domain.MessageDataSource
import com.h_fahmy.chat.room.RoomController
import com.mongodb.kotlin.client.coroutine.MongoClient
import org.koin.dsl.module

val mainModule = module {
    single {
        MongoClient.create(
            connectionString = "mongodb://$DATABASE_USERNAME:$DATABASE_PASSWORD@$DATABASE_HOST:$DATABASE_PORT/"
        ).getDatabase("message_db")
    }

    single<MessageDataSource> {
        MessageDataSourceImp(get())
    }

    single {
        RoomController(get())
    }
}