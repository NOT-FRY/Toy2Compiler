package tree_structure.Expression;

import scoping.ExpressionType;
import tree_structure.Node;
import tree_structure.Type;
import visitors.Visitor;

public class False_const extends Node implements Expression {
    private final boolean value;

    private final Type type = Type.BOOL;

    private ExpressionType expressionType = ExpressionType.CONST;

    @Override
    public String toString() {
        return "false_const{" +
                "value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof False_const that)) return false;
        return getValue() == that.getValue();
    }


    public False_const() {
        this.value = false;
    }

    public boolean getValue() {
        return value;
    }


    @Override
    public Object accept(Visitor v){
        return v.visit(this);
    }

    public Type getType() {
        return type;
    }

    public ExpressionType getExpressionType() {
        return expressionType;
    }

}