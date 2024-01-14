package tree_structure.Expression;

import scoping.ExpressionType;
import tree_structure.Node;
import tree_structure.Type;
import visitors.Visitor;

import java.util.ArrayList;
import java.util.Objects;

public class FunCallOp extends Node implements Expression{
    private Identifier identifier;
    private ArrayList<Expression> expressions;

    private ArrayList<Type> returnTypes;

    private ExpressionType expressionType = ExpressionType.FUNCALL;

    public FunCallOp(Identifier identifier, ArrayList<Expression> expressions) {
        this.identifier = identifier;
        this.expressions = expressions;
        returnTypes = new ArrayList<Type>();
    }

    public FunCallOp(Identifier identifier) {
        this.identifier = identifier;
        this.expressions = new ArrayList<Expression>();
        returnTypes = new ArrayList<Type>();
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    public ArrayList<Expression> getExpressions() {
        return expressions;
    }

    public void setExpressions(ArrayList<Expression> expressions) {
        this.expressions = expressions;
    }

    @Override
    public String toString() {
        return "FunCallOp{" +
                "identifier=" + identifier +
                ", expressions=" + expressions +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FunCallOp funCallOp)) return false;
        return Objects.equals(identifier, funCallOp.identifier) && Objects.equals(expressions, funCallOp.expressions);
    }

    public Object accept(Visitor v){
        return v.visit(this);
    }

    public ExpressionType getExpressionType() {
        return expressionType;
    }

    public void setExpressionType(ExpressionType expressionType) {
        this.expressionType = expressionType;
    }

    public ArrayList<Type> getReturnTypes() {
        return returnTypes;
    }

    public void setReturnTypes(ArrayList<Type> returnTypes) {
        this.returnTypes = returnTypes;
    }

    public void addReturnType(Type returnType){
        returnTypes.add(returnType);
    }

    @Override
    public Type getType() {
        return null;
    }
}
