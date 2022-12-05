# Grammar

```
expression  → equality
equality    → comparison ( ( "!=" | "==" ) comparison )* ;
comparison  → term ( ( ">" | ">=" | "<" | "<=" ) term )* ;
term        → factor ( ( "-" | "+" ) factor )* ;
factor      → unary ( ( "/" | "*" ) unary )*
unary       → ( "!" | "-" ) unary | primary;
primary     → NUMBER | STRING | "true" | "false" | "nil"
              | grouping;
grouping    → "(" expression ")" ;
```

# Errors

```
error                       → unexpectedTokenError | missingExpectedTokenError | syntaxError
missingExpectedTokenError   → ( "(" expression expression) | ( "(" expression ";")
unexpectedTokenError        → ???
syntaxError                 → ???
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
