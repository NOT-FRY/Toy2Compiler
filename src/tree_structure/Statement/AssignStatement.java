package tree_structure.Statement;

import scoping.SymbolTable;
import tree_structure.Expression.Expression;
import tree_structure.Expression.Identifier;
import tree_structure.Node;
import tree_structure.Type;
import visitors.Visitor;

import java.util.ArrayList;
import java.util.Objects;

public class AssignStatement extends Node implements Statement {
    private ArrayList<Identifier> identifiers;

    private ArrayList<Expression> expressions;

    private Type type;

    private SymbolTable symbolTable;

    public AssignStatement(ArrayList<Identifier> identifiers, ArrayList<Expression> expressions) {
        this.identifiers = identifiers;
        this.expressions = expressions;
    }

    public void addExpression(Expression e){
        expressions.add(e);
    }

    @Override
    public String toString() {
        return "AssignStatement{" +
                "identifiers=" + identifiers +
                ", expressions=" + expressions +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AssignStatement that)) return false;
        return Objects.equals(getIdentifiers(), that.getIdentifiers()) && Objects.equals(getExpressions(), that.getExpressions());
    }

    public ArrayList<Identifier> getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(ArrayList<Identifier> identifiers) {
        this.identifiers = identifiers;
    }

    public ArrayList<Expression> getExpressions() {
        return expressions;
    }

    public void setExpressions(ArrayList<Expression> expressions) {
        this.expressions = expressions;
    }

    public Object accept(Visitor v){
        return v.visit(this);
    }

    public Type getType() {
        return type;
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

