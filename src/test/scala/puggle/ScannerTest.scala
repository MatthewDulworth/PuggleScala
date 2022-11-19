package puggle

import org.scalatest.funsuite.AnyFunSuite

class ScannerTest extends AnyFunSuite {
  test("Empty input -> no tokens") {
    testScanner("", Nil)
  }

  test("Single Character Tokens") {
    testScanner("(){}.,;+-*", LEFT_PAREN :: RIGHT_PAREN :: LEFT_BRACE :: RIGHT_BRACE :: DOT ::
      COMMA :: SEMICOLON :: PLUS :: MINUS :: MULTIPLY :: Nil)
  }

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

  def testErrors(expected: Error*): Unit = {
    assertResult(expected.toList.toString()){Error.toString}
  }

  def testScanner(in: String, exp: List[Token]): Unit = {
    val tokens = Scanner(in).scan()
    val expected = EOF :: exp.reverse
    assertResult(expected.reverse){tokens}
  }
}
