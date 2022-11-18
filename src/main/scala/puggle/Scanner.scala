package puggle

import scala.collection.mutable.ListBuffer
import TokenType.*

class Scanner(src: String) {

  private var cursor = -1
  private var line = 0

  /**
   * @return Scans the given string of source code into a List of Tokens.
   */
  def scan(): List[Token] = {
    val tokenBuffer: ListBuffer[Token] = ListBuffer()

    println("Scanning Source...")
    // create a list of tokens by calling nextToken until EOF is hit
    var currentToken = nextToken()

    while currentToken.ttype != EOF do
      tokenBuffer += currentToken
      currentToken = nextToken()

    tokenBuffer += currentToken
    println(s"Lines: ${line + 1}")
    tokenBuffer.to(List)
  }

  /**
   * @return The next token in the source string
   */
  private def nextToken(): Token = {
    nextChar match
      case Some(c) => getToken(c)
      case None => Token(EOF, "EOF")
  }

  /**
   * @return The next character in the source string.
   */
  private def nextChar: Option[Char] = {
    cursor += 1
    if cursor < src.length then Some(src.charAt(cursor)) else None
  }

  /**
   * Scans and returns a single token
   *
   * @param current The character in the string we are currently on
   * @return
   */
  private def getToken(current: Char): Token =
    current match
      case '(' => Token(LEFT_PAREN, "(")
      case ')' => Token(RIGHT_PAREN, ")")
      case '{' => Token(LEFT_BRACE, "{")
      case '}' => Token(RIGHT_BRACE, "}")
      case ',' => Token(COMMA, ",")
      case '.' => Token(DOT, ".")
      case ';' => Token(SEMICOLON, ";")
      case '+' => Token(PLUS, "+")
      case '-' => Token(MINUS, "-")
      case '*' => Token(STAR, "*")
      case ' ' | '\r' | '\t' => nextToken()
      case '\n' => line += 1; nextToken()
      case _ => Token(INVALID, current.toString, line)
}