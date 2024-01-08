package tree_structure.Expression;

import tree_structure.Node;
import visitors.Visitor;

import java.util.Objects;

public class String_const extends Node implements Expression {
    private String value;

    @Override
    public String toString() {
        return "string_const{" +
                "value='" + value + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof String_const that)) return false;
        return Objects.equals(getValue(), that.getValue());
    }


    public String_const(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public Object accept(Visitor v){
        return v.visit(this);
    }
}
