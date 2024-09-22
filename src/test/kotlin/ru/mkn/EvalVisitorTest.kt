package ru.mkn

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import io.ktor.server.testing.*
import ru.mkn.data.*
import ru.mkn.visit.EvalVisitor
import kotlin.test.Test
import kotlin.test.assertEquals

class EvalVisitorTest {

    private val evalVisitor = EvalVisitor()

    @Test
    fun intLiteral() {
        val expr = Expr(ExprKind.Literal(Literal.IntLit(1)))
        assertEquals(Ok(Value.IntValue(1)), evalVisitor.visit(expr))
    }

    @Test
    fun floatLiteral() {
        val expr = Expr(ExprKind.Literal(Literal.FloatLit(1.0)))
        assertEquals(Ok(Value.FloatValue(1.0)), evalVisitor.visit(expr))
    }

    @Test
    fun unaryOperatorInt() {
        val left = Expr(ExprKind.Literal(Literal.IntLit(5)))
        val expr = Expr(ExprKind.Unary(UnaryKind.Neg, left))
        assertEquals(Ok(Value.IntValue(-5)), evalVisitor.visit(expr))
    }

    @Test
    fun unaryOperatorFloat() {
        val left = Expr(ExprKind.Literal(Literal.FloatLit(5.0)))
        val expr = Expr(ExprKind.Unary(UnaryKind.Neg, left))
        assertEquals(Ok(Value.FloatValue(-5.0)), evalVisitor.visit(expr))
    }

    @Test
    fun sumTwoArguments() {
        val left = Expr(ExprKind.Literal(Literal.FloatLit(3.3)))
        val right = Expr(ExprKind.Literal(Literal.IntLit(5)))
        val expr = Expr(ExprKind.Binary(BinaryKind.Sum, left, right))
        assertEquals(Ok(Value.FloatValue(8.3)), evalVisitor.visit(expr))
    }

    @Test
    fun mulTwoArguments() {
        val left = Expr(ExprKind.Literal(Literal.FloatLit(3.383)))
        val right = Expr(ExprKind.Literal(Literal.IntLit(4)))
        val expr = Expr(ExprKind.Binary(BinaryKind.Mul, left, right))
        assertEquals(Ok(Value.FloatValue(13.532)), evalVisitor.visit(expr))
    }

    @Test
    fun subTwoArguments() {
        val left = Expr(ExprKind.Literal(Literal.FloatLit(3.383)))
        val right = Expr(ExprKind.Literal(Literal.FloatLit(4.1)))
        val expr = Expr(ExprKind.Binary(BinaryKind.Sub, left, right))
        assertEquals(Ok(Value.FloatValue(-0.717)), evalVisitor.visit(expr))
    }

    @Test
    fun divTwoGoodArguments() {
        val left = Expr(ExprKind.Literal(Literal.IntLit(-5)))
        val right = Expr(ExprKind.Literal(Literal.IntLit(8)))
        val expr = Expr(ExprKind.Binary(BinaryKind.Div, left, right))
        assertEquals(Ok(Value.FloatValue(-0.625)), evalVisitor.visit(expr))
    }

    @Test
    fun divTwoBadArguments() {
        val left = Expr(ExprKind.Literal(Literal.IntLit(-5)))
        val right = Expr(ExprKind.Literal(Literal.IntLit(0)))
        val expr = Expr(ExprKind.Binary(BinaryKind.Div, left, right))
        assertEquals(Err("Division by zero!"), evalVisitor.visit(expr))
    }

    @Test
    fun powerTwoArguments() {
        val left = Expr(ExprKind.Literal(Literal.IntLit(-5)))
        val right = Expr(ExprKind.Literal(Literal.IntLit(4)))
        val expr = Expr(ExprKind.Binary(BinaryKind.Power, left, right))
        assertEquals(Ok(Value.IntValue(625)), evalVisitor.visit(expr))
    }

    @Test
    fun bigExpression() {
        val left1 = Expr(ExprKind.Literal(Literal.IntLit(-5)))
        val right1 = Expr(ExprKind.Literal(Literal.IntLit(2)))
        val expr1 = Expr(ExprKind.Binary(BinaryKind.Power, left1, right1)) // (-5)^2

        val left2 = Expr(ExprKind.Literal(Literal.FloatLit(11.324)))
        val expr2 = Expr(ExprKind.Binary(BinaryKind.Sum, expr1, left2)) // (-5)^2 + 11.324

        val right2 = Expr(ExprKind.Literal(Literal.IntLit(9)))
        val expr3 = Expr(ExprKind.Unary(UnaryKind.Neg, right2)) // -9

        val expr = Expr(ExprKind.Binary(BinaryKind.Sub, expr2, expr3)) // (-5)^2 + 11.324 - (-9)

        assertEquals(Ok(Value.FloatValue(45.324)), evalVisitor.visit(expr))
    }
}