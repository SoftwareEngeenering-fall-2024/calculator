package ru.mkn.data

sealed class Literal {
    data class FloatLit(val value: Double)
    data class IntLit(val value: Int)
}