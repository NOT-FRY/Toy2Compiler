package scoping;


import tree_structure.Expression.Expression;

public class TypeCheck {

    // Metodo per controllare il tipo di espressioni binarie
    public static Type checkBinaryExprType(Expression expr1, Expression expr2, Operator op) {
        Type type1 = expr1.getType();
        Type type2 = expr2.getType();

        switch (op) {
            case PLUS:
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

    // Metodo per controllare il tipo di espressioni unarie
    public static Type checkUnaryExprType(Expression expr, Operator op) {
        Type type = expr.getType();

        switch (op) {
            case NOT:
                return type == Type.BOOLEAN ? Type.BOOLEAN : Type.ERROR;

            case MINUS:
                return (type == Type.INTEGER || type == Type.REAL) ? type : Type.ERROR;

            default:
                return Type.ERROR; // Tipo non valido o non supportato
        }
    }

    // Metodo per controllare le operazioni aritmetiche
    private static Type checkArithmeticExpr(Type type1, Type type2) {
        if (type1 == Type.INTEGER && type2 == Type.INTEGER)
            return Type.INTEGER;
        if ((type1 == Type.REAL && type2 == Type.REAL) ||
                (type1 == Type.REAL && type2 == Type.INTEGER))
            return Type.REAL;
        return Type.ERROR;
    }

    // Metodo per controllare le operazioni logiche
    private static Type checkLogicalExpr(Type type1, Type type2) {
        return (type1 == Type.BOOLEAN && type2 == Type.BOOLEAN) ? Type.BOOLEAN : Type.ERROR;
    }

    // Metodo per controllare le operazioni di confronto
    private static Type checkRelationalExpr(Type type1, Type type2) {
        if (type1 == type2 || (isNumeric(type1) && isNumeric(type2)))
            return Type.BOOLEAN;
        return Type.ERROR;
    }

    // Metodo helper per verificare se un tipo è numerico
    private static boolean isNumeric(Type type) {
        return type == Type.INTEGER || type == Type.REAL;
    }

}
