package puggle.data

import puggle.data.Tokens.*

object Expressions:
  sealed trait Expr

  // ----------------------------------------
  // Binary
  // ----------------------------------------
  /**
   * AST node representing a binary operation
   * @param operator Lexeme of the operator. 
   * @param left Left-hand expression.
   * @param right Right-hand expression.
   * @param line Line the operation occurs on (for errors).
   */
  case class Binary(operator: Lexeme, left: Expr, right: Expr, line: Int = NO_LINE) extends Expr
  // Binary Companion object
  case object Binary extends Expr:
    // Constructor that handles tokens and optional expressions
    def apply(token: Token, left: Option[Expr], right: Option[Expr]): Option[Expr] =
      (left, right) match
        case (Some(l), Some(r)) => Some(Binary(token.lexeme, l, r, token.line))
        case _ => None
    // Redefine pattern matching to ignore line.
    def unapply(binary: Binary): Option[(Lexeme, Expr, Expr)] =
      Some((binary.operator, binary.left, binary.right))
  
  // ----------------------------------------
  // Unary
  // ----------------------------------------
  /**
   * AST node representing a unary operation.
   * @param operator The lexeme of the operator.
   * @param operand The expression the operation is performed on.
   * @param line The line the operation occurs on (for errors).
   */
  case class Unary(operator: Lexeme, operand: Expr, line: Int = NO_LINE) extends Expr
  // Unary companion object.
  case object Unary extends Expr:
    // Constructor that handles Token and optional expressions
    def apply(token: Token, right: Option[Expr]): Option[Unary] = right match
      case Some(e) => Some(Unary(token.lexeme, e, token.line))
      case None => None
    // Redefine pattern matching to ignore line
    def unapply(unary: Unary): Option[(Lexeme, Expr)] =
      Some((unary.operator, unary.operand))


  // ----------------------------------------
  // Grouping
  // ----------------------------------------
  /**
   * AST node representing a expression grouping.
   * @param expr The expression being grouped.
   */
  case class Grouping(expr: Expr) extends Expr
  // Grouping companion object
  case object Grouping extends Expr:
    // Constructor that handles Token and optional expressions
    def apply(expr: Option[Expr]): Option[Grouping] = expr match
      case Some(e) => Some(Grouping(e))
      case None => None

  // ----------------------------------------
  // Literal
  // ----------------------------------------
  /**
   * AST Node representing a value literal.
   * @param lexeme The lexeme of the literal.
   * @param value The value of the literal.
   * @param line The line the literal appears on.
   */
  case class Literal(lexeme: Lexeme, value: Matchable, line: Int = NO_LINE) extends Expr
  // Literal companion object.
  case object Literal:
    // Constructor that handles tokens.
    def apply(token: Token): Literal =
      val value: Matchable = token.lexeme match
        case STRING(s) => s
        case NUMBER(n) => n
        case TRUE => true
        case FALSE => false
        case _ => ???
      Literal(token.lexeme, value, token.line)
    //Constructor for just a lexeme
    def apply(lexeme: Lexeme): Literal =
      val value: Matchable = lexeme match
        case STRING(s) => s
        case NUMBER(n) => n
        case TRUE => true
        case FALSE => false
        case _ => ???
      Literal(lexeme, value, NO_LINE)
    // Redefine pattern matching to ignore line
    def unapply(literal: Literal): Option[(Lexeme, Matchable)] =
      Some((literal.lexeme, literal.value))