package puggle

import org.scalatest.compatible.Assertion
import org.scalatest.funsuite.AnyFunSuite
import puggle.Data.{Binary, CLOSE_PAREN, DIVIDE, EOF, EQUAL, Expr, FALSE, GREATER, GREATER_EQUAL, Grouping, LESSER, LESSER_EQUAL, Literal, MINUS, MULTIPLY, NOT, NOT_EQUAL, NULL, NUMBER, OPEN_PAREN, PLUS, STRING, TRUE, Token, Unary}

class ParserTest extends AnyFunSuite {

  def testParser(in: List[Token], out: Option[Expr]): Unit =
    Error.clear()
    val res = Parser(in)
    assertResult(out)(res)
    assert(Error.noErrors)

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

  test("Grouping: nested parens") {
    testParser(
      OPEN_PAREN :: OPEN_PAREN :: OPEN_PAREN :: FALSE :: CLOSE_PAREN :: CLOSE_PAREN :: CLOSE_PAREN :: EOF :: Nil,
      Some(Grouping(Grouping(Grouping(Literal(FALSE)))))
    )
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


  // ----------------------------------------
  // Test Term
  // ----------------------------------------
  test("Term: simple addition") {
    testParser(
      NUMBER(3) :: PLUS :: NUMBER(2) :: EOF :: Nil,
      Some(Binary(PLUS, Literal(NUMBER(3)), Literal(NUMBER(2))))
    )
  }

  test("Term: simple subtraction") {
    testParser(
      STRING("2") :: MINUS :: FALSE :: EOF :: Nil,
      Some(Binary(MINUS, Literal(STRING("2")), Literal(FALSE)))
    )
  }

  test("Term: subtraction of negative") {
    testParser(
      MINUS :: NUMBER(2) :: MINUS :: MINUS :: NUMBER(1) :: EOF :: Nil,
      Some(Binary(MINUS, Unary(MINUS, Literal(NUMBER(2))), Unary(MINUS, Literal(NUMBER(1)))))
    )
  }

  test("Term: chained terms") {
    testParser(
      STRING("2") :: MINUS :: FALSE :: PLUS :: TRUE :: EOF :: Nil,
      Some(Binary(PLUS, Binary(MINUS, Literal(STRING("2")), Literal(FALSE)), Literal(TRUE)))
    )
  }

  test("Term: parens") {
    testParser(
      OPEN_PAREN :: NUMBER(-1) :: CLOSE_PAREN :: PLUS :: OPEN_PAREN :: NUMBER(45) :: CLOSE_PAREN :: EOF :: Nil,
      Some(Binary(PLUS, Grouping(Literal(NUMBER(-1))), Grouping(Literal(NUMBER(45)))))
    )
  }

  test("Term: terms with factors") {
    testParser(
      NUMBER(-1) :: MULTIPLY :: NUMBER(2) :: MINUS :: NUMBER(45) :: EOF :: Nil,
      Some(
        Binary(MINUS, Binary(MULTIPLY, Literal(NUMBER(-1)), Literal(NUMBER(2))), Literal(NUMBER(45))))
    )
  }

  test("Term: terms with factors 2") {
    testParser(
      NUMBER(-1) :: MINUS :: NUMBER(2) :: MULTIPLY :: NUMBER(45) :: EOF :: Nil,
      Some(Binary(MINUS,
        Literal(NUMBER(-1)),
        Binary(MULTIPLY,
          Literal(NUMBER(2)),
          Literal(NUMBER(45)) )))
    )
  }

  // ----------------------------------------
  // Test Comparison
  // ----------------------------------------
  test("Comparison: greater-than") {
    testParser(
      TRUE :: GREATER :: FALSE :: EOF :: Nil,
      Some(Binary(GREATER, Literal(TRUE), Literal(FALSE)))
    )
  }

  test("Comparison: less-than") {
    testParser(
      TRUE :: LESSER :: FALSE :: EOF :: Nil,
      Some(Binary(LESSER, Literal(TRUE), Literal(FALSE)))
    )
  }

  test("Comparison: greater-than-equal") {
    testParser(
      TRUE :: GREATER_EQUAL :: FALSE :: EOF :: Nil,
      Some(Binary(GREATER_EQUAL, Literal(TRUE), Literal(FALSE)))
    )
  }

  test("Comparison: less-than-equal") {
    testParser(
      TRUE :: LESSER_EQUAL :: FALSE :: EOF :: Nil,
      Some(Binary(LESSER_EQUAL, Literal(TRUE), Literal(FALSE)))
    )
  }

  test("Comparison: chained comparisons") {
    testParser(
      TRUE :: GREATER :: FALSE :: LESSER :: TRUE :: EOF :: Nil,
      Some(Binary(LESSER, Binary(GREATER, Literal(TRUE), Literal(FALSE)), Literal(TRUE)))
    )
  }

  test("Comparison: parens") {
    testParser(
      OPEN_PAREN :: OPEN_PAREN :: TRUE :: CLOSE_PAREN :: GREATER
        :: OPEN_PAREN :: NUMBER(45) :: CLOSE_PAREN :: CLOSE_PAREN :: EOF :: Nil,
      Some(Grouping(Binary(GREATER, Grouping(Literal(TRUE)), Grouping(Literal(NUMBER(45))))))
    )
  }


  // ----------------------------------------
  // Test Equality
  // ----------------------------------------
  test("Equality: simple equal") {
    testParser(
      TRUE :: EQUAL :: FALSE :: EOF :: Nil,
      Some(Binary(EQUAL, Literal(TRUE), Literal(FALSE)))
    )
  }

  test("Equality: simple not-equal") {
    testParser(
      TRUE :: NOT_EQUAL :: FALSE :: EOF :: Nil,
      Some(Binary(NOT_EQUAL, Literal(TRUE), Literal(FALSE)))
    )
  }

  test("Full Expression") {
    testParser(
      OPEN_PAREN :: OPEN_PAREN :: TRUE :: GREATER_EQUAL :: STRING("q") :: CLOSE_PAREN :: PLUS
        :: NUMBER(2) :: CLOSE_PAREN :: MULTIPLY :: FALSE :: EQUAL :: MINUS :: NUMBER(3) :: EOF :: Nil,
      Some(
        Binary(EQUAL,
          Binary(MULTIPLY,
            Grouping(Binary(PLUS,
              Grouping(Binary(GREATER_EQUAL,
                Literal(TRUE),
                Literal(STRING("q")))),
              Literal(NUMBER(2)))),
            Literal(FALSE)),
          Unary(MINUS, Literal(NUMBER(3)))))
    )
  }
}
