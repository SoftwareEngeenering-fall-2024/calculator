package ru.mkn.data

enum class BinaryKind {
    Sum, Sub, Mul, Div, Power;

    override fun toString(): String {
        return when (this) {
            Sum -> "+"
            Sub -> "-"
            Mul -> "*"
            Div -> "/"
            Power -> "^"
        }
    }
}