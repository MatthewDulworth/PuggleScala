package puggle

val keywords = new BiMap(
  "AND" -> AND,
  "OR" -> OR,
  "IF" -> IF,
  "ELSE" -> ELSE,
  "FOR" -> FOR,
  "WHILE" -> WHILE,
  "CLASS" -> CLASS,
  "FUNC" -> FUNC,
  "THIS" -> THIS,
  "TRUE" -> TRUE,
  "FALSE" -> FALSE,
  "NIL" -> NIL,
  "VAL" -> VAL,
  "VAR" -> VAR,
  "PRINT" -> PRINT,
)

val x = keywords(AND)
