package ru.mkn

import ru.mkn.data.Literal

class Lexer(val input: String) {
    private  val DOT = '.'
    private val formatedInput = input.replace(" ", "")
    private val tokens = arrayListOf<Token>()
    fun tokenize(): ArrayList<Token> {
        var curNum = ""

        for (i in formatedInput.indices) {
            val value = formatedInput[i].digitToIntOrNull()

            if (value != null || formatedInput[i] == DOT)
                curNum += formatedInput[i]
            else {
                if (curNum.toIntOrNull() != null)
                    tokens.add(LiteralToken(Literal.IntLit(curNum.toInt())))
                else if (curNum.toDoubleOrNull() != null)
                    tokens.add(LiteralToken(Literal.FloatLit(curNum.toDouble())))

                curNum = ""

                primitiveTokenize(formatedInput[i])
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