package puggle

sealed trait Expr
case class Binary(operator: TOKEN, left: Expr, right: Expr) extends Expr


// ----- Unary ----- //
case class Unary (operator: TOKEN, right: Expr) extends Expr

case object Unary extends Expr:
  def apply (operator: TOKEN, right: Option[Expr]): Option[Unary] = right match
    case Some(e) => Some(Unary(operator, e))
    case None => None

// ----- Grouping ----- //
case class Grouping (expression: Expr) extends Expr

case object Grouping extends Expr:
  def apply(expr: Option[Expr]): Option[Grouping] = expr match
    case Some(e) => Some(Grouping(e))
    case None => None

// ----- Literal ----- //
case class Literal (token: LiteralToken) extends Expr:
  val value: Any = token match
    case STRING(s) => s
    case NUMBER(n) => n
    case TRUE => true
    case FALSE => false
    case NULL => null