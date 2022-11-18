package puggle

import TokenType.*
import Console.{GREEN, RED, RESET}

object Error:
   def reportInvalidTokens(tokens: List[Token]): Unit =
      println("Scanner Errors:")
      val reportToken = (token: Token) =>
         println(s"${RESET}${RED}Invalid character '${token.toString}' on line: ${token.line} $RESET")
      tokens.filter(_.ttype == INVALID).foreach(reportToken)
