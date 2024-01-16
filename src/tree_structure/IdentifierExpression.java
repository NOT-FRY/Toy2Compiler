package tree_structure;

import scoping.SymbolTable;
import tree_structure.Expression.Expression;
import tree_structure.Expression.Identifier;
import visitors.Visitor;

import java.util.Objects;

public class IdentifierExpression extends Node{
    private Identifier identifier;
    private Expression exp;

    private SymbolTable symbolTable;

    public IdentifierExpression(Identifier identifier, Expression exp) {
        this.identifier = identifier;
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "IdentifierExpression{" +
                "identifier=" + identifier +
                ", exp=" + exp +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IdentifierExpression that)) return false;
        return Objects.equals(getIdentifier(), that.getIdentifier()) && Objects.equals(getExpression(), that.getExpression());
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    public Expression getExpression() {
        return exp;
    }

    public void setExpression(Expression exp) {
        this.exp = exp;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }
}
