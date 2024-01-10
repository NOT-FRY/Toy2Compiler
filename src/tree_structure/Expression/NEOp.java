package tree_structure.Expression;

import tree_structure.Node;
import tree_structure.Type;
import visitors.Visitor;

import java.util.Objects;

public class NEOp extends Node implements Expression {
    private Expression left;
    private Expression right;

    private Type type;

    public NEOp(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "NEOp{" +
                "left=" + left +
                ", right=" + right +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NEOp neOp)) return false;
        return Objects.equals(getLeft(), neOp.getLeft()) && Objects.equals(getRight(), neOp.getRight());
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}