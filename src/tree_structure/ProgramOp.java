package tree_structure;

import scoping.SymbolTable;
import visitors.Visitor;

import java.util.ArrayList;
import java.util.Objects;

public class ProgramOp extends Node{
    private ArrayList<VarDeclOp> varDeclList;
    private ArrayList<FunctionOrProcedure> funProcList;

    private SymbolTable symbolTable;

    public ProgramOp() {
        varDeclList = new ArrayList<VarDeclOp>();
        funProcList = new ArrayList<FunctionOrProcedure>();
    }

    public ProgramOp(ArrayList<VarDeclOp> varDeclList, ArrayList<FunctionOrProcedure> funProcList) {
        this.varDeclList = varDeclList;
        this.funProcList = funProcList;
    }

    public void addVarDecl(VarDeclOp v){
        varDeclList.add(0,v);
    }

    public void addFunctionOrProcedure(FunctionOrProcedure f){
        funProcList.add(0,f);
    }

    @Override
    public String toString() {
        return "ProgramOp{" +
                "varDeclList=" + varDeclList +
                ", funProcList=" + funProcList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProgramOp programOp)) return false;
        return Objects.equals(getVarDeclList(), programOp.getVarDeclList()) && Objects.equals(getFunProcList(), programOp.getFunProcList());
    }


    public Object accept(Visitor v){
        return v.visit(this);
    }

    public ArrayList<VarDeclOp> getVarDeclList() {
        return varDeclList;
    }

    public void setVarDeclList(ArrayList<VarDeclOp> varDeclList) {
        this.varDeclList = varDeclList;
    }

    public ArrayList<FunctionOrProcedure> getFunProcList() {
        return funProcList;
    }

    public void setFunProcList(ArrayList<FunctionOrProcedure> funProcList) {
        this.funProcList = funProcList;
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }
}
