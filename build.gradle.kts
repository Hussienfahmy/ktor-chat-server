import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val slf4j_version: String by project
val mongo_driver_version: String by project
val koin_version: String by project

plugins {
    kotlin("jvm") version "1.9.10"
    id("io.ktor.plugin") version "2.3.4"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.10"
    id("com.github.johnrengelman.shadow") version "7.1.0"
}

group = "com.h_fahmy.chat"
version = "0.0.2"

application {
    mainClass.set("com.h_fahmy.chat.ApplicationKt")
    project.setProperty("mainClassName", mainClass.get())
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-websockets-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    implementation("io.ktor:ktor-server-call-logging-jvm")
    implementation("io.ktor:ktor-server-sessions-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("org.slf4j:slf4j-api:$slf4j_version")

    // Mongo driver
    implementation("org.mongodb:mongodb-driver-kotlin-coroutine:$mongo_driver_version")

    // Koin core features
    implementation("io.insert-koin:koin-core:$koin_version")
    implementation("io.insert-koin:koin-ktor:$koin_version")


    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}

tasks.withType<ShadowJar>() {
    manifest {
        attributes["Main-Class"] = application.mainClass.get()
    }
}
