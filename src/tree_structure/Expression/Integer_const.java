package tree_structure.Expression;

import tree_structure.Node;
import tree_structure.Type;
import visitors.Visitor;

public class Integer_const extends Node implements Expression {
    private int value;

    private final Type type = Type.INTEGER;

    @Override
    public String toString() {
        return "integer_const{" +
                "value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Integer_const that)) return false;
        return getValue() == that.getValue();
    }



    public Integer_const(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public Object accept(Visitor v){
        return v.visit(this);
    }

    public Type getType() {
        return type;
    }

}
