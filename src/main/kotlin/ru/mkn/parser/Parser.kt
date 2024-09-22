package ru.mkn.parser

import com.github.michaelbull.result.*
import ru.mkn.DummyToken
import ru.mkn.LiteralToken
import ru.mkn.PrimitiveToken
import ru.mkn.Token
import ru.mkn.data.BinaryKind
import ru.mkn.data.Expr
import ru.mkn.data.ExprKind
import ru.mkn.data.UnaryKind

class Parser(val tokens: ArrayList<Token>) {
    inner class TokenCursor {
        private val tokenStream = tokens.iterator()

        fun next(): Token? {
            return if (!tokenStream.hasNext()) {
                null
            } else {
                tokenStream.next()
            }
        }
    }

    private val tokenCursor = TokenCursor()
    private var token: Token = DummyToken

    init {
        bump()
    }

    private fun bump() {
        token = tokenCursor.next() ?: DummyToken
    }

    private fun makeExpr(exprKind: ExprKind): Expr = Expr(exprKind)

    private fun makeUnary(unOpKind: UnaryKind, expr: Expr): ExprKind = ExprKind.Unary(unOpKind, expr)

    private fun makeBinary(binOpKind: BinaryKind, left: Expr, right: Expr): ExprKind =
        ExprKind.Binary(binOpKind, left, right)

    private fun parseUnary(kind: UnaryKind): Result<ExprKind, String> = binding {
        bump()
        val expr = parsePrefix().bind()
        makeUnary(kind, expr)
    }

    private fun expect(curToken: Token): Result<Unit, String> = binding {
        if (token != curToken) {
            Err("Unexpected token $curToken, $token expected")
        } else {
            bump()
        }
    }

    private fun parsePrefix(): Result<Expr, String> = binding {
        when (val curToken = token) {
            is LiteralToken -> {
                bump()
                makeExpr(ExprKind.Literal(curToken.literal))
            }

            PrimitiveToken.Minus -> {
                val opKind = parseUnary(UnaryKind.Neg).bind()
                makeExpr(opKind)
            }

            PrimitiveToken.LeftParen -> {
                expect(PrimitiveToken.LeftParen)
                val expr = parse().bind()
                expect(PrimitiveToken.RightParen)
                expr
            }

            else -> {
                Err("Syntax error").bind()
            }
        }
    }

    private fun parseWithPrec(minPrec: Int): Result<Expr, String> = binding {
        var lhs = parsePrefix().bind()
        var op: Operator? = null
        while (true) {
            op = Operator.fromToken(token) ?: break
            val prec = op.precedence()
            if (prec < minPrec) {
                break
            }
            bump()
            val rhs = parseWithPrec(prec + 1).bind()
            lhs = makeExpr(makeBinary(op.toAstBinOp(), lhs, rhs))
        }
        lhs
    }

    fun parse(): Result<Expr, String> =
        parseWithPrec(0)
}