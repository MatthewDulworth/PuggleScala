package puggle


sealed trait Token
case object EOF extends Token

// ----------------------------------------
// Identifiers
// ----------------------------------------
sealed trait Id extends Token
case class IDENTIFIER(lexeme: String) extends Id

sealed trait Keyword extends Id
case object AND extends Keyword with Operator
case object OR extends Keyword with Operator

case object IF extends Keyword
case object ELSE extends Keyword
case object FOR extends Keyword
case object WHILE extends Keyword

case object CLASS extends Keyword
case object FUNC extends Keyword
case object THIS extends Keyword

case object TRUE extends Keyword with LiteralToken
case object FALSE extends Keyword with LiteralToken
case object NULL extends Keyword with LiteralToken

case object VAL extends Keyword
case object VAR extends Keyword

case object PRINT extends Keyword

// ----------------------------------------
// Separators
// ----------------------------------------
case object OPEN_PAREN extends Token
case object CLOSE_PAREN extends Token

case object OPEN_BRACE extends Token
case object CLOSE_BRACE extends Token

case object COMMA extends Token
case object SEMICOLON extends Token

// ----------------------------------------
// Literals
// ----------------------------------------
sealed trait LiteralToken extends Token

case class STRING(value: String) extends LiteralToken
case class NUMBER(value: Double) extends LiteralToken

// ----------------------------------------
// Operators
// ----------------------------------------
sealed trait Operator extends Token
sealed trait UnaryOp extends Operator
sealed trait BinaryOp extends Operator

case object DOT extends Operator
case object ASSIGN extends Operator

// Arithmetic Operators
case object MINUS extends BinaryOp with UnaryOp
case object PLUS extends BinaryOp

sealed trait Factor extends BinaryOp
case object MULTIPLY extends Factor
case object DIVIDE extends Factor

// Logical Operators
case object NOT extends UnaryOp
case object NOT_EQUAL extends Operator
case object GREATER extends Operator
case object GREATER_EQUAL extends Operator
case object LESSER extends Operator
case object LESSER_EQUAL extends Operator
case object EQUAL extends Operator