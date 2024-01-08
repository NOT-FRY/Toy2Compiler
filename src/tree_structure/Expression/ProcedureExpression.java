package tree_structure.Expression;
import tree_structure.Identifier;
import tree_structure.Node;
import visitors.Visitor;

import java.util.Objects;

public class ProcedureExpression extends Node{
    Expression expression;

    boolean reference;

    Identifier identifier;


    public ProcedureExpression(Expression expression, boolean reference, Identifier identifier) {
        this.expression = expression;
        this.reference = reference;
        this.identifier = identifier;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public boolean isReference() {
        return reference;
    }

    public void setReference(boolean reference) {
        this.reference = reference;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcedureExpression that = (ProcedureExpression) o;
        return reference == that.reference && Objects.equals(expression, that.expression) && Objects.equals(identifier, that.identifier);
    }

    @Override
    public String toString() {
        return "ProcedureExpression{" +
                "expression=" + expression +
                ", reference=" + reference +
                ", identifier=" + identifier +
                '}';
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
