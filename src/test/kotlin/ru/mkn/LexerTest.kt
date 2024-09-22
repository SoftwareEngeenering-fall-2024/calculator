import ru.mkn.Lexer
import ru.mkn.LiteralToken
import ru.mkn.PrimitiveToken
import ru.mkn.data.Literal
import kotlin.test.*

class LexerTest {
    @Test
    fun testCorrectIntStr() {
        val str = "5+(4-6^2)"

        val expect = arrayListOf(
            LiteralToken(Literal.IntLit(5)),
            PrimitiveToken.Plus,
            PrimitiveToken.LeftParen,
            LiteralToken(Literal.IntLit(4)),
            PrimitiveToken.Minus,
            LiteralToken(Literal.IntLit(6)),
            PrimitiveToken.Cap,
            LiteralToken(Literal.IntLit(2)),
            PrimitiveToken.RightParen
        )

        val res = Lexer(str).tokenize()

        assertContentEquals(res, expect)
    }

    @Test
    fun testCorrectFloatStr() {
        val str = "15+16*(16.13-16.15)"

        val expect = arrayListOf(
            LiteralToken(Literal.IntLit(15)),
            PrimitiveToken.Plus,
            LiteralToken(Literal.IntLit(16)),
            PrimitiveToken.Star,
            PrimitiveToken.LeftParen,
            LiteralToken(Literal.FloatLit(16.13)),
            PrimitiveToken.Minus,
            LiteralToken(Literal.FloatLit(16.15)),
            PrimitiveToken.RightParen
        )

        val res = Lexer(str).tokenize()

        assertContentEquals(res, expect)
    }

    @Test
    fun testIncorrectStr() {
        val str = "++5+-^2)"

        val expect = arrayListOf(
            PrimitiveToken.Plus,
            PrimitiveToken.Plus,
            LiteralToken(Literal.IntLit(5)),
            PrimitiveToken.Plus,
            PrimitiveToken.Minus,
            PrimitiveToken.Cap,
            LiteralToken(Literal.IntLit(2)),
            PrimitiveToken.RightParen
        )

        val res = Lexer(str).tokenize()

        assertContentEquals(res, expect)
    }

    @Test
    fun testMoreDots1() {
        val str1 = ".2.1...2.4."

        val expect1 = arrayListOf(
            PrimitiveToken.DotToken,
            LiteralToken(Literal.FloatLit(2.1)),
            PrimitiveToken.DotToken,
            PrimitiveToken.DotToken,
            PrimitiveToken.DotToken,
            LiteralToken(Literal.FloatLit(2.4)),
            PrimitiveToken.DotToken
        )

        val res1 = Lexer(str1).tokenize()

        assertContentEquals(res1, expect1)
    }

    @Test
    fun testMoreDots2() {
        val str = "..15+16.*(..165-16.15)"

        val expect = arrayListOf(
            PrimitiveToken.DotToken,
            PrimitiveToken.DotToken,
            LiteralToken(Literal.IntLit(15)),
            PrimitiveToken.Plus,
            LiteralToken(Literal.FloatLit(16.0)),
            PrimitiveToken.Star,
            PrimitiveToken.LeftParen,
            PrimitiveToken.DotToken,
            PrimitiveToken.DotToken,
            LiteralToken(Literal.IntLit(165)),
            PrimitiveToken.Minus,
            LiteralToken(Literal.FloatLit(16.15)),
            PrimitiveToken.RightParen
        )

        val res = Lexer(str).tokenize()

        assertContentEquals(res, expect)
    }

}

