grammar ILPMLgrammar2tme5;

// Import de la grammaire à enrichir
import ILPMLgrammar2;

// Expressions enrichies
expr returns [com.paracamplus.ilp1.interfaces.IASTexpression node]
// expressions de la grammaire précédente
    : '(' exprs+=expr (';'? exprs+=expr)* ';'? ')' # Sequence
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

    // ajouts
    | 'break' name=IDENT # NamedBreak
    | 'continue' name=IDENT # NamedContinue
    | 'break' # Break
    | 'continue' # Continue
    | name=IDENT ':' 'while' condition=expr 'do' body=expr # NamedLoop
    ;

