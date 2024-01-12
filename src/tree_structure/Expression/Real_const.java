package tree_structure.Expression;

import scoping.ExpressionType;
import tree_structure.Node;
import tree_structure.Type;
import visitors.Visitor;

public class Real_const extends Node implements Expression {
    private double value;

    private final Type type= Type.REAL;

    private ExpressionType expressionType = ExpressionType.CONST;

    @Override
    public String toString() {
        return "real_const{" +
                "value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Real_const realConst)) return false;
        return getValue() == realConst.getValue();
    }


    public Real_const(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
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

