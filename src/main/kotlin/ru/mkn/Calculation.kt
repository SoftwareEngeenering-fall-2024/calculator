package ru.mkn

import io.ktor.resources.*

@Resource("/calc")
data class Calculation(val expression: String)