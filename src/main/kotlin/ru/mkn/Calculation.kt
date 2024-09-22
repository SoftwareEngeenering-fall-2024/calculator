package ru.mkn

import io.ktor.resources.*

@Resource("/$ROUTE")
data class Calculation(val expression: String)