package puggle


sealed trait TOKEN
case object EOF extends TOKEN

// ----------------------------------------
// Identifiers
// ----------------------------------------
sealed trait ID extends TOKEN
case class IDENTIFIER(lexeme: String) extends ID

sealed trait KEYWORD extends ID
case object AND extends KEYWORD with OPERATOR
case object OR extends KEYWORD with OPERATOR

case object IF extends KEYWORD
case object ELSE extends KEYWORD
case object FOR extends KEYWORD
case object WHILE extends KEYWORD

case object CLASS extends KEYWORD
case object FUNC extends KEYWORD
case object THIS extends KEYWORD

case object TRUE extends KEYWORD with Lit
case object FALSE extends KEYWORD with Lit
case object NULL extends KEYWORD with Lit

case object VAL extends KEYWORD
case object VAR extends KEYWORD

case object PRINT extends KEYWORD

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
sealed trait Lit extends TOKEN

case class STRING(value: String) extends Lit
case class NUMBER(value: Double) extends Lit

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