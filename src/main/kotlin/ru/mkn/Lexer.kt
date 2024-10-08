package ru.mkn

import ru.mkn.data.Literal

class Lexer(private val input: String) {
    private  val DOT = '.'
    private val tokens = arrayListOf<Token>()
    private var curNum = ""
    private var isFloat = false
    fun tokenize(): ArrayList<Token> {

        input.forEach {
            if (it.digitToIntOrNull() != null || it == DOT) {
                parseLiteral(it)
            }
            else {
                parsePrimitiveToken(it)
            }
        }
        if (curNum.toIntOrNull() != null) {
            tokens.add(LiteralToken(Literal.IntLit(curNum.toInt())))
        } else if (curNum.toDoubleOrNull() != null) {
            tokens.add(LiteralToken(Literal.FloatLit(curNum.toDouble())))
        }
        return tokens
    }

    private fun parsePrimitiveToken(c: Char){
        if (curNum != "") {
            parseLiteral(' ')
        }
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
    private fun parseLiteral(c: Char) {
        val value = c.digitToIntOrNull()
        if (value != null) {
            curNum += c
        }
        else if (c == DOT && !isFloat && curNum != "") {
            curNum += c
            isFloat = true
        }
        else {
            if (curNum.toIntOrNull() != null) {
                tokens.add(LiteralToken(Literal.IntLit(curNum.toInt())))
            }
            else if (curNum.toDoubleOrNull() != null) {
                tokens.add(LiteralToken(Literal.FloatLit(curNum.toDouble())))
                isFloat = false
            }

            if (c != ' ') {
                tokens.add(PrimitiveToken.DotToken)
            }
            curNum = ""
        }
    }
}