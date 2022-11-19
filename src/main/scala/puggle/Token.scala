package puggle


sealed trait Token:
  val lexeme: String

case object EOF extends Token:
  val lexeme = "\u0000"

case class Identifier(lexeme: String) extends Token

// Parentheses
case object LEFT_PAREN extends Token:
  val lexeme = "("
case object RIGHT_PAREN extends Token:
  val lexeme = ")"

// Braces
case object LEFT_BRACE extends Token:
  val lexeme = "{"
case object RIGHT_BRACE extends Token:
  val lexeme = "}"

// Punctuation
case object DOT extends Token:
  val lexeme = "."
case object COMMA extends Token:
  val lexeme = ","
case object SEMICOLON extends Token:
  val lexeme = ";"

// Arithmetic Operators
case object ASSIGN extends Token:
  val lexeme = "="
case object MINUS extends Token:
  val lexeme = "-"
case object PLUS extends Token:
  val lexeme = "+"
case object MULTIPLY extends Token:
  val lexeme = "*"
case object DIVIDE extends Token:
  val lexeme = "/"

// Logical Operators
case object NOT extends Token:
  val lexeme = "!"
case object NOT_EQUAL extends Token:
  val lexeme = "!="
case object GREATER extends Token:
  val lexeme = ">"
case object GREATER_EQUAL extends Token:
  val lexeme = ">="
case object LESSER extends Token:
  val lexeme = "<"
case object LESSER_EQUAL extends Token:
  val lexeme = "<="
case object EQUAL extends Token:
  val lexeme = "=="

// Literals
case class StringLiteral(value: String) extends Token:
  val lexeme: String = value
case class NumberLiteral(value: Int) extends Token:
  val lexeme: String = value.toString