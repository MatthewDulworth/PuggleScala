package puggle

import scala.annotation.tailrec

case object Parser {
  def apply(tokens: List[TOKEN]): Option[Expr] =
    assert(tokens.nonEmpty)
    assert(tokens.last == EOF)

    val list = TokenList(tokens)
    Parser(list).parse()
}


private case class Parser(tokens: TokenList) {
  def parse(): Option[Expr] = expression()

  /**
   * @return expression → equality
   */
  private def expression(): Option[Expr] = primary()

  /**
   * @return equality → comparison ( ( "!=" | "==" ) comparison )*
   */
  private def equality(): Expr =
    var expr = comparison()
    while (tokens.matches(EQUAL, NOT_EQUAL)) {
      val operator = tokens.peek
      val right = comparison()
      expr = Binary(operator, expr, right)
    }
    expr

  /**
   * @return comparison → term ( ( ">" | ">=" | "<" | "<=" ) term )*
   */
  private def comparison(): Expr = ???

  /**
   * @return term → factor ( ( "-" | "+" ) factor )*
   */
  private def term(): Expr = ???

  /**
   * @return factor → unary ( ( "/" | "*" ) unary )*
   */
  private def factor(): Expr = ???

  /**
   * @return unary → ( "!" | "-" ) unary | primary
   */
  private def unary(): Expr = ???

  /**
   * Parses the next token into a primary expression.
   * @return primary → NUMBER | STRING | "true" | "false" | "nil" | grouping
   */
  private def primary(): Option[Expr] = tokens.next() match
    case t: LiteralToken => Some(Literal(t))
    case OPEN_PAREN => grouping()
    case EOF => None
    case _ => parseError(UnexpectedToken(tokens.peek))

  /**
   * Parses the next token into a grouping. 
   * @return grouping → "(" expression ")"
   */
  private def grouping(): Option[Expr] =
    val expr = expression()
    tokens.next() match
      case CLOSE_PAREN => Grouping(expr)
      case _ => parseError(MissingExpectedToken(CLOSE_PAREN))

  /**
   * Reports a parsing error than returns None.
   * @param error The type of parse error to report. 
   * @return None
   */
  private def parseError(error: Error): Option[Expr] =
    Error.report(error)
    None
}
