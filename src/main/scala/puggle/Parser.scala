package puggle

class Parser(private val list: List[TOKEN]) {
  private val tokens = TokenList(list)

  def parse(): Expr = expression()

  /**
   * @return expression → equality
   */
  private def expression(): Expr = primary()

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
  private def comparison(): Expr = term()

  /**
   * @return term → factor ( ( "-" | "+" ) factor )*
   */
  private def term(): Expr = factor()

  /**
   * @return factor → unary ( ( "/" | "*" ) unary )*
   */
  private def factor(): Expr = unary()

  /**
   * @return unary → ( "!" | "-" ) unary | primary
   */
  private def unary(): Expr = primary()

  /**
   * @return primary → NUMBER | STRING | "true" | "false" | "nil" | "(" expression ")"
   */
  private def primary(): Expr = tokens.next() match
    case t: Lit => Literal(t)

    // "(" expression ")"
    case OPEN_PAREN =>
      val expr = expression()
      tokens.next() match
        case CLOSE_PAREN => Grouping(expr)
        case _ =>
          Error.report(MissingExpectedToken(CLOSE_PAREN))
          expression()

    case _ =>
      Error.report(UnexpectedToken(tokens.peek))
      expression()
}
