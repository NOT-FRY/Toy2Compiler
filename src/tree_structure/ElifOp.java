package tree_structure;

import tree_structure.Expression.Expression;
import visitors.Visitor;

import java.util.Objects;

public class ElifOp extends Node{
    private Expression expression;
    private BodyOp body;

    public ElifOp(Expression expression, BodyOp body) {
        this.expression = expression;
        this.body = body;
    }

    @Override
    public String toString() {
        return "ElifOp{" +
                "expression=" + expression +
                ", body=" + body +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ElifOp elifOp = (ElifOp) o;
        return Objects.equals(expression, elifOp.expression) && Objects.equals(body, elifOp.body);
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

    public Object accept(Visitor v){
        return v.visit(this);
    }
}
