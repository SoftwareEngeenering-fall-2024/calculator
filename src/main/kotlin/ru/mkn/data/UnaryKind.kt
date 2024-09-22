package ru.mkn.data

enum class UnaryKind {
    Neg;

    override fun toString(): String {
        return when (this) {
            Neg -> "-"
        }
    }
}