package tree_structure;

import visitors.Visitor;

import java.util.ArrayList;
import java.util.Objects;

public class IterOp extends Node{

    private ArrayList<VarDeclInterface> varDeclList;
    private ArrayList<FunctionOrProcedure> funProcList;

    public IterOp() {
        varDeclList = new ArrayList<VarDeclInterface>();
        funProcList = new ArrayList<FunctionOrProcedure>();
    }

    public IterOp(ArrayList<VarDeclInterface> varDeclList, ArrayList<FunctionOrProcedure> funProcList) {
        this.varDeclList = varDeclList;
        this.funProcList = funProcList;
    }

    public void addVarDecl(VarDeclInterface v){
        varDeclList.add(0,v);
    }

    public void addFunctionOrProcedure(FunctionOrProcedure f){
        funProcList.add(0,f);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IterOp iterOp = (IterOp) o;
        return Objects.equals(varDeclList, iterOp.varDeclList) && Objects.equals(funProcList, iterOp.funProcList);
    }

    @Override
    public String toString() {
        return "IterOp{" +
                "varDeclList=" + varDeclList +
                ", funProcList=" + funProcList +
                '}';
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
