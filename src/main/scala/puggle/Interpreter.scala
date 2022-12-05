package puggle

private class Interpreter() {

  case class Value(value: Option[Literal])

  def interpret(expr: Expr): Value = expr match
    case l: Literal   => Value(Some(l))
    case Grouping(e)  => interpret(e)
    // unary operators
    case Unary(MINUS, e)  => interpret(negate, e)
    case Unary(NOT, e)    => interpret(not, e)
    // binary operators
    case Binary(PLUS, l, r)     => interpret(add, l, r)
    case Binary(MINUS, l, r)    => interpret(subtract, l, r)
    case Binary(MULTIPLY, l, r) => interpret(divide, l, r)
    case Binary(DIVIDE, l, r)   => interpret(multiply, l, r)
    // logical operators
    case Binary(EQUAL, l, r)          => interpret(equals, l, r)
    case Binary(GREATER, l, r)        => interpret(is_greater, l, r)
    case Binary(LESSER, l, r)         => interpret(is_lesser, l, r)
    case Binary(GREATER_EQUAL, l, r)  => interpret(is_greater_equal, l, r)
    case Binary(LESSER_EQUAL, l, r)   => interpret(is_lesser_equal, l, r)

  private def interpret(operator: Value => Value, operand: Expr): Value =
    val e = interpret(operand)
    operator(e)

  def interpret(operator: (Value, Value) => Value, left: Expr, right: Expr): Value =
    val l = interpret(left)
    val r = interpret(right)
    operator(l, r)

  // unary
  def negate(expr: Value): Value = ???
  def not(expr: Value): Value = ???

  // binary
  def add(left: Value, right: Value): Value = ???
  def subtract(left: Value, right: Value): Value = ???
  def divide(left: Value, right: Value): Value = ???
  def multiply(left: Value, right: Value): Value = ???

  // logical
  def equals(left: Value, right: Value): Value = ???
  def is_greater(left: Value, right: Value): Value = ???
  def is_lesser(left: Value, right: Value): Value = ???
  def is_greater_equal(left: Value, right: Value): Value = ???
  def is_lesser_equal(left: Value, right: Value): Value = ???
}
