package ru.mkn.visit

import ru.mkn.data.Expr

interface Visitor<T> {
    fun visit(expr: Expr): T

}