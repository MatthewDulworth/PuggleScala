package puggle

sealed trait Expr
case class Binary(operator: TOKEN, left: Expr, right: Expr) extends Expr
case class Unary (operator: TOKEN, right: Expr) extends Expr
case class Grouping (expression: Expr)
case class Literal (ttype: LITERAL):
  val value: Any = ttype.value
