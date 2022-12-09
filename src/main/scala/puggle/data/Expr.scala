package puggle.data

import puggle.data.tokens.*

package expressions:
  sealed trait Expr
  
  // ----- Binary ----- //
  case class Binary(operator: Token, left: Expr, right: Expr) extends Expr
  case object Binary extends Expr:
    def apply (operator: Token,
               left: Option[Expr],
               right: Option[Expr]): Option[Expr] = (left, right) match
      case (Some(l), Some(r)) => Some(Binary(operator, l, r))
      case _ => None
  
  // ----- Unary ----- //
  case class Unary (operator: Token, right: Expr) extends Expr
  case object Unary extends Expr:
    def apply (operator: Token, right: Option[Expr]): Option[Unary] = right match
      case Some(e) => Some(Unary(operator, e))
      case None => None
  
  // ----- Grouping ----- //
  case class Grouping (expr: Expr) extends Expr
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