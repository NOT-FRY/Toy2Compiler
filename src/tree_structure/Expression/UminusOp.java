package tree_structure.Expression;

import scoping.ExpressionType;
import tree_structure.Node;
import tree_structure.Type;
import visitors.Visitor;

import java.util.Objects;

public class UminusOp extends Node implements Expression {
    private Expression value;

    private Type type;

    private ExpressionType expressionType = ExpressionType.UMINUS;

    @Override
    public String toString() {
        return "UminusOp{" +
                "value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UminusOp uminusOp)) return false;
        return Objects.equals(getValue(), uminusOp.getValue());
    }

    public UminusOp(Expression value) {
        this.value = value;
    }

    public Expression getValue() {
        return value;
    }

    public void setValue(Expression value) {
        this.value = value;
    }

    @Override
    public Object accept(Visitor v){
        return v.visit(this);
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public ExpressionType getExpressionType() {
        return expressionType;
    }

}
