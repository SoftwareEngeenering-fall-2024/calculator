package ru.mkn

import ru.mkn.data.Literal

sealed interface Token
data class LiteralToken(val literal : Literal) : Token
enum class PrimitiveToken : Token {
    Plus, Minus, Slash, Star, Cap, LeftParen, RightParen
}

data object DummyToken : Token
