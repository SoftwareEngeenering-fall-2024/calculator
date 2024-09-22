package ru.mkn

import kotlinx.serialization.Serializable
import java.util.*
import kotlin.math.pow

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

    operator fun plus(value: Value) : Value = when (this) {
        is IntValue -> when (value){
            is FloatValue -> FloatValue(this.value + value.value)
            is IntValue -> IntValue(this.value + value.value)
        }
        is FloatValue -> when (value) {
            is FloatValue -> FloatValue(this.value + value.value)
            is IntValue -> FloatValue(this.value + value.value)
        }
    }

    operator fun minus(value: Value) : Value = when (this) {
        is IntValue -> when (value){
            is FloatValue -> FloatValue((this.value.toBigDecimal() - value.value.toBigDecimal()).toDouble())
            is IntValue -> IntValue(this.value - value.value)
        }
        is FloatValue -> when (value) {
            is FloatValue -> FloatValue((this.value.toBigDecimal() - value.value.toBigDecimal()).toDouble())
            is IntValue -> FloatValue((this.value.toBigDecimal() - value.value.toBigDecimal()).toDouble())
        }
    }

    fun pow(value: Value) : Value = when (this) {
        is IntValue -> when (value){
            is FloatValue -> FloatValue(this.value.toDouble().pow(value.value))
            is IntValue -> IntValue(this.value.toDouble().pow(value.value).toInt())
        }
        is FloatValue -> when (value) {
            is FloatValue -> FloatValue(this.value.pow(value.value))
            is IntValue -> FloatValue(this.value.pow(value.value))
        }
    }

    operator fun div(value: Value) : Value {
        if (value == IntValue(0) || value == FloatValue(0.0)) {
            throw IllegalArgumentException("Division by zero!")
        }
        return when (this) {
            is IntValue -> when (value) {
                is FloatValue -> FloatValue(this.value/ value.value)
                is IntValue -> FloatValue(this.value.toDouble() / value.value)
            }

            is FloatValue -> when (value) {
                is FloatValue -> FloatValue(this.value / value.value)
                is IntValue -> FloatValue(this.value / value.value)
            }
        }
    }

    operator fun times(value: Value) : Value = when (this) {
        is IntValue -> when (value){
            is FloatValue -> FloatValue(this.value * value.value)
            is IntValue -> IntValue(this.value * value.value)
        }
        is FloatValue -> when (value) {
            is FloatValue -> FloatValue(this.value * value.value)
            is IntValue -> FloatValue(this.value * value.value)
        }
    }

    operator fun unaryMinus() : Value = when (this) {
        is IntValue -> IntValue(-this.value)
        is FloatValue -> FloatValue(-this.value)
    }
}
