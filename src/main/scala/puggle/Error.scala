package puggle

import Console.{RED, RESET}
import scala.collection.mutable.ListBuffer

case object Error:
   private val errorLog: ListBuffer[Error] = ListBuffer.empty
   def clear(): Unit = errorLog.clear()
   def noErrors: Boolean = errorLog.isEmpty
   
   def report(error: Error): Unit = 
      errorLog.append(error)
      println(error)

   override def toString: String = errorLog.toList.toString()

sealed trait Error:
   val line: Int
   val message: String
   override def toString: String = s"$RESET$RED$message$RESET"

case class UnexpectedCharacter(char: Char, line: Int) extends Error:
   val message = s"Unexpected character '$char' at line $line"
   
case class UnterminatedString(line: Int) extends Error:
   val message = s"Unterminated String at line $line"