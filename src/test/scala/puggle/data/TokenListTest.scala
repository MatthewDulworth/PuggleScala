package puggle.data

import org.scalatest.funsuite.AnyFunSuite
import puggle.data.Tokens.*

class TokenListTest extends AnyFunSuite {

  def testTL(in: List[Lexeme], advance: TokenList => Lexeme): Unit = {
    val tokens = TokenList(Token.lexemesToTokens(in))
    var t: Lexeme = EOF

    val out = Iterator.continually {
      t = advance(tokens); t
    }.takeWhile(_ => t != EOF).toList

    assertResult(in)(out)
  }

  test("TokenList") {
    testTL(IF :: OPEN_PAREN :: TRUE :: CLOSE_PAREN :: Nil,
      t => t.next().lexeme)
  }

  test("Empty TokenList") {
    testTL(Nil,
      t => t.next().lexeme)
  }

  test("Peek") {
   testTL(IF :: OPEN_PAREN :: TRUE :: CLOSE_PAREN :: Nil,
     t => {t.next(); t.peek.lexeme})
  }

  test("PeekNext") {
    testTL(IF :: OPEN_PAREN :: TRUE :: CLOSE_PAREN :: Nil,
      t => {val l = t.peekNext.lexeme; t.next(); l})
  }
}
