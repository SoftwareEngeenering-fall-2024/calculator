package ru.mkn.visit

import ru.mkn.data.Expr
import ru.mkn.data.ExprKind
import com.github.michaelbull.result.*
import ru.mkn.Value

class EvalVisitor : Visitor<Result<Value, String>> {
    override fun visit(expr: Expr): Result<Value, String> {
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