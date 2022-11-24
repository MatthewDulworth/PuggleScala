package puggle

import org.scalatest.funsuite.AnyFunSuite

class TokenListTest extends AnyFunSuite {

  test("TokenList") {
    val l = IF :: OPEN_PAREN :: TRUE :: CLOSE_PAREN :: Nil
    val tokens = TokenList(l)
    var t: TOKEN = EOF
    val x = Iterator.continually {
      t = tokens.next()
      t
    }.takeWhile(_ => t != EOF).toList
    assertResult(l)(x)
  }

  test("Empty TokenList") {
    val l = Nil
    val tokens = TokenList(l)
    var t: TOKEN = EOF
    val x = Iterator.continually {
      t = tokens.next()
      t
    }.takeWhile(_ => t != EOF).toList
    assertResult(l)(x)
  }

  test("Peek") {
    val l = IF :: OPEN_PAREN :: TRUE :: CLOSE_PAREN :: Nil
    val tokens = TokenList(l)
    var t: TOKEN = EOF
    val x = Iterator.continually {
      tokens.next()
      t = tokens.peek
      t
    }.takeWhile(_ => t != EOF).toList
    assertResult(l)(x)
  }

  test("PeekNext") {
    val l = IF :: OPEN_PAREN :: TRUE :: CLOSE_PAREN :: Nil
    val tokens = TokenList(l)
    var t: TOKEN = EOF
    val x = Iterator.continually {
      t = tokens.peekNext
      tokens.next()
      t
    }.takeWhile(_ => t != EOF).toList
    assertResult(l)(x)
  }

  test("Matches") {
    val tokens = TokenList(IF :: OPEN_PAREN :: TRUE :: CLOSE_PAREN :: Nil)
    var t: TOKEN = EOF
    val x = Iterator.continually {
      t = tokens.next()
      tokens.matches(DOT, IF, FALSE, CLOSE_PAREN)
    }.takeWhile(_ => t != EOF).toList
    assertResult(true :: false :: false :: true :: Nil)(x)
  }

  test("Matches Empty Tokens") {
    val tokens = TokenList(Nil)
    var t: TOKEN = EOF
    val x = Iterator.continually {
      t = tokens.next()
      tokens.matches(DOT, IF, FALSE, CLOSE_PAREN)
    }.takeWhile(_ => t != EOF).toList
    assertResult(Nil)(x)
  }

  test("Matches Empty Matchables") {
    val tokens = TokenList(IF :: OPEN_PAREN :: TRUE :: CLOSE_PAREN :: Nil)
    var t: TOKEN = EOF
    val x = Iterator.continually {
      t = tokens.next()
      tokens.matches()
    }.takeWhile(_ => t != EOF).toList
    assertResult(false :: false :: false :: false :: Nil)(x)
  }
}
