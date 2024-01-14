package scoping;


import tree_structure.ElifOp;
import tree_structure.Expression.Expression;
import tree_structure.Expression.FunCallOp;
import tree_structure.Expression.Identifier;
import tree_structure.Statement.AssignStatement;
import tree_structure.Statement.IOArg;
import tree_structure.Type;

import java.util.ArrayList;

// TYPE SYSTEM di toy2

public class TypeCheck {

    // Metodo per controllare il tipo di espressioni binarie
    public static Type checkBinaryExprType(Expression expr1, Expression expr2, ExpressionType op) {
        Type type1 = expr1.getType();
        Type type2 = expr2.getType();

        switch (op) {
            case PLUS:

                if(type1==Type.STRING || type2==Type.STRING)
                    return checkStringConcatenation(type1,type2);
                else
                    return checkArithmeticExpr(type1,type2);

            case MINUS:
            case TIMES:
            case DIV:
                return checkArithmeticExpr(type1, type2);

            case AND:
            case OR:
                return checkLogicalExpr(type1, type2);

            case EQ:
            case NE:
            case LT:
            case LE:
            case GT:
            case GE:
                return checkRelationalExpr(type1, type2);

            default:
                return Type.ERROR; // Tipo non valido o non supportato
        }
    }

    //caso concatenazione stringhe
    public static Type checkStringConcatenation(Type type1, Type type2) {
        if (type1 == Type.STRING && type2 == Type.STRING)
            return Type.STRING;
        else
            return Type.ERROR;
    }

    // Metodo per controllare il tipo di espressioni unarie
    public static Type checkUnaryExprType(Expression expr, ExpressionType op) {
        Type type = expr.getType();

        switch (op) {
            case NOT:
                return type == Type.BOOL ? Type.BOOL : Type.ERROR;

            case UMINUS:
                return (type == Type.INTEGER || type == Type.REAL) ? type : Type.ERROR;

            default:
                return Type.ERROR; // Tipo non valido o non supportato
        }
    }

    // Metodo per controllare le operazioni aritmetiche
    public static Type checkArithmeticExpr(Type type1, Type type2) {
        if (type1 == Type.INTEGER && type2 == Type.INTEGER)
            return Type.INTEGER;
        if ((type1 == Type.REAL && type2 == Type.REAL) ||
                (type1 == Type.REAL && type2 == Type.INTEGER) ||
                (type1 == Type.INTEGER && type2 == Type.REAL)
            )
            return Type.REAL;
        return Type.ERROR;
    }

    // Metodo per controllare le operazioni logiche
    public static Type checkLogicalExpr(Type type1, Type type2) {
        return (type1 == Type.BOOL && type2 == Type.BOOL) ? Type.BOOL : Type.ERROR;
    }

    // Metodo per controllare le operazioni di confronto
    public static Type checkRelationalExpr(Type type1, Type type2) {
        if (type1 == type2 ||
                (type1 == Type.REAL && type2 == Type.INTEGER) ||
                (type1 == Type.INTEGER && type2 == Type.REAL)
                )
            return Type.BOOL;

        return Type.ERROR;
    }

    public static Type checkWhileStatement(Type expressionType, Type bodyType){
        if(expressionType==Type.BOOL && bodyType==Type.NOTYPE)
            return Type.NOTYPE;
        else
            return Type.ERROR;
    }

    public static Type checkAssignStatement(AssignStatement assignStatement)throws Exception{
        int identifierCount = 0;
        int expressionCount = 0;

        //il numero n degli identificatori nel primo figlio deve essere uguale al numero dei valori restituiti dalle
        //espressioni nel secondo figlio
        for(Identifier i : assignStatement.getIdentifiers()) {
            identifierCount++;
        }
        for(Expression e : assignStatement.getExpressions()){
            if(e instanceof FunCallOp){ //ExpressionType.FUNCALL
                for(Type t :((FunCallOp) e).getReturnTypes()){
                    expressionCount++;
                }
            }else {
                expressionCount++;
            }
        }
        if(identifierCount != expressionCount) {
            throw new Exception(">Semantic error: numero di identificatori diverso dal numero di espressioni nell'assegnazione -starting at:" + assignStatement.getIdentifiers().get(0).getName());
        }

        ArrayList<Identifier> identifier = assignStatement.getIdentifiers();

        // il tipo dell’i-esimo identificatore è compatibile con il tipo dell’iesimo valore per i = 1, .., n
        for(int i=0; i< assignStatement.getIdentifiers().size();i++){

            Identifier id = assignStatement.getIdentifiers().get(i);
            Expression ex = assignStatement.getExpressions().get(i);

            //se l'espressione destra dell'assegnamento è unaria
            if(ex.getExpressionType()==ExpressionType.NOT || ex.getExpressionType()==ExpressionType.UMINUS){
                Type idType = checkUnaryExprType(id,ex.getExpressionType());
                Type expressionType = checkUnaryExprType(ex,ex.getExpressionType());

                if(idType!=expressionType){
                    throw new Exception(">Semantic error: Tipo dell'identificatore non compatibile con il lato destro dell'assegnazione -starting at:" + id.getName());
                }
            }else if(ex.getExpressionType() == ExpressionType.FUNCALL) {
                //gli id dovrebbero essere ordinati...
                FunCallOp funCall = (FunCallOp) ex;
                for(Type t: funCall.getReturnTypes()){
                    if(id.getType() != t){
                        throw new Exception(">Semantic error: Tipo dell'identificatore non compatibile con il lato destro dell'assegnazione -starting at:" + id.getName()+" mismatch con la funzione: "+funCall.getIdentifier().getName());
                    }
                    i++;
                    id = assignStatement.getIdentifiers().get(i);//L'overflow dovrebbe essere stato controllato prima (numero identificatori)
                }

            }else{//tipo binario
                Type type = checkBinaryExprType(id,ex,ex.getExpressionType());
                if(type==Type.ERROR){
                    throw new Exception(">Semantic error: Tipo dell'identificatore non compatibile con il lato destro dell'assegnazione -starting at:" + id.getName());
                }
            }

        }

        return Type.NOTYPE;

    }

    public static Type checkIfStatement(Type expressionType, Type bodyType){
        if(expressionType==Type.BOOL && bodyType==Type.NOTYPE)
            return Type.NOTYPE;
        else
            return Type.ERROR;
    }

    public static Type checkIfElseStatement(Type expressionType, Type bodyType, Type elseBodyType){
        if(expressionType==Type.BOOL && bodyType==Type.NOTYPE && elseBodyType==Type.NOTYPE)
            return Type.NOTYPE;
        else
            return Type.ERROR;
    }

    public static Type checkIfElsifStatement(Type expressionType, Type bodyType, ArrayList<ElifOp> elifList, Type elseBodyType){

        for(ElifOp elif : elifList){
            Type t = checkIfStatement(elif.getExpression().getType(), elif.getBody().getType());
            if(t==Type.ERROR)
                return Type.ERROR;
        }

        if(elseBodyType!=null){
            Type t = checkIfElseStatement(expressionType,bodyType,elseBodyType);
            if(t==Type.ERROR)
                return Type.ERROR;
        }else{
            Type t = checkIfStatement(expressionType,bodyType);
            if(t==Type.ERROR)
                return Type.ERROR;
        }

        return Type.NOTYPE;

    }

}