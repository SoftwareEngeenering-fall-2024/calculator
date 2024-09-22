package ru.mkn

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.serialization.json.Json
import io.ktor.server.routing.*
import io.ktor.server.resources.*
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.encodeToString

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.configureDatabase(): Database {
    val driver: SqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    Database.Schema.create(driver)
    return Database(driver)
}
fun Application.module() {
    install(Resources)
    val db = configureDatabase()
    routing {
        get<Calculation> { calculation ->
            val lexer = Lexer(calculation.expression)
            val parser = Parser()
            val evalVisitor = EvalVisitor()
            parser.parse(lexer.tokenize()).map { expr ->
                evalVisitor.visit(expr).map {value ->
                    call.respond(Json.encodeToString(value))
                }
            }
        }
        // Dummy routes to demonstate DB api
        get("/expressions") {
            val res = db.evaluationsQueries.selectAll().executeAsList().toString()
            call.respond(HttpStatusCode.OK, res)
        }
        post("/expressions") {
            db.evaluationsQueries.insert("10 + 10", "20")
            call.respond(HttpStatusCode.OK)
        }
    }
}
