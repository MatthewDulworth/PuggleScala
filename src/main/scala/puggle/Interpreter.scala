package puggle

import scala.annotation.tailrec

object Interpreter {
  def interpret(expr: Expr): Value = expr match
    case l: Literal   => Typed(l.token, l.value)
    case Grouping(e)  => interpret(e)
    // unary operators
    case Unary(MINUS, e)    => interpret(negate, e)
    case Unary(NOT, e)      => interpret(not, e)
    // binary operators
    case Binary(PLUS, l, r)     => interpret(add, l, r)
    case Binary(MINUS, l, r)    => interpret(subtract, l, r)
    case Binary(MULTIPLY, l, r) => interpret(multiply, l, r)
    case Binary(DIVIDE, l, r)   => interpret(divide, l, r)
    // logical operators
    case Binary(EQUAL, l, r)          => interpret(equals, l, r)
    case Binary(GREATER, l, r)        => interpret(is_greater, l, r)
    case Binary(LESSER, l, r)         => interpret(is_lesser, l, r)
    case Binary(GREATER_EQUAL, l, r)  => interpret(is_greater_equal, l, r)
    case Binary(LESSER_EQUAL, l, r)   => interpret(is_lesser_equal, l, r)
    // catch-all
    case _ => Empty

  private def interpret(operator: Value => Value, operand: Expr): Value =
    interpret(operand) match
      case Empty => err(MissingOperand)
      case e => operator(e)

  def interpret(operator: (Value, Value) => Value, left: Expr, right: Expr): Value =
    (interpret(left), interpret(right)) match
      case (Empty, Empty) => err(MissingOperand)
        err(MissingOperand)
      case (Empty, _) | (_, Empty) => err(MissingOperand)
      case (l, r) => operator(l, r)

  def err(error: RuntimeError): Value =
    Error.report(error)
    Empty

  // unary
  def negate(operand: Value): Value = operand match
    case NumberVal(v) => NumberVal(-v)
    case _ => err(InvalidOperand)

  def not(operand: Value): Value = operand match
    case BoolVal(v) => BoolVal(!v)
    case _ => err(InvalidOperand)

  // binary
  def add(left: Value, right: Value): Value = (left, right) match
    case (NumberVal(l), NumberVal(r)) => NumberVal(l + r)
    case (StringVal(l), StringVal(r)) => StringVal(l + r)
    case _ => err(InvalidOperand)

  def subtract(left: Value, right: Value): Value = (left, right) match
    case (NumberVal(l), NumberVal(r)) => NumberVal(l - r)
    case _ => err(InvalidOperand)

  def divide(left: Value, right: Value): Value = (left, right) match
    case (NumberVal(l), NumberVal(r)) => NumberVal(l / r)
    case _ => err(InvalidOperand)

  def multiply(left: Value, right: Value): Value = (left, right) match
    case (NumberVal(l), NumberVal(r)) => NumberVal(l * r)
    case _ => err(InvalidOperand)

  // logical
  def equals(left: Value, right: Value): Value = BoolVal(left == right)

  def is_greater(left: Value, right: Value): Value = (left, right) match
    case (NumberVal(l), NumberVal(r)) => BoolVal(l > r)
    case _ => err(InvalidOperand)
    
  def is_lesser(left: Value, right: Value): Value = (left, right) match
    case (NumberVal(l), NumberVal(r)) => BoolVal(l < r)
    case _ => err(InvalidOperand)
  
  def is_greater_equal(left: Value, right: Value): Value = (left, right) match
    case (NumberVal(l), NumberVal(r)) => BoolVal(l >= r)
    case _ => err(InvalidOperand)
    
  def is_lesser_equal(left: Value, right: Value): Value = (left, right) match
    case (NumberVal(l), NumberVal(r)) => BoolVal(l <= r)
    case _ => err(InvalidOperand)
}
