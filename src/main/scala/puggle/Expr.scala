package puggle

sealed trait Expr
case class Binary(operator: TOKEN, left: Expr, right: Expr) extends Expr
case class Unary (operator: TOKEN, right: Expr) extends Expr
case class Grouping (expression: Expr) extends Expr

case class Literal (ttype: Lit) extends Expr:
  val value: Any = ttype match
    case STRING(s) => s
    case NUMBER(n) => n
    case TRUE => true
    case FALSE => false
    case NULL => null