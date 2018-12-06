grammar ILPMLgrammar4tme8;

import ILPMLgrammar4;

// Expressions enrichies
expr returns [com.paracamplus.ilp1.interfaces.IASTexpression node]
// expressions de la grammaire précédente
    : '(' exprs+=expr (';'? exprs+=expr)* ';'? ')' # Sequence
    | 'self'    # Self
    | 'super' # Super
    | obj=expr '.' field=IDENT '=' val=expr # WriteField
    | obj=expr '.' field=IDENT  '(' args+=expr? (',' args+=expr)* ')' # Send
    | obj=expr '.' field=IDENT # ReadField
    | 'new' className=IDENT  '(' args+=expr? (',' args+=expr)* ')' # New
    | fun=expr '(' args+=expr? (',' args+=expr)* ')' # Invocation
    | op=('-' | '!') arg=expr # Unary
    | arg1=expr op=('*' | '/' | '%') arg2=expr # Binary
    | arg1=expr op=('+' | '-') arg2=expr # Binary
    | arg1=expr op=('<' | '<=' | '>' | '>=') arg2=expr # Binary
    | arg1=expr op=('==' | '!=') arg2=expr # Binary
    | arg1=expr op='&' arg2=expr # Binary
    | arg1=expr op=('|' | '^') arg2=expr # Binary
    | 'true' # ConstTrue
    | 'false' # ConstFalse
    | intConst=INT # ConstInteger
    | floatConst=FLOAT # ConstFloat
    | stringConst=STRING # ConstString
    | var=IDENT # Variable
    | 'let' vars+=IDENT '=' vals+=expr ('and' vars+=IDENT '=' vals+=expr)*
      'in' body=expr # Binding
    | 'if' condition=expr 'then' consequence=expr
        ('else' alternant=expr)? # Alternative
    | var=IDENT '=' val=expr # VariableAssign
    | 'while' condition=expr 'do' body=expr # Loop
    | 'try' body=expr
      ('catch' '(' var=IDENT  ')' catcher=expr)?
      ('finally' finallyer=expr)? # Try
    | 'lambda' '(' vars+=IDENT? (',' vars+=IDENT)* ')'
      body=expr # Lambda
    | defs+=localFunDef ('and' defs+=localFunDef)* 'in' body=expr # Codefinitions

        // Mes ajouts
    | obj=expr '[' fie=expr ']' # ReadExprField
    | obj=expr '[' fie=expr ']' '=' val=expr # WriteExprField
    | obj=expr 'has' fie=expr # HasField
    ;
