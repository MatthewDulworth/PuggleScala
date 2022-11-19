package puggle


sealed trait Token:
  val lexeme: String

case object EOF extends Token:
  val lexeme = "\u0000"

// ----------------------------------------
// Identifiers
// ----------------------------------------
sealed trait Identifier extends Token
case class UserIdentifier(lexeme: String) extends Identifier
case class Keyword(lexeme: String) extends Identifier

// ----------------------------------------
// Separators
// ----------------------------------------
case object LEFT_PAREN extends Token:
  val lexeme = "("
case object RIGHT_PAREN extends Token:
  val lexeme = ")"

case object LEFT_BRACE extends Token:
  val lexeme = "{"
case object RIGHT_BRACE extends Token:
  val lexeme = "}"

case object COMMA extends Token:
  val lexeme = ","
case object SEMICOLON extends Token:
  val lexeme = ";"

// ----------------------------------------
// Literals
// ----------------------------------------
sealed trait Literal extends Token

case class StringLiteral(value: String) extends Literal:
  val lexeme: String = value
case class NumberLiteral(value: Double) extends Literal:
  val lexeme: String = value.toString

// ----------------------------------------
// Operators
// ----------------------------------------
sealed trait Operator extends Token

case object DOT extends Operator:
  val lexeme = "."
case object ASSIGN extends Operator:
  val lexeme = "="

// Arithmetic Operators
case object MINUS extends Operator:
  val lexeme = "-"
case object PLUS extends Operator:
  val lexeme = "+"
case object MULTIPLY extends Operator:
  val lexeme = "*"
case object DIVIDE extends Operator:
  val lexeme = "/"

// Logical Operators
case object NOT extends Operator:
  val lexeme = "!"
case object NOT_EQUAL extends Operator:
  val lexeme = "!="
case object GREATER extends Operator:
  val lexeme = ">"
case object GREATER_EQUAL extends Operator:
  val lexeme = ">="
case object LESSER extends Operator:
  val lexeme = "<"
case object LESSER_EQUAL extends Operator:
  val lexeme = "<="
case object EQUAL extends Operator:
  val lexeme = "=="