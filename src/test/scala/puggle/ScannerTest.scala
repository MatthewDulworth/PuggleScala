package puggle

import org.scalatest.funsuite.AnyFunSuite

class ScannerTest extends AnyFunSuite {
  test("Empty input -> no tokens") {
    testScanner("", Nil)
  }

  test("Single Character Tokens") {
    testScanner("(){}.,;+-*", OPEN_PAREN :: CLOSE_PAREN :: OPEN_BRACE :: CLOSE_BRACE :: DOT ::
      COMMA :: SEMICOLON :: PLUS :: MINUS :: MULTIPLY :: Nil)
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
    testScanner("\"hello\"", StringLiteral("hello") :: Nil)
  }

  test("String and more") {
    testScanner("\"hello\" + -", StringLiteral("hello") :: PLUS :: MINUS :: Nil)
  }

  test("unterminated string") {
    testScanner("\"hello", StringLiteral("hello") :: Nil)
    testErrors(UnterminatedString(0))
  }

  test("multi-line string") {
    testScanner("\"hello\ngoodbye\"", StringLiteral("hello\ngoodbye") :: Nil)
  }

  test("multi-line unterminated string") {
    testScanner("\"hello\ngoodbye", StringLiteral("hello\ngoodbye") :: Nil)
    testErrors(UnterminatedString(0))
  }

  // ----------------------------------------
  // Number Literal Tests
  // ----------------------------------------
  test("Integer literal") {
    testScanner("12345", NumberLiteral(12345) :: Nil)
  }

  test("Decimal literal") {
    testScanner("12345.6789", NumberLiteral(12345.6789) :: Nil)
  }

  test("Multiline number literal") {
    testScanner("123.45\n6789", NumberLiteral(123.45) :: NumberLiteral(6789) :: Nil)
  }

  test("Multiple decimals") {
    testScanner("1.2.3", NumberLiteral(1.2) :: DOT :: NumberLiteral(3) :: Nil)
  }

  test("Multiline number with literal extra decimals") {
    testScanner("123.45\n67.89.10", NumberLiteral(123.45) :: NumberLiteral(67.89) ::
      DOT :: NumberLiteral(10) :: Nil)
  }

  // ----------------------------------------
  // Identifiers
  // ----------------------------------------
  test("Keywords") {
    testScanner("and or if else for while class func this true false nil val var print",
      AND :: OR :: IF :: ELSE :: FOR :: WHILE :: CLASS :: FUNC :: THIS :: TRUE ::
        FALSE :: NIL :: VAL :: VAR :: PRINT:: Nil)
  }

  test("If statement") {
    testScanner(
      """if (x == 2){
        | print(x);
        |}
        |""".stripMargin,
      IF :: OPEN_PAREN :: UserIdentifier("x") :: EQUAL :: NumberLiteral(2) :: CLOSE_PAREN ::
        OPEN_BRACE :: PRINT :: OPEN_PAREN :: UserIdentifier("x") :: CLOSE_PAREN :: SEMICOLON :: CLOSE_BRACE ::Nil)
  }

  // ----------------------------------------
  // Helpers
  // ----------------------------------------
  def testErrors(expected: Error*): Unit = {
    assertResult(expected.toList.toString()){Error.toString}
    Error.clear()
  }

  def testScanner(in: String, exp: List[Token]): Unit = {
    val tokens = Scanner(in).scan()
    val expected = EOF :: exp.reverse
    assertResult(expected.reverse){tokens}
  }
}
