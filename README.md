# Puggle Grammar v2

```
expression  →  equality
equality    → comparison ( ( "!=" | "==" ) comparison )* ;
comparison  → term ( ( ">" | ">=" | "<" | "<=" ) term )* ;
term        → factor ( ( "-" | "+" ) factor )* ;
factor      → unary ( ( "/" | "*" ) unary )*
unary       → ( "!" | "-" ) unary | primary;
primary     → NUMBER | STRING | "true" | "false" | "nil"
              | grouping;
grouping    → "(" expression ")" ;
```


# Puggle Grammar v1

```
expression  -> literal | unary | binary | grouping ;
literal     -> NUMBER | STRING | "true" | "false" | "nil" ;
grouping    -> "(" expression ")" ;
unary       -> ( "-" | "1" ) expression ;
binary      -> expression operator expression ;
operator    -> "==" | "!=" | "<" | "<=" | ">" | ">="
             | "+"  | "-"  | "*" | "/" ;
```

# Usage

```
val x = 3;          // constant 
var y = "hello";    // variable 

if (x != y) {       // if statement
    y = "hi;
};

var i = 0;
while(i < 7) {

};
```
