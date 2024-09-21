package ru.mkn

import ru.mkn.data.Expr
import ru.mkn.data.ExprKind

class EvalVisitor {
    fun visit(expr: Expr): Result<Value> {
        when (expr.kind) {
            is ExprKind.Binary -> {

            }
            is ExprKind.Unary -> {

            }
            is ExprKind.Literal -> {

            }
        }
        TODO("unimplemented")
    }
}