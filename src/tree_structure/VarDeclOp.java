package tree_structure;

import tree_structure.Expression.Expression;
import visitors.Visitor;

import java.util.ArrayList;
import java.util.Objects;

public class VarDeclOp extends Node {


    private ArrayList<IdentifierExpression> identifierExpressionsList;
    private Type type;

    private DeclarationType declarationType;



    public VarDeclOp(ArrayList<IdentifierExpression> identifierExpressionsList, Type type,DeclarationType declarationType) {
        this.identifierExpressionsList = identifierExpressionsList;
        this.type = type;
        this.declarationType=declarationType;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VarDeclOp varDeclOp = (VarDeclOp) o;
        return Objects.equals(identifierExpressionsList, varDeclOp.identifierExpressionsList) && type == varDeclOp.type && declarationType == varDeclOp.declarationType;
    }

    @Override
    public String toString() {
        return "VarDeclOp{" +
                "identifierExpressionsList=" + identifierExpressionsList +
                ", type=" + type +
                ", declarationType=" + declarationType +
                '}';
    }

    public ArrayList<IdentifierExpression> getIdentifierExpressionsList() {
        return identifierExpressionsList;
    }

    public void setIdentifierExpressionsList(ArrayList<IdentifierExpression> identifierExpressionsList) {
        this.identifierExpressionsList = identifierExpressionsList;
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

    public DeclarationType getDeclarationType() {
        return declarationType;
    }

    public void setDeclarationType(DeclarationType declarationType) {
        this.declarationType = declarationType;
    }
}
