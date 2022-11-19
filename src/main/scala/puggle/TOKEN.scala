package puggle


sealed trait TOKEN
case object EOF extends TOKEN

// ----------------------------------------
// Identifiers
// ----------------------------------------
sealed trait ID extends TOKEN
case class IDENTIFIER(lexeme: String) extends ID

sealed trait Keyword extends ID
case object AND extends Keyword with OPERATOR
case object OR extends Keyword with OPERATOR

case object IF extends Keyword
case object ELSE extends Keyword
case object FOR extends Keyword
case object WHILE extends Keyword

case object CLASS extends Keyword
case object FUNC extends Keyword
case object THIS extends Keyword

case object TRUE extends Keyword
case object FALSE extends Keyword
case object NIL extends Keyword

case object VAL extends Keyword
case object VAR extends Keyword

case object PRINT extends Keyword

// ----------------------------------------
// Separators
// ----------------------------------------
case object OPEN_PAREN extends TOKEN
case object CLOSE_PAREN extends TOKEN

case object OPEN_BRACE extends TOKEN
case object CLOSE_BRACE extends TOKEN

case object COMMA extends TOKEN
case object SEMICOLON extends TOKEN

// ----------------------------------------
// Literals
// ----------------------------------------
sealed trait LITERAL extends TOKEN:
  val value: Any

case class STRING(value: String) extends LITERAL
case class NUMBER(value: Double) extends LITERAL

// ----------------------------------------
// Operators
// ----------------------------------------
sealed trait OPERATOR extends TOKEN

case object DOT extends OPERATOR
case object ASSIGN extends OPERATOR

// Arithmetic Operators
case object MINUS extends OPERATOR
case object PLUS extends OPERATOR
case object MULTIPLY extends OPERATOR
case object DIVIDE extends OPERATOR

// Logical Operators
case object NOT extends OPERATOR
case object NOT_EQUAL extends OPERATOR
case object GREATER extends OPERATOR
case object GREATER_EQUAL extends OPERATOR
case object LESSER extends OPERATOR
case object LESSER_EQUAL extends OPERATOR
case object EQUAL extends OPERATOR