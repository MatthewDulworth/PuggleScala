package puggle

import org.scalatest.compatible.Assertion
import org.scalatest.funsuite.AnyFunSuite

class InterpreterTest extends AnyFunSuite {
  def testIntrp(input: Expr, expect: Value) =
    Error.clear()
    val res = Interpreter.interpret(input)
    assertResult(expect)(res)
    assert(Error.noErrors)

  test("Negation") {
    testIntrp(
      Unary(MINUS, Literal(NUMBER(2))),
      NumberVal(-2))
  }

  test("Not") {
    testIntrp(
      Unary(NOT, Literal(TRUE)),
      BoolVal(false))
  }

  test("Addition") {
    testIntrp(
      Binary(PLUS, Literal(NUMBER(2)), Literal(NUMBER(2))),
      NumberVal(4))
  }

  test("Subtraction") {
    testIntrp(
      Binary(MINUS, Literal(NUMBER(2)), Literal(NUMBER(2))),
      NumberVal(0))
  }

  test("Multiplication") {
    testIntrp(
      Binary(MULTIPLY, Literal(NUMBER(1)), Literal(NUMBER(2))),
      NumberVal(2))
  }

  test("Division") {
    testIntrp(
      Binary(DIVIDE, Literal(NUMBER(2)), Literal(NUMBER(2))),
      NumberVal(1))
  }

  test("Equality") {
    testIntrp(
      Binary(EQUAL, Literal(NUMBER(2)), Literal(NUMBER(2))),
      BoolVal(true))
  }
}
