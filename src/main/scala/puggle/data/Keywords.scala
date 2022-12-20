package puggle.data

import puggle.data.Tokens.*

val keywords = new BiMap(
  "true" -> TRUE,
  "false" -> FALSE,
  "and" -> AND,
  "or" -> OR,
  "if" -> IF,
  "else" -> ELSE,
  "while" -> WHILE,
  "func" -> FUNC,
  "val" -> VAL,
  "var" -> VAR,
  "print" -> PRINT,
)
