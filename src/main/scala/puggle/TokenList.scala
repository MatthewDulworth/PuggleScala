package puggle

import scala.annotation.tailrec

class TokenList(private val _tokens: List[TOKEN]) {
  private val tokens = _tokens.toVector
  private var cursor = -1

  def peek: TOKEN =
    if cursor < tokens.length || cursor < 0 then
      tokens(cursor)
    else EOF
  
  def peekNext: TOKEN =
    val i = cursor + 1
    if i < tokens.length || i < 0 then
      tokens(i)
    else EOF
  
  def next(): TOKEN =
    cursor += 1
    peek

  @tailrec
  final def matches(matchables: TOKEN*): Boolean = matchables match
    case m if m.isEmpty => false
    case m if m.head == peek => true
    case _ => matches(matchables.tail: _*)
}
