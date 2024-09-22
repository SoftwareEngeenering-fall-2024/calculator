package ru.mkn

import ru.mkn.data.Literal

class Lexer(val input: String) {
    private  val DOT = '.'
    private val tokens = arrayListOf<Token>()
    fun tokenize(): ArrayList<Token> {
        var curNum = ""

        for (i in input.indices) {
            val value = input[i].digitToIntOrNull()

            if (value != null || input[i] == DOT)
                curNum += input[i]
            else {
                if (curNum.toIntOrNull() != null)
                    tokens.add(LiteralToken(Literal.IntLit(curNum.toInt())))
                else if (curNum.toDoubleOrNull() != null)
                    tokens.add(LiteralToken(Literal.FloatLit(curNum.toDouble())))

                curNum = ""

                if (input[i] != ' ')
                    primitiveTokenize(input[i])
            }
            if (curNum.isNotEmpty() && curNum[0] == DOT)
                curNum = ""
        }
        return tokens
    }

    private fun primitiveTokenize(c: Char) {
        when (c) {
            '+' -> tokens.add(PrimitiveToken.Plus)
            '-' -> tokens.add(PrimitiveToken.Minus)
            '*' -> tokens.add(PrimitiveToken.Star)
            '/' -> tokens.add(PrimitiveToken.Slash)
            '(' -> tokens.add(PrimitiveToken.LeftParen)
            ')' -> tokens.add(PrimitiveToken.RightParen)
            '^' -> tokens.add(PrimitiveToken.Cap)
        }
    }
}