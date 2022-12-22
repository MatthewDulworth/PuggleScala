package puggle.data

import puggle.data.Tokens.*

object Values:
  sealed trait Value
  
  case object Empty extends Value:
    override def toString: String = "Empty"
  
  sealed trait Typed extends Value:
    val value: Any
  
  case class BoolVal(value: Boolean) extends Typed:
    override def toString: String = s"Boolean: $value"

  case class StringVal(value: String) extends Typed:
    override def toString: String = s"String: \"$value\""

  case class NumberVal(value: Double) extends Typed:
    override def toString: String = s"Number: $value"
  
  case object Typed:
    def apply(token: Lexeme, value: Any): Typed = token match
      case TRUE | FALSE => BoolVal(value.asInstanceOf[Boolean])
      case STRING(_) => StringVal(value.asInstanceOf[String])
      case NUMBER(_) => NumberVal(value.asInstanceOf[Double])
      case _ => ???