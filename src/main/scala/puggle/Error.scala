package puggle

import Console.{RED, RESET}
import scala.collection.mutable.ListBuffer

case object Error:
   private val errorLog: ListBuffer[Error] = ListBuffer.empty
   def log: List[Error] = errorLog.toList
   def clear(): Unit = errorLog.clear()
   def noErrors: Boolean = errorLog.isEmpty
   
   def report(error: Error): Unit = 
      errorLog.append(error)
      println(error)

   override def toString: String = log.toString()

sealed trait Error:
   val line: Int
   val message: String
   override def toString: String = s"$RESET$RED[$line] $message$RESET"


// ----------------------------------------
// Scanner Errors
// ----------------------------------------
sealed trait ScannerError extends Error

case class UnexpectedCharacter(char: Char, line: Int) extends ScannerError:
   val message = s"Unexpected character '$char'"
   
case class UnterminatedString(line: Int) extends ScannerError:
   val message = s"Unterminated string"


// ----------------------------------------
// Parser Errors
// ----------------------------------------
sealed trait ParserError extends Error

case class SyntaxError(token: Token) extends ParserError:
   val line: Int = -1
   val message: String = s"Syntax Error at \"$token\""

case class MissingExpectedToken(token: Token) extends ParserError:
   //TODO: Figure out how to get this info
   val line: Int = -1
   val message: String = s"Missing expected token \"$token\""

case class UnexpectedToken(token: Token) extends ParserError:
   //TODO: Figure out how to get this info 
   val line: Int = -1
   val message: String = s"Unexpected token \"$token\""
   