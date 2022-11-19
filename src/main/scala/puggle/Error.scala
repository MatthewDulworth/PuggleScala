package puggle

import Console.{GREEN, RED, RESET}
import scala.collection.mutable.ListBuffer

case object Error:
   private val errors: ListBuffer[Error] = ListBuffer.empty
   def report(error: Error): Unit = errors.append(error)
   def clear(): Unit = errors.clear()
   def display(): Unit = errors.foreach(println(_))
   override def toString: String = errors.toList.toString

sealed trait Error:
   val line: Int
   val message: String
   override def toString: String = message

case class UnexpectedCharacter(char: Char, line: Int) extends Error:
   val message = s"Unexpected character '$char' at line $line"
   
case class UnterminatedString(line: Int) extends Error:
   val message = s"Unterminated String at line $line"