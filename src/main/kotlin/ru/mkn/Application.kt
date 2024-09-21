package ru.mkn

import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.serialization.json.Json
import io.ktor.server.routing.*
import io.ktor.server.resources.*
import kotlinx.serialization.encodeToString

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    install(Resources)
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
    }
}