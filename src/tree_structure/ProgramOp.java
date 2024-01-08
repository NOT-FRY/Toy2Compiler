package tree_structure;

import visitors.Visitor;

import java.util.ArrayList;
import java.util.Objects;

public class ProgramOp extends Node{
    private ArrayList<VarDeclInterface> varDeclList;
    private ArrayList<FunctionOrProcedure> funProcList;

    public ProgramOp() {
        varDeclList = new ArrayList<VarDeclInterface>();
        funProcList = new ArrayList<FunctionOrProcedure>();
    }

    public ProgramOp(ArrayList<VarDeclInterface> varDeclList, ArrayList<FunctionOrProcedure> funProcList) {
        this.varDeclList = varDeclList;
        this.funProcList = funProcList;
    }

    public void addVarDecl(VarDeclInterface v){
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

    public ArrayList<VarDeclInterface> getVarDeclList() {
        return varDeclList;
    }

    public void setVarDeclList(ArrayList<VarDeclInterface> varDeclList) {
        this.varDeclList = varDeclList;
    }

    public ArrayList<FunctionOrProcedure> getFunProcList() {
        return funProcList;
    }

    public void setFunProcList(ArrayList<FunctionOrProcedure> funProcList) {
        this.funProcList = funProcList;
    }
}
