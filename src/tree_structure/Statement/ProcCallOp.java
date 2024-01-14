package tree_structure.Statement;

import tree_structure.ProcedureExpression;
import tree_structure.Expression.Identifier;
import tree_structure.Node;
import tree_structure.Type;
import visitors.Visitor;

import java.util.ArrayList;
import java.util.Objects;

public class ProcCallOp extends Node implements Statement {

    private Identifier identifier;
    private ArrayList<ProcedureExpression> procedureExpressions;

    private Type type;


    public ProcCallOp(Identifier identifier) {
        this.identifier = identifier;
        this.procedureExpressions = new ArrayList<ProcedureExpression>();
    }

    public ProcCallOp(Identifier identifier, ArrayList<ProcedureExpression> procedureExpressions) {
        this.identifier = identifier;
        this.procedureExpressions = procedureExpressions;
    }


    public Identifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    public ArrayList<ProcedureExpression> getProcedureExpressions() {
        return procedureExpressions;
    }

    public void setProcedureExpressions(ArrayList<ProcedureExpression> procedureExpressions) {
        this.procedureExpressions = procedureExpressions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcCallOp that = (ProcCallOp) o;
        return Objects.equals(identifier, that.identifier) && Objects.equals(procedureExpressions, that.procedureExpressions);
    }

    @Override
    public String toString() {
        return "ProcCallOp{" +
                "identifier=" + identifier +
                ", procedureExpressions=" + procedureExpressions +
                '}';
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

}
