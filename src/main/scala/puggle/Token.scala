package puggle

case class Token (ttype: TokenType, lexeme: String, private val _line: Option[Int] = None):
  override def toString: String = lexeme
  def line: Int = _line match
    case Some(i) => i
    case None => -1

case object Token:
  def apply(ttype: TokenType, lexeme: String, line: Int): Token = Token(ttype, lexeme, Some(line))