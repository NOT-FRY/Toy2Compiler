package tree_structure;

import scoping.SymbolTable;
import tree_structure.Expression.Identifier;
import visitors.Visitor;

import java.util.ArrayList;
import java.util.Objects;

public class ProcedureOp extends Node implements FunctionOrProcedure{

    private Identifier identifier;
    private ArrayList<ProcFunParamOp> procFunParamOpList;

    private BodyOp body;

    private SymbolTable symbolTable;

    public ProcedureOp(Identifier identifier, ArrayList<ProcFunParamOp> procFunParamOpList, BodyOp body) {
        this.identifier = identifier;
        this.procFunParamOpList = procFunParamOpList;
        this.body = body;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    public ArrayList<ProcFunParamOp> getProcFunParamOpList() {
        return procFunParamOpList;
    }

    public void setProcFunParamOpList(ArrayList<ProcFunParamOp> procFunParamOpList) {
        this.procFunParamOpList = procFunParamOpList;
    }

    public BodyOp getBody() {
        return body;
    }

    public void setBody(BodyOp body) {
        this.body = body;
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcedureOp procedureOp = (ProcedureOp) o;
        return Objects.equals(identifier, procedureOp.identifier) && Objects.equals(procFunParamOpList, procedureOp.procFunParamOpList) && Objects.equals(body, procedureOp.body);
    }

    @Override
    public String toString() {
        return "ProcOp{" +
                "identifier=" + identifier +
                ", procFunParamOpList=" + procFunParamOpList +
                ", body=" + body +
                '}';
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }
}
