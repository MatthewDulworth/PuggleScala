# Puggle Grammer v2

```
expression  →  equality
equality    → comparison ( ( "!=" | "==" ) comparison )* ;
comparison  → term ( ( ">" | ">=" | "<" | "<=" ) term )* ;
term        → factor ( ( "-" | "+" ) factor )* ;
factor      → unary ( ( "/" | "*" ) unary )*
unary       → ( "!" | "-" ) unary | primary;
primary     → NUMBER | STRING | "true" | "false" | "nil"
              | "(" expression ")" ;
```


# Puggle Grammer v1

```
expression  -> literal | unary | binary | grouping ;
literal     -> NUMBER | STRING | "true" | "false" | "nil" ;
grouping    -> "(" expression ")" ;
unary       -> ( "-" | "1" ) expression ;
binary      -> expression operator expression ;
operator    -> "==" | "!=" | "<" | "<=" | ">" | ">="
             | "+"  | "-"  | "*" | "/" ;
```
