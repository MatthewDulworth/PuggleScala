package puggle

import tools.BiMap

val keywords = new BiMap(
  "and" -> AND,
  "or" -> OR,
  "if" -> IF,
  "else" -> ELSE,
  "for" -> FOR,
  "while" -> WHILE,
  "class" -> CLASS,
  "func" -> FUNC,
  "this" -> THIS,
  "true" -> TRUE,
  "false" -> FALSE,
  "nil" -> NULL,
  "val" -> VAL,
  "var" -> VAR,
  "print" -> PRINT,
)
