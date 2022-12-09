package puggle.logic

import org.scalatest.compatible.Assertion
import org.scalatest.funsuite.AnyFunSuite
import puggle.data.expressions.*
import puggle.data.tokens.*
import puggle.logic
import puggle.logic.{MissingExpectedToken, Parser, UnexpectedToken}

class ParserErrorTest extends AnyFunSuite {
  def testError(in: List[Token], out: Option[Expr], errors: List[logic.Error]): Unit =
    logic.Error.clear()
    val res = Parser(in)
    assertResult(out)(res)
    assertResult(errors)(logic.Error.log)


  test("(") {
    testError(
      OPEN_PAREN :: EOF :: Nil,
      None,
      MissingExpectedToken(CLOSE_PAREN) :: Nil)
  }

  test(")") {
    testError(
      CLOSE_PAREN :: EOF :: Nil,
      None,
      UnexpectedToken(CLOSE_PAREN) :: Nil)
  }

  test("(expr") {
    testError(
      OPEN_PAREN :: TRUE :: EOF :: Nil,
      None,
      MissingExpectedToken(CLOSE_PAREN) :: Nil)
  }

  ignore("expr)") {
    //TODO: Make this work (needs statements)
    testError(
      TRUE :: CLOSE_PAREN :: EOF :: Nil,
      None,
      UnexpectedToken(CLOSE_PAREN) :: Nil)
  }

  ignore("()") {
    //TODO: Make this work (needs error synchronization)
    testError(
      OPEN_PAREN :: CLOSE_PAREN :: EOF :: Nil,
      None,
      MissingExpectedToken(CLOSE_PAREN) :: Nil)
  }

  ignore("-") {
    testError(
      MINUS :: EOF :: Nil,
      None,
      UnexpectedToken(STRING("-")) :: Nil)
  }
}
