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

case object TRUE extends Keyword with Literal
case object FALSE extends Keyword with Literal
case object NIL extends Keyword with Literal

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
sealed trait Literal extends Token

case class STRING(value: String) extends Literal
case class NUMBER(value: Double) extends Literal

// ----------------------------------------
// Operators
// ----------------------------------------
sealed trait Operator extends Token

case object DOT extends Operator
case object ASSIGN extends Operator

// Arithmetic Operators
case object MINUS extends Operator
case object PLUS extends Operator
case object MULTIPLY extends Operator
case object DIVIDE extends Operator

// Logical Operators
case object NOT extends Operator
case object NOT_EQUAL extends Operator
case object GREATER extends Operator
case object GREATER_EQUAL extends Operator
case object LESSER extends Operator
case object LESSER_EQUAL extends Operator
case object EQUAL extends Operator