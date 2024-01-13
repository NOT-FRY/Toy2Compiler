package tree_structure;

import tree_structure.Statement.Statement;
import visitors.Visitor;

import java.util.ArrayList;
import java.util.Objects;

public class BodyOp extends Node{
    private ArrayList<VarDeclOp> varDeclList;
    private ArrayList<Statement> statementList;

    private Type type;

    public BodyOp() {
        varDeclList = new ArrayList<VarDeclOp>();
        statementList = new ArrayList<Statement>();
    }

    public BodyOp(ArrayList<VarDeclOp> varDeclList, ArrayList<Statement> statementList) {
        this.varDeclList = varDeclList;
        this.statementList = statementList;
    }

    public void addStatement(Statement s){
        statementList.add(0,s);
    }

    public void addVarDecl(VarDeclOp v){
        varDeclList.add(0,v);
    }

    @Override
    public String toString() {
        return "BodyOp{" +
                "varDeclList=" + varDeclList +
                ", statementList=" + statementList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BodyOp bodyOp = (BodyOp) o;
        return Objects.equals(varDeclList, bodyOp.varDeclList) && Objects.equals(statementList, bodyOp.statementList);
    }

    public ArrayList<VarDeclOp> getVarDeclList() {
        return varDeclList;
    }

    public void setVarDeclList(ArrayList<VarDeclOp> varDeclList) {
        this.varDeclList = varDeclList;
    }

    public ArrayList<Statement> getStatementList() {
        return statementList;
    }

    public void setStatementList(ArrayList<Statement> statementList) {
        this.statementList = statementList;
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
}
