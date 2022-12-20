package puggle.logic

import org.scalatest.funsuite.AnyFunSuite
import puggle.data.keywords
import puggle.data.Tokens.*

class ScannerTest extends AnyFunSuite {
  test("Empty input -> no tokens") {
    testScanner("", Nil)
  }

  test("Single Character Tokens") {
    testScanner("(){},;+-*", OPEN_PAREN :: CLOSE_PAREN :: OPEN_BRACE :: CLOSE_BRACE ::
      COMMA :: SEMICOLON :: PLUS :: MINUS :: STAR :: Nil)
  }

  // ----------------------------------------
  // Keyword BiMap
  // ----------------------------------------
  test("Invalid keyword bimap string access") {
    val x: Option[Keyword] = keywords("sdfsdf")
    assert(x.isEmpty)
  }

  test("Valid keyword bimap string access") {
    val x: Option[Keyword] = keywords("if")
    assert(x.get == IF)
  }

  // ----------------------------------------
  // String Literal Tests
  // ----------------------------------------
  test("Single string") {
    testScanner("\"hello\"", STRING("hello") :: Nil)
  }

  test("String and more") {
    testScanner("\"hello\" + -", STRING("hello") :: PLUS :: MINUS :: Nil)
  }

  test("unterminated string") {
    testScanner("\"hello", STRING("hello") :: Nil)
    testErrors(UnterminatedString(0))
  }

  test("multi-line string") {
    testScanner("\"hello\ngoodbye\"", STRING("hello\ngoodbye") :: Nil)
  }

  test("multi-line unterminated string") {
    testScanner("\"hello\ngoodbye", STRING("hello\ngoodbye") :: Nil)
    testErrors(UnterminatedString(0))
  }

  // ----------------------------------------
  // Number Literal Tests
  // ----------------------------------------
  test("Integer literal") {
    testScanner("12345", NUMBER(12345) :: Nil)
  }

  test("Decimal literal") {
    testScanner("12345.6789", NUMBER(12345.6789) :: Nil)
  }

  test("Multiline number literal") {
    testScanner("123.45\n6789", NUMBER(123.45) :: NUMBER(6789) :: Nil)
  }

  test("Multiline number with literal extra decimals") {
    testScanner("123.45\n67.89+10", NUMBER(123.45) :: NUMBER(67.89) ::
      PLUS :: NUMBER(10) :: Nil)
  }

  // ----------------------------------------
  // Identifiers
  // ----------------------------------------
  test("Keywords") {
    testScanner("and or if else while func true false val var print",
      AND :: OR :: IF :: ELSE :: WHILE :: FUNC :: TRUE ::
        FALSE :: VAL :: VAR :: PRINT:: Nil)
  }

  test("User Identifiers") {
    testScanner("a*b f45",
      IDENTIFIER("a") :: STAR :: IDENTIFIER("b") :: IDENTIFIER("f45") :: Nil)
  }

  test("If statement") {
    testScanner(
      """if (x == 2){
        | print(x);
        |}
        |""".stripMargin,
      IF :: OPEN_PAREN :: IDENTIFIER("x") :: EQUAL :: NUMBER(2) :: CLOSE_PAREN ::
        OPEN_BRACE :: PRINT :: OPEN_PAREN :: IDENTIFIER("x") :: CLOSE_PAREN :: SEMICOLON :: CLOSE_BRACE ::Nil)
  }

  // ----------------------------------------
  // Helpers
  // ----------------------------------------
  def testErrors(expected: Error*): Unit = {
    assertResult(expected.toList.toString()){Error.toString}
    Error.clear()
  }

  def testScanner(in: String, exp: List[Lexeme]): Unit = {
    val tokens = Scanner(in).scan().map(token => token.lexeme)
    val expected = EOF :: exp.reverse
    assertResult(expected.reverse){tokens}
  }
}
