package puggle.logic

import org.scalatest.compatible.Assertion
import org.scalatest.funsuite.AnyFunSuite
import puggle.data.Expressions.*
import puggle.data.Tokens.*
import puggle.data.Values.*

class InterpreterTest extends AnyFunSuite {
  def testIntrp(input: Expr, expect: Value): Assertion =
    Error.clear()
    val res = Interpreter(Some(input))
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
      Binary(STAR, Literal(NUMBER(1)), Literal(NUMBER(2))),
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

  test("Greater") {
    testIntrp(
      Binary(GREATER, Literal(NUMBER(1)), Literal(NUMBER(2))),
      BoolVal(false))
  }

  test("Lesser") {
    testIntrp(
      Binary(LESSER, Literal(NUMBER(1)), Literal(NUMBER(2))),
      BoolVal(true))
  }

  test("Greater Equal") {
    testIntrp(
      Binary(GREATER_EQUAL, Literal(NUMBER(2)), Literal(NUMBER(2))),
      BoolVal(true))
  }

  test("Lesser Equal") {
    testIntrp(
      Binary(LESSER_EQUAL, Literal(NUMBER(2)), Literal(NUMBER(2))),
      BoolVal(true))
  }
}
