package puggle.data

import tokens.*
import scala.annotation.tailrec

class TokenList(private val _tokens: List[Token]) {
  private val tokens = _tokens.toVector
  private var cursor = -1

  def peek: Token =
    if cursor < tokens.length || cursor < 0 then
      tokens(cursor)
    else EOF
  
  def peekNext: Token =
    val i = cursor + 1
    if i < tokens.length || i < 0 then
      tokens(i)
    else EOF
  
  def next(): Token =
    cursor += 1
    peek

  @tailrec final def matches(matchables: Token*): Boolean = matchables match
    case m if m.isEmpty => false
    case m if m.head == peek => true
    case _ => matches(matchables.tail: _*)
}
