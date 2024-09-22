package ru.mkn

import kotlinx.serialization.Serializable

@Serializable
sealed class Value {
    @Serializable
    data class IntValue(val value: Int) : Value()

    @Serializable
    data class FloatValue(val value: Double) : Value()

    override fun toString() : String {
        return when (this) {
            is FloatValue -> value.toString()
            is IntValue -> value.toString()
        }
    }
}
