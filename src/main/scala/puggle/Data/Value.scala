package puggle.Data

import puggle.*
import puggle.Data.{FALSE, LiteralToken, NUMBER, STRING, TRUE}


sealed trait Value

case object Empty extends Value

sealed trait Typed extends Value:
  val value: Any

case class BoolVal(value: Boolean) extends Typed
case class StringVal(value: String) extends Typed
case class NumberVal(value: Double) extends Typed

case object Typed:
  def apply(token: LiteralToken, value: Any): Typed = token match
    case TRUE | FALSE => BoolVal(value.asInstanceOf[Boolean])
    case STRING(_) => StringVal(value.asInstanceOf[String])
    case NUMBER(_) => NumberVal(value.asInstanceOf[Double])
    case _ => ???