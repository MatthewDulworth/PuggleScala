package puggle

import org.scalatest.compatible.Assertion
import org.scalatest.funsuite.AnyFunSuite

class ParserTest extends AnyFunSuite {

  def testParser(in: List[TOKEN], out: Option[Expr]): Assertion =
    val res = Parser(in)
    assertResult(out)(res)
    assert(Error.noErrors)

  def testError(in: List[TOKEN], out: Option[Expr], errors: List[Error]): Assertion =
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
    testParser(OPEN_PAREN :: TRUE :: CLOSE_PAREN :: EOF :: Nil, Some(Grouping(Literal(TRUE))))
  }
}
