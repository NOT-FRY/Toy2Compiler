package scoping;

public enum ExpressionType {
    PLUS,       // Operatore +
    MINUS,      // Operatore - (binario)
    TIMES,      // Operatore *
    DIV,        // Operatore /
    AND,        // Operatore &&
    OR,         // Operatore ||
    NOT,        // Operatore !
    EQ,         // Operatore ==
    NE,         // Operatore <>
    LT,         // Operatore <
    LE,         // Operatore <=
    GT,         // Operatore >
    GE,         // Operatore >=
    ASSIGN,     // Operatore di assegnazione ^=
    UMINUS,      //Operatore - (unario)
    FUNCALL,     //Chiamata a funzione
    CONST,       //costanti di tipo REAL, INTEGER, BOOL, STRING
    ID          //Identificatore
}