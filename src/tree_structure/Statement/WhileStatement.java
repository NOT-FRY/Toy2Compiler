package tree_structure.Statement;

import tree_structure.BodyOp;
import tree_structure.Expression.Expression;
import tree_structure.Node;
import visitors.Visitor;

import java.util.Objects;

public class WhileStatement extends Node implements Statement {
    private Expression expression;

    private BodyOp body;

    public WhileStatement(Expression expression, BodyOp body) {
        this.expression = expression;
        this.body = body;
    }

    @Override
    public String toString() {
        return "WhileStatement{" +
                "expression=" + expression +
                ", body=" + body +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WhileStatement that)) return false;
        return Objects.equals(getExpression(), that.getExpression()) && Objects.equals(getBody(), that.getBody());
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