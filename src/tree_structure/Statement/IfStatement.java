package tree_structure.Statement;

import tree_structure.*;
import tree_structure.Expression.Expression;
import visitors.Visitor;

import java.util.ArrayList;
import java.util.Objects;

public class IfStatement extends Node implements Statement {
    private Expression expression;
    private BodyOp body;
    private ArrayList<ElifOp> elifList;
    private ElseOp elseBody;

    public IfStatement(Expression expression, BodyOp body, ArrayList<ElifOp> elifList, ElseOp elseBody) {
        this.expression = expression;
        this.body = body;
        this.elifList = elifList;
        this.elseBody = elseBody;
    }

    @Override
    public String toString() {
        return "IfStatement{" +
                "expression=" + expression +
                ", body=" + body +
                ", elifList=" + elifList +
                ", elseBody=" + elseBody +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IfStatement that)) return false;
        return Objects.equals(getExpression(), that.getExpression()) && Objects.equals(getBody(), that.getBody()) && Objects.equals(getElifList(), that.getElifList()) && Objects.equals(getElseBody(), that.getElseBody());
    }


    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public BodyOp getBody() {
        return body;
    }

    public void setBody(BodyOp body) {
        this.body = body;
    }

    public ArrayList<ElifOp> getElifList() {
        return elifList;
    }

    public void setElifList(ArrayList<ElifOp> elifList) {
        this.elifList = elifList;
    }

    public ElseOp getElseBody() {
        return elseBody;
    }

    public void setElseBody(ElseOp elseBody) {
        this.elseBody = elseBody;
    }

    public Object accept(Visitor v){
        return v.visit(this);
    }
}
