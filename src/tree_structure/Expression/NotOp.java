package tree_structure.Expression;

import tree_structure.Node;
import visitors.Visitor;

import java.util.Objects;

public class NotOp extends Node implements Expression {
    private Expression value;

    @Override
    public String toString() {
        return "NotOp{" +
                "value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NotOp notOp)) return false;
        return Objects.equals(getValue(), notOp.getValue());
    }

    public NotOp(Expression value) {
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
}
