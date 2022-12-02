package puggle

import scala.annotation.tailrec

case object Parser {
  def apply(tokens: List[Token]): Option[Expr] =
    assert(tokens.nonEmpty)
    assert(tokens.last == EOF)

    val list = TokenList(tokens)
    Parser(list).parse()
}


private case class Parser(tokens: TokenList) {
  def parse(): Option[Expr] = expression(tokens.next())

  /**
   * @return expression → equality
   */
  private def expression(token: Token): Option[Expr] = term(token)

  /**
   * @return equality → comparison ( ( "!=" | "==" ) comparison )*
   */
  private def equality(): Option[Expr] = ???

  /**
   * @return comparison → term ( ( ">" | ">=" | "<" | "<=" ) term )*
   */
  private def comparison(): Option[Expr] = ???

  /**
   * @return term → factor ( ( "-" | "+" ) factor )*
   */
  private def term(token: Token): Option[Expr] = _term(factor(token))

  @tailrec
  private def _term(expr: Option[Expr]): Option[Expr] = tokens.peekNext match
    case t: Arithmetic => _term(Binary(tokens.next(), expr, factor(tokens.next())))
    case _ => expr

  /**
   * @return factor → unary ( ( "/" | "*" ) unary )*
   */
  private def factor(token: Token): Option[Expr] = _factor(unary(token))

  @tailrec
  private def _factor(expr: Option[Expr]): Option[Expr] = tokens.peekNext match
    case t: Factor => _factor(Binary(tokens.next(), expr, unary(tokens.next())))
    case _ => expr

  /**
   * @return unary → ( "!" | "-" ) unary | primary
   */
  private def unary(token: Token): Option[Expr] = token match
    case t: UnaryOp => Unary(t, unary(tokens.next()))
    case _ => primary(token)

  /**
   * Parses the next token into a primary expression.
   * @return primary → NUMBER | STRING | "true" | "false" | "nil" | grouping
   */
  private def primary(token: Token): Option[Expr] = token match
    case t: LiteralToken => Some(Literal(t))
    case OPEN_PAREN => grouping()
    case EOF => None
    case _ => parseError(UnexpectedToken(token))

  /**
   * Parses the next token into a grouping.
   * @return grouping → "(" expression ")"
   */
  private def grouping(): Option[Expr] =
    val expr = expression(tokens.next())
    tokens.next() match
      case CLOSE_PAREN => Grouping(expr)
      case _ => parseError(MissingExpectedToken(CLOSE_PAREN))

  /**
   * Reports a parsing error than returns None.
   * @param error The type of parse error to report.
   * @return None
   */
  private def parseError(error: ParserError): Option[Expr] =
    Error.report(error)
    None
}
