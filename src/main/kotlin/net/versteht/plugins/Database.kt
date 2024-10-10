//package net.versteht.plugins
//
//import io.ktor.server.application.*
//import org.jetbrains.exposed.*
//import java.sql.*
//import kotlinx.coroutines.*
//import org.jetbrains.exposed.sql.*
//
//fun Application.configureDatabases() {
//    Database.connect(
//        "jdbc:postgresql://localhost:5432/ktor_tutorial_db",
//        user = "postgres",
//        password = "password"
//    )
//}