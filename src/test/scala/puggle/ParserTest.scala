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

  test("Grouping: nested parens") {
    testParser(
      OPEN_PAREN :: OPEN_PAREN :: OPEN_PAREN :: FALSE :: CLOSE_PAREN :: CLOSE_PAREN :: CLOSE_PAREN :: EOF :: Nil,
      Some(Grouping(Grouping(Grouping(Literal(FALSE)))))
    )
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

  // ----------------------------------------
  // Test Factor
  // ----------------------------------------
  test("Factor: simple multiplication") {
    testParser(
      NUMBER(3) :: MULTIPLY :: NUMBER(2) :: EOF :: Nil,
      Some(Binary(MULTIPLY, Literal(NUMBER(3)), Literal(NUMBER(2))))
    )
  }

  test("Factor: simple division") {
    testParser(
      STRING("2") :: DIVIDE :: FALSE :: EOF :: Nil,
      Some(Binary(DIVIDE, Literal(STRING("2")), Literal(FALSE)))
    )
  }

  test("Factor: chained factors") {
    testParser(
      STRING("2") :: DIVIDE :: FALSE :: MULTIPLY :: TRUE :: EOF :: Nil,
      Some(Binary(MULTIPLY, Binary(DIVIDE, Literal(STRING("2")), Literal(FALSE)), Literal(TRUE)))
    )
  }

  test("Factor: parens") {
    testParser(
      OPEN_PAREN :: NUMBER(-1) :: CLOSE_PAREN :: MULTIPLY :: OPEN_PAREN :: NUMBER(45) :: CLOSE_PAREN :: EOF :: Nil,
      Some(Binary(MULTIPLY, Grouping(Literal(NUMBER(-1))), Grouping(Literal(NUMBER(45)))))
    )
  }
}
