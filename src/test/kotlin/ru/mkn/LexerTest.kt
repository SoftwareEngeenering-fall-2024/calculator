
import io.ktor.server.testing.*
import ru.mkn.Lexer
import ru.mkn.LiteralToken
import ru.mkn.PrimitiveToken
import ru.mkn.data.Literal
import kotlin.test.*

class LexerTest {
    @Test
    fun testCorrectStr() = testApplication {
        val str1 = "5+(4-6^2)"
        val str2 = "15+16*(165-16.15)"

        val expect1 = arrayListOf(
            LiteralToken(Literal.IntLit(5)),
            PrimitiveToken.Plus,
            PrimitiveToken.LeftParen,
            LiteralToken(Literal.IntLit(4)),
            PrimitiveToken.Minus,
            LiteralToken(Literal.IntLit(6)),
            PrimitiveToken.Cap,
            LiteralToken(Literal.IntLit(2)),
            PrimitiveToken.RightParen)
        val expect2 = arrayListOf(
            LiteralToken(Literal.IntLit(15)),
            PrimitiveToken.Plus,
            LiteralToken(Literal.IntLit(16)),
            PrimitiveToken.Star,
            PrimitiveToken.LeftParen,
            LiteralToken(Literal.IntLit(165)),
            PrimitiveToken.Minus,
            LiteralToken(Literal.FloatLit(16.15)),
            PrimitiveToken.RightParen)

        val res1 = Lexer(str1).tokenize()
        val res2 = Lexer(str2).tokenize()

        for (i in res1.indices)
            assertEquals(res1[i], expect1[i])
        for (i in res2.indices)
            assertEquals(res2[i], expect2[i])
    }

    @Test
    fun testMoreWhiteSpaces() = testApplication {
        val str1 = "5    +   (4       -6 ^2)"
        val str2 = "15    +16   *(  165   -16.15)"

        val expect1 = arrayListOf(
            LiteralToken(Literal.IntLit(5)),
            PrimitiveToken.Plus,
            PrimitiveToken.LeftParen,
            LiteralToken(Literal.IntLit(4)),
            PrimitiveToken.Minus,
            LiteralToken(Literal.IntLit(6)),
            PrimitiveToken.Cap,
            LiteralToken(Literal.IntLit(2)),
            PrimitiveToken.RightParen)
        val expect2 = arrayListOf(
            LiteralToken(Literal.IntLit(15)),
            PrimitiveToken.Plus,
            LiteralToken(Literal.IntLit(16)),
            PrimitiveToken.Star,
            PrimitiveToken.LeftParen,
            LiteralToken(Literal.IntLit(165)),
            PrimitiveToken.Minus,
            LiteralToken(Literal.FloatLit(16.15)),
            PrimitiveToken.RightParen)

        val res1 = Lexer(str1).tokenize()
        val res2 = Lexer(str2).tokenize()

        for (i in res1.indices)
            assertEquals(res1[i], expect1[i])
        for (i in res2.indices)
            assertEquals(res2[i], expect2[i])
    }

    @Test
    fun testIncorrectStr() = testApplication {
        val str1 = "++5 +-   ^2)"
        val str2 = "..15+16  *(..165-16.    15)"

        val expect1 = arrayListOf(
            PrimitiveToken.Plus,
            PrimitiveToken.Plus,
            LiteralToken(Literal.IntLit(5)),
            PrimitiveToken.Plus,
            PrimitiveToken.Minus,
            PrimitiveToken.Cap,
            LiteralToken(Literal.IntLit(2)),
            PrimitiveToken.RightParen)
        val expect2 = arrayListOf(
            LiteralToken(Literal.IntLit(15)),
            PrimitiveToken.Plus,
            LiteralToken(Literal.IntLit(16)),
            PrimitiveToken.Star,
            PrimitiveToken.LeftParen,
            LiteralToken(Literal.IntLit(165)),
            PrimitiveToken.Minus,
            LiteralToken(Literal.FloatLit(16.0)),
            LiteralToken(Literal.IntLit(15)),
            PrimitiveToken.RightParen)

        val res1 = Lexer(str1).tokenize()
        val res2 = Lexer(str2).tokenize()

        for (i in res1.indices)
            assertEquals(res1[i], expect1[i])
        for (i in res2.indices)
            assertEquals(res2[i], expect2[i])
    }
}

