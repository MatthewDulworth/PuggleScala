//package puggle
//
//
//sealed trait Token:
//  val lexeme: String
//
//case object EOF extends Token:
//  val lexeme = "\u0000"

//case class InvalidToken(lexeme: String, line: Int, offset: Int) extends Token
//
//case class Identifier(lexeme: String) extends Token
//
//// Parentheses
//case object OPEN_PAREN extends Token:
//  val lexeme = "("
//case object CLOSE_PAREN extends Token:
//  val lexeme = ")"
//
//// Braces
//case object OPEN_BRACE extends Token:
//  val lexeme = "{"
//case object CLOSE_BRACE extends Token:
//  val lexeme = "}"
//
//// Punctuation
//case object DOT extends Token:
//  val lexeme = "."
//case object COMMA extends Token:
//  val lexeme = ","
//
//// Arithmetic Operators
//case object MINUS extends Token:
//  val lexeme = "-"
//case object PLUS extends Token:
//  val lexeme = "+"
//case object MULTIPLY extends Token:
//  val lexeme = "*"
//case object DIVIDE extends Token:
//  val lexeme = "/"
//
