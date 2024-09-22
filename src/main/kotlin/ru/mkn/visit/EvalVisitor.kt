package ru.mkn.visit

import com.github.michaelbull.result.*
import ru.mkn.Value
import ru.mkn.data.*

class EvalVisitor : Visitor<Result<Value, String>> {
    override fun visit(expr: Expr): Result<Value, String> {
        return when (val expr = expr.kind) {
            is ExprKind.Binary -> {
                val leftChild = visit(expr.left)
                val rightChild = visit(expr.right)

                val leftResult = leftChild.value
                val rightResult = rightChild.value

                when (expr.kind) {
                    BinaryKind.Sum -> Ok(leftResult + rightResult)
                    BinaryKind.Sub -> Ok(leftResult - rightResult)
                    BinaryKind.Mul -> Ok(leftResult * rightResult)
                    BinaryKind.Div -> {
                        val quotient = leftResult / rightResult
                        if (quotient == null) {
                            Err("Division by zero!")
                        }
                        else {
                            Ok(quotient)
                        }
                    }
                    BinaryKind.Power -> Ok(leftResult.pow(rightResult))
                }
            }
            is ExprKind.Unary -> {
                val child = visit(expr.expr)
                when (expr.kind) {
                    UnaryKind.Neg -> Ok(-child.value)
                }
            }
            is ExprKind.Literal -> {
                when (val lit = expr.literal) {
                    is Literal.FloatLit -> Ok(Value.FloatValue(lit.value))
                    is Literal.IntLit -> Ok(Value.IntValue(lit.value))
                }
            }
        }
    }
}