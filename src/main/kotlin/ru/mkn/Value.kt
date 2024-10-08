package ru.mkn

import kotlinx.serialization.Serializable
import java.util.*
import kotlin.math.pow

sealed class Value {
    data class IntValue(val value: Int) : Value() {
        override fun toString(): String = value.toString()
    }

    data class FloatValue(val value: Double) : Value() {
        override fun toString(): String = value.toString()
    }

    operator fun plus(value: Value): Value = when (this) {
        is IntValue -> when (value) {
            is FloatValue -> FloatValue((this.value.toBigDecimal() + value.value.toBigDecimal()).toDouble())
            is IntValue -> IntValue(this.value + value.value)
        }

        is FloatValue -> when (value) {
            is FloatValue -> FloatValue((this.value.toBigDecimal() + value.value.toBigDecimal()).toDouble())
            is IntValue -> FloatValue((this.value.toBigDecimal() + value.value.toBigDecimal()).toDouble())
        }
    }

    operator fun minus(value: Value): Value = when (this) {
        is IntValue -> when (value) {
            is FloatValue -> FloatValue((this.value.toBigDecimal() - value.value.toBigDecimal()).toDouble())
            is IntValue -> IntValue(this.value - value.value)
        }

        is FloatValue -> when (value) {
            is FloatValue -> FloatValue((this.value.toBigDecimal() - value.value.toBigDecimal()).toDouble())
            is IntValue -> FloatValue((this.value.toBigDecimal() - value.value.toBigDecimal()).toDouble())
        }
    }

    fun pow(value: Value): Value = when (this) {
        is IntValue -> when (value) {
            is FloatValue -> FloatValue(this.value.toDouble().pow(value.value))
            is IntValue -> IntValue(this.value.toDouble().pow(value.value).toInt())
        }

        is FloatValue -> when (value) {
            is FloatValue -> FloatValue(this.value.pow(value.value))
            is IntValue -> FloatValue(this.value.pow(value.value))
        }
    }

    operator fun div(value: Value): Value? {
        if (value == IntValue(0) || value == FloatValue(0.0)) {
            return null
        }
        return when (this) {
            is IntValue -> when (value) {
                is FloatValue -> FloatValue((this.value.toBigDecimal() / value.value.toBigDecimal()).toDouble())
                is IntValue -> {
                    if (this.value % value.value == 0) {
                        IntValue(this.value / value.value)
                    }  else {
                        FloatValue(this.value.toDouble() / value.value)
                    }
                }
            }

            is FloatValue -> when (value) {
                is FloatValue -> FloatValue((this.value.toBigDecimal() / value.value.toBigDecimal()).toDouble())
                is IntValue -> FloatValue((this.value.toBigDecimal() / value.value.toBigDecimal()).toDouble())
            }
        }
    }

    operator fun times(value: Value): Value = when (this) {
        is IntValue -> when (value) {
            is FloatValue -> FloatValue(this.value * value.value)
            is IntValue -> IntValue(this.value * value.value)
        }

        is FloatValue -> when (value) {
            is FloatValue -> FloatValue(this.value * value.value)
            is IntValue -> FloatValue(this.value * value.value)
        }
    }

    operator fun unaryMinus(): Value = when (this) {
        is IntValue -> IntValue(-this.value)
        is FloatValue -> FloatValue(-this.value)
    }
}
