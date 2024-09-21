package ru.mkn.parser

import ru.mkn.PrimitiveToken
import ru.mkn.Token
import ru.mkn.data.BinaryKind

enum class Operator {
    Add, Subtract, Divide, Multiply, Power;

    companion object {
        fun fromToken(token: Token) : Operator? {
            return when (token) {
                PrimitiveToken.Plus -> Add
                PrimitiveToken.Minus -> Subtract
                PrimitiveToken.Slash -> Divide
                PrimitiveToken.Star -> Multiply
                PrimitiveToken.Cap -> Power
                else -> null
            }
        }


    }

    fun precedence() : Int {
        return when (this) {
            Power -> 7
            Multiply, Divide -> 6
            Add, Subtract -> 5
        }
    }

    fun toAstBinOp() : BinaryKind {
        return when (this) {
            Power -> BinaryKind.Power
            Multiply -> BinaryKind.Mul
            Divide -> BinaryKind.Div
            Subtract -> BinaryKind.Sub
            Add -> BinaryKind.Sum
        }
    }

}