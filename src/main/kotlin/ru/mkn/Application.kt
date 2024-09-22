package ru.mkn

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.github.michaelbull.result.andThen
import com.github.michaelbull.result.mapBoth
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.serialization.json.Json
import io.ktor.server.routing.*
import io.ktor.server.resources.*
import kotlinx.serialization.encodeToString

import ru.mkn.parser.Parser
import ru.mkn.visit.EvalVisitor

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
            val parser = Parser(lexer.tokenize())
            val evalVisitor = EvalVisitor()
            parser.parse().andThen { expr ->
                evalVisitor.visit(expr)
            }.mapBoth(
                { value ->
                    db.evaluationsQueries.insert(calculation.expression, value.toString())
                    call.respond(HttpStatusCode.OK, Json.encodeToString(value)) },
                { error ->
                    call.respond(HttpStatusCode.BadRequest, error)
                }
            )
        }
        get("/calc/hist") {
            val res = db.evaluationsQueries.selectAll().executeAsList().toString()
            call.respond(HttpStatusCode.OK, res)
        }
        post("/calc/hist") {
            call.respond(HttpStatusCode.OK)
        }
    }
}
