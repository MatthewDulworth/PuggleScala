package puggle

import org.scalatest.compatible.Assertion
import org.scalatest.funsuite.AnyFunSuite

class ParserTest extends AnyFunSuite {

  def testParser(in: List[Token], out: Option[Expr]): Unit =
    Error.clear()
    val res = Parser(in)
    assertResult(out)(res)
    assert(Error.noErrors)

  def testError(in: List[Token], out: Option[Expr], errors: List[Error]): Unit =
    Error.clear()
    val res = Parser(in)
    assertResult(out)(res)
    assertResult(errors)(Error.log)

  // ----------------------------------------
  // Test Primary
  // ----------------------------------------
  test("Empty") {
    testParser(EOF :: Nil, None)
  }

  test("Primary: False Literal") {
    testParser(FALSE :: EOF :: Nil, Some(Literal(FALSE)))
  }

  test("Primary: True Literal") {
    testParser(TRUE :: EOF :: Nil, Some(Literal(TRUE)))
  }

  test("Primary: Number Literal") {
    testParser(NUMBER(2) :: EOF :: Nil, Some(Literal(NUMBER(2))))
  }

  test("Primary: String Literal") {
    testParser(STRING("2") :: EOF :: Nil, Some(Literal(STRING("2"))))
  }

  test("Primary: Null Literal") {
    testParser(NULL :: EOF :: Nil, Some(Literal(NULL)))
  }

  // ----------------------------------------
  // Test Grouping
  // ----------------------------------------
  test("Grouping") {
    testParser(
      OPEN_PAREN :: TRUE :: CLOSE_PAREN :: EOF :: Nil,
      Some(Grouping(Literal(TRUE)))
    )
  }

  test("Grouping: Missing closing paren") {
    testError(
      OPEN_PAREN :: TRUE :: EOF :: Nil,
      None,
      MissingExpectedToken(CLOSE_PAREN) :: Nil)
  }

  test("Grouping: Missing expression and closing paren") {
    testError(
      OPEN_PAREN :: EOF :: Nil,
      None,
      MissingExpectedToken(CLOSE_PAREN) :: Nil)
  }

  ignore("Grouping: Missing expression") {
    //TODO: Make this work (needs error synchronization)
    testError(
      OPEN_PAREN :: CLOSE_PAREN :: EOF :: Nil,
      None,
      MissingExpectedToken(CLOSE_PAREN) :: Nil)
  }

  // ----------------------------------------
  // Test Unary
  // ----------------------------------------
  test("Unary: negative") {
    testParser(
      MINUS :: NUMBER(3) :: EOF :: Nil,
      Some(Unary(MINUS, Literal(NUMBER(3))))
    )
  }

  test("Unary: not") {
    testParser(
      NOT :: FALSE :: EOF :: Nil,
      Some(Unary(NOT, Literal(FALSE)))
    )
  }

  test("Unary: nested unary") {
    testParser(
      NOT :: MINUS :: NOT :: FALSE :: EOF :: Nil,
      Some(Unary(NOT, Unary(MINUS, Unary(NOT, Literal(FALSE)))))
    )
  }

  test("Unary: missing expression") {
    testParser(
      NOT :: EOF :: Nil,
      None,
    )
  }
}
