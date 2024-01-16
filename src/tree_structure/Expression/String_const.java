package tree_structure.Expression;

import scoping.ExpressionType;
import scoping.SymbolTable;
import tree_structure.Node;
import tree_structure.Type;
import visitors.Visitor;

import java.util.Objects;

public class String_const extends Node implements Expression {
    private String value;

    private final Type type = Type.STRING;

    private ExpressionType expressionType = ExpressionType.CONST;

    private SymbolTable symbolTable;

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

    public Type getType() {
        return type;
    }

    public ExpressionType getExpressionType() {
        return expressionType;
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }
}
