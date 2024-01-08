package tree_structure.Expression;

import tree_structure.Node;
import visitors.Visitor;

import java.util.Objects;

public class OrOp extends Node implements Expression {
    private Expression left;
    private Expression right;

    public OrOp(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "OrOp{" +
                "left=" + left +
                ", right=" + right +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrOp orOp)) return false;
        return Objects.equals(getLeft(), orOp.getLeft()) && Objects.equals(getRight(), orOp.getRight());
    }


    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }

    public void setLeft(Expression left) {
        this.left = left;
    }

    public void setRight(Expression right) {
        this.right = right;
    }

    @Override
    public Object accept(Visitor v){
        return v.visit(this);
    }
}
