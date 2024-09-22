package ru.mkn

import kotlinx.serialization.Serializable

@Serializable
data class EvaluatedCalculation(val expression: String, val result: String)