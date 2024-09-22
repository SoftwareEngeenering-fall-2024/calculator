package ru.mkn.parser

import com.github.michaelbull.result.unwrap
import ru.mkn.LiteralToken
import ru.mkn.PrimitiveToken.*
import ru.mkn.Token
import ru.mkn.data.Literal
import ru.mkn.visit.SExpVisitor
import kotlin.test.Test
import kotlin.test.assertEquals

class ParserTest {

    private fun makeIntToken(value: Int): Token =
        LiteralToken(Literal.IntLit(value))

    private fun makeFloatToken(value: Double): Token =
        LiteralToken(Literal.FloatLit(value))

    private fun testParser(tokenStream: ArrayList<Token>, sexp: String) {
        val parser = Parser(tokenStream)
        val sExpVisitor = SExpVisitor()
        assertEquals(sexp, sExpVisitor.visit(parser.parse().unwrap()))
    }

    @Test
    fun smokeTest() {
        testParser(
            arrayListOf(
                makeIntToken(1), Minus, makeIntToken(2), Plus, makeIntToken(3)
            ), "(+ (- 1 2) 3)"
        )
    }

    @Test
    fun parenthesisTest() {
        testParser(
            arrayListOf(
                makeIntToken(1), Minus, LeftParen, makeIntToken(2), Plus, makeIntToken(3), RightParen
            ), "(- 1 (+ 2 3))"
        )
    }

    @Test
    fun expressionTest() {
        testParser(
            arrayListOf(
                LeftParen, makeIntToken(10), Plus, makeIntToken(100), Plus, LeftParen, makeIntToken(5), Minus, makeIntToken(10), Star, makeFloatToken(2.5), RightParen, RightParen, Slash, makeIntToken(100)
            ),
            "(/ (+ (+ 10 100) (- 5 (* 10 2.5))) 100)"
        )
    }
}