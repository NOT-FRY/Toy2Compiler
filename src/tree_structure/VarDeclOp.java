package tree_structure;

import tree_structure.Expression.Expression;
import visitors.Visitor;

import java.util.ArrayList;
import java.util.Objects;

public class VarDeclOp extends Node implements VarDeclInterface {


    private ArrayList<Expression> expressionList;
    private Type type;

    public VarDeclOp(ArrayList<Expression> expressionList, Type type) {
        this.expressionList = expressionList;
        this.type = type;
    }

    @Override
    public String toString() {
        return "VarDeclOp{" +
                "identifierExpressionList=" + expressionList +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VarDeclOp varDeclOp)) return false;
        return Objects.equals(getExpressionList(), varDeclOp.getExpressionList()) && getType() == varDeclOp.getType();
    }


    public ArrayList<Expression> getExpressionList() {
        return expressionList;
    }

    public void setExpressionList(ArrayList<Expression> expressionList) {
        this.expressionList = expressionList;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Object accept(Visitor v){
        return v.visit(this);
    }
}
