package puggle

class Parser(private val list: List[TOKEN]) {
  private val tokens = TokenList(list)
  
  
  /**
   * @return
   */
  private def expression(): Expr = equilty()

  /**
   * @return
   */
  private def equilty(): Expr = ???

  private def comparison(): Expr = ???


}
