package ru.mkn.visit

import ru.mkn.data.Expr
import ru.mkn.data.ExprKind
import ru.mkn.data.Literal

class SExpVisitor : Visitor<String> {
    override fun visit(expr: Expr): String {
        return when (val expr = expr.kind) {
            is ExprKind.Binary -> "(${expr.kind} ${visit(expr.left)} ${visit(expr.right)})"
            is ExprKind.Literal -> when (val lit = expr.literal) {
                is Literal.FloatLit -> lit.value.toString()
                is Literal.IntLit -> lit.value.toString()
            }
            is ExprKind.Unary -> "${expr.kind} ${visit(expr.expr)}"
        }
    }
}