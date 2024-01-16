package tree_structure.Statement;

import scoping.ExpressionType;
import scoping.SymbolTable;
import tree_structure.Expression.Expression;
import tree_structure.Node;
import tree_structure.Type;
import visitors.Visitor;

import java.util.Objects;

public class IOArg extends Node implements Expression{

    private Expression expression;

    private boolean dollarSign;

    private Type type;

    private SymbolTable symbolTable;

    public IOArg(Expression expression, boolean dollarSign) {
        this.expression = expression;
        this.dollarSign = dollarSign;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public boolean isDollarSign() {
        return dollarSign;
    }

    public void setDollarSign(boolean dollarSign) {
        this.dollarSign = dollarSign;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IOArg ioArg = (IOArg) o;
        return dollarSign == ioArg.dollarSign && Objects.equals(expression, ioArg.expression);
    }

    @Override
    public String toString() {
        return "IOArg{" +
                "expression=" + expression +
                ", dollarSign=" + dollarSign +
                '}';
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

    public Type getType() {
        return type;
    }

    @Override
    public ExpressionType getExpressionType() {
        return null;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }
}
