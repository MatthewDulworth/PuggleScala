package puggle.logic

import org.scalatest.compatible.Assertion
import org.scalatest.funsuite.AnyFunSuite
import puggle.data.Expressions.*
import puggle.data.Tokens.*

class ParserErrorTest extends AnyFunSuite {
  def testError(in: List[Token], out: Option[Expr], errors: List[Error]): Unit =
    Error.clear()
    val res = Parser(in)
    assertResult(out)(res)
    assertResult(errors)(Error.log)


  test("(") {
    testError(
      Token.lexemesToTokens(OPEN_PAREN :: EOF :: Nil),
      None,
      MissingExpectedToken(CLOSE_PAREN, Token(OPEN_PAREN)) :: Nil)
  }

  test(")") {
    testError(
      Token.lexemesToTokens(CLOSE_PAREN :: EOF :: Nil),
      None,
      UnexpectedToken(Token(CLOSE_PAREN)) :: Nil)
  }

  test("(expr") {
    testError(
      Token.lexemesToTokens(OPEN_PAREN :: TRUE :: EOF :: Nil),
      None,
      MissingExpectedToken(CLOSE_PAREN, Token(TRUE)) :: Nil)
  }

//  ignore("expr)") {
//    //TODO: Make this work (needs statements)
//    testError(
//      TRUE :: CLOSE_PAREN :: EOF :: Nil,
//      None,
//      UnexpectedToken(CLOSE_PAREN) :: Nil)
//  }

//  ignore("()") {
//    //TODO: Make this work (needs error synchronization)
//    testError(
//      OPEN_PAREN :: CLOSE_PAREN :: EOF :: Nil,
//      None,
//      MissingExpectedToken(CLOSE_PAREN) :: Nil)
//  }

//  ignore("-") {
//    testError(
//      MINUS :: EOF :: Nil,
//      None,
//      UnexpectedToken(STRING("-")) :: Nil)
//  }
}
