package puggle

import org.scalatest.funsuite.AnyFunSuite

class ParserTest extends AnyFunSuite {

  test("Test Primary: False Literal") {
    val tokens = FALSE :: Nil
    val res = Parser(tokens).parse()
    assertResult(Literal(FALSE))(res)
  }

  test("Test Primary: True Literal") {
    val tokens = TRUE :: Nil
    val res = Parser(tokens).parse()
    assertResult(Literal(TRUE))(res)
  }

  test("Test Primary: Number Literal") {
    val tokens = NUMBER(2) :: Nil
    val res = Parser(tokens).parse()
    assertResult(Literal(NUMBER(2)))(res)
  }

  test("Test Primary: String Literal") {
    val tokens = STRING("2") :: Nil
    val res = Parser(tokens).parse()
    assertResult(Literal(STRING("2")))(res)
  }

  test("Test Primary: Null Literal") {
    val tokens = NULL :: Nil
    val res = Parser(tokens).parse()
    assertResult(Literal(NULL))(res)
  }
}
