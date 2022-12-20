package puggle.data

object Tokens:
  val NO_LINE: Int = -100
  
  case class Token(lexeme: Lexeme, line: Int = NO_LINE)
  case object Token:
    def unapply(token: Token): Option[Lexeme] = Some(token.lexeme)
    def lexemesToTokens(lexemes: List[Lexeme]): List[Token] = lexemes.map(lexeme => Token(lexeme))
  
  sealed trait Lexeme
  case object EOF extends Lexeme

  // ----------------------------------------
  // Separators
  // ----------------------------------------
  case object OPEN_PAREN extends Lexeme
  case object CLOSE_PAREN extends Lexeme
  case object OPEN_BRACE extends Lexeme
  case object CLOSE_BRACE extends Lexeme
  case object COMMA extends Lexeme
  case object SEMICOLON extends Lexeme

  // ----------------------------------------
  // Literals
  // ----------------------------------------
  sealed trait LiteralToken extends Lexeme
  case class STRING(value: String) extends LiteralToken
  case class NUMBER(value: Double) extends LiteralToken
  
  // ----------------------------------------
  // Operators
  // ----------------------------------------
  sealed trait Operator extends Lexeme
  sealed trait UnaryOp extends Operator
  case object ASSIGN extends Operator
  case object NOT extends UnaryOp

  // Arithmetic Operators
  sealed trait BinaryOp extends Operator
  sealed trait Arithmetic extends BinaryOp
  case object MINUS extends Arithmetic with UnaryOp
  case object PLUS extends Arithmetic

  // Factor Operators
  sealed trait Factor extends BinaryOp
  case object STAR extends Factor
  case object DIVIDE extends Factor

  // Comparison Operators
  sealed trait Comparison extends BinaryOp
  case object GREATER extends Comparison
  case object GREATER_EQUAL extends Comparison
  case object LESSER extends Comparison
  case object LESSER_EQUAL extends Comparison

  // Equality Operators
  sealed trait Equality extends BinaryOp
  case object NOT_EQUAL extends Equality
  case object EQUAL extends Equality

  // ----------------------------------------
  // Identifiers
  // ----------------------------------------
  sealed trait Id extends Lexeme
  case class IDENTIFIER(lexeme: String) extends Id

  sealed trait Keyword extends Id
  case object TRUE extends Keyword with LiteralToken
  case object FALSE extends Keyword with LiteralToken
  
  case object AND extends Keyword with Operator
  case object OR extends Keyword with Operator
  
  case object IF extends Keyword
  case object ELSE extends Keyword
  case object WHILE extends Keyword
  
  case object FUNC extends Keyword
  case object VAL extends Keyword
  case object VAR extends Keyword
  
  case object PRINT extends Keyword