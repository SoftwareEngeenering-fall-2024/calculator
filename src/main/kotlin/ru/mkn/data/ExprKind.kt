package ru.mkn.data

sealed class ExprKind {
    data class Binary(val kind: BinaryKind, val left: Expr, val right: Expr) : ExprKind()
    data class Unary(val kind: UnaryKind, val expr: Expr) : ExprKind()
    data class Literal(val literal: ru.mkn.data.Literal) : ExprKind()
}