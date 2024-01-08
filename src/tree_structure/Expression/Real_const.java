package tree_structure.Expression;

import tree_structure.Node;
import visitors.Visitor;

public class Real_const extends Node implements Expression {
    private double value;

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
}

