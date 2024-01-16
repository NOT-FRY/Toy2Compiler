package tree_structure.Expression;


import scoping.ExpressionType;
import scoping.SymbolTable;
import tree_structure.Node;
import visitors.Visitor;

import java.util.Objects;
import tree_structure.Type;

public class AddOp extends Node implements Expression {
    private Expression left;
    private Expression right;

    private Type type;

    private ExpressionType expressionType = ExpressionType.PLUS;

    private SymbolTable symbolTable;


    public AddOp(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "AddOp{" +
                "left=" + left +
                ", right=" + right +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AddOp addOp)) return false;
        return Objects.equals(getLeft(), addOp.getLeft()) && Objects.equals(getRight(), addOp.getRight());
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }

    public void setLeft(Expression left) {
        this.left = left;
    }

    public void setRight(Expression right) {
        this.right = right;
    }

    @Override
    public Object accept(Visitor v){
        return v.visit(this);
    }

    public Type getType() {
        return type;
    }

    @Override
    public ExpressionType getExpressionType() {
        return expressionType;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setExpressionType(ExpressionType expressionType) {
        this.expressionType = expressionType;
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }
}