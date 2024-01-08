package esercitazione4;
import java_cup.runtime.Symbol;

%%

%unicode
%line
%column
%cup

%{
        private Symbol symbol(int type) {
            return new Symbol(type, yyline, yycolumn);
        }
        private Symbol symbol(int type, Object value) {
            return new Symbol(type, yyline, yycolumn, value);
        }
%}

/*Whitespaces*/
LineTerminator = \r|\n|\r\n
WhiteSpace = {LineTerminator} | [ \t\f]

/*Commenti*/
Commento = \%[^\%]*\%

/*String literals*/
StringLiteral = \"[^\"]*\"


Identificatore = [A-Za-z] ( [A-Za-z_] | [0-9] )*

IntegerNumber = 0 | [1-9][0-9]*
RealNumber = {IntegerNumber}"."[0-9]+

%%

/*Commenti*/
<YYINITIAL> {Commento}      { /*ignora*/ }

/*Whitespaces*/
<YYINITIAL> {WhiteSpace}      { /*ignora*/ }

//KEYWORDS e delimitatori
<YYINITIAL> "var"              { return symbol(sym.VAR); }
<YYINITIAL> "true"              { return symbol(sym.TRUE); }
<YYINITIAL> "false"              { return symbol(sym.FALSE); }
<YYINITIAL> "real"              { return symbol(sym.REAL); }
<YYINITIAL> "integer"              { return symbol(sym.INTEGER); }
<YYINITIAL> "string"              { return symbol(sym.STRING); }
<YYINITIAL> "boolean"              { return symbol(sym.BOOLEAN); }
<YYINITIAL> "return"              { return symbol(sym.RETURN); }
<YYINITIAL> "func"              { return symbol(sym.FUNCTION); }
<YYINITIAL> "->"              { return symbol(sym.TYPERETURN); }
<YYINITIAL> "endfunc"              { return symbol(sym.ENDFUNCTION); }
<YYINITIAL> "("              { return symbol(sym.LPAR); }
<YYINITIAL> ")"              { return symbol(sym.RPAR); }
<YYINITIAL> "proc"              { return symbol(sym.PROCEDURE); }
<YYINITIAL> "endproc"              { return symbol(sym.ENDPROCEDURE); }
<YYINITIAL> "out"              { return symbol(sym.OUT); }
<YYINITIAL> "-->"              { return symbol(sym.WRITE); }
<YYINITIAL> "-->!"              { return symbol(sym.WRITERETURN); }
<YYINITIAL> "$"              { return symbol(sym.DOLLARSIGN); }
<YYINITIAL> "<--"              { return symbol(sym.READ); }
<YYINITIAL> "if"              { return symbol(sym.IF); }
<YYINITIAL> "then"              { return symbol(sym.THEN); }
<YYINITIAL> "else"              { return symbol(sym.ELSE); }
<YYINITIAL> "endif"              { return symbol(sym.ENDIF); }
<YYINITIAL> "elseif"              { return symbol(sym.ELIF); }
<YYINITIAL> "while"              { return symbol(sym.WHILE); }
<YYINITIAL> "endwhile"              { return symbol(sym.ENDWHILE); }
<YYINITIAL> "do"              { return symbol(sym.DO); }
<YYINITIAL> ":"       { return symbol(sym.COLON); }
<YYINITIAL> ";"       { return symbol(sym.SEMI); }
<YYINITIAL> ","       { return symbol(sym.COMMA); }
<YYINITIAL> "^="       { return symbol(sym.ASSIGN); }
<YYINITIAL> "\\"       { return symbol(sym.ENDVAR); }


/*Operatori*/
<YYINITIAL> "+"              { return symbol(sym.PLUS); }
<YYINITIAL> "-"              { return symbol(sym.MINUS); }
<YYINITIAL> "*"              { return symbol(sym.TIMES); }
<YYINITIAL> "/"              { return symbol(sym.DIV); }
<YYINITIAL> "="              { return symbol(sym.EQ); }
<YYINITIAL> "<>"              { return symbol(sym.NE); }
<YYINITIAL> "<"              { return symbol(sym.LT); }
<YYINITIAL> "<="              { return symbol(sym.LE); }
<YYINITIAL> ">"              { return symbol(sym.GT); }
<YYINITIAL> ">="              { return symbol(sym.GE); }
<YYINITIAL> "&&"              { return symbol(sym.AND); }
<YYINITIAL> "||"              { return symbol(sym.OR); }
<YYINITIAL> "!"              { return symbol(sym.NOT); }

<YYINITIAL> "@"              { return symbol(sym.REF); }

<YYINITIAL>{
    {RealNumber}               { return symbol(sym.REAL_CONST, Double.parseDouble(yytext())); }
    {IntegerNumber}            { return symbol(sym.INTEGER_CONST, Integer.parseInt(yytext())); }
    {StringLiteral}            {
                                    String str =  yytext().substring(1,yylength()-1);
                                    return symbol(sym.STRING_CONST,str);
                                }
}
<YYINITIAL>{
    {Identificatore}            { return symbol(sym.ID,yytext()); }
}

//EOF
<YYINITIAL>{
    <<EOF>>   { return symbol(sym.EOF); }
}

/* error fallback */

[^]                {throw new Error("- Carattere non consentito < "+
                    yytext()+" > a riga "+(yyline+1)+"\n" );}


