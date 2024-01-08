package tree_structure.Statement;

import tree_structure.Expression.Expression;
import tree_structure.Node;
import visitors.Visitor;

import java.util.ArrayList;
import java.util.Objects;

public class ReturnStatement extends Node implements Statement {
    private ArrayList<Expression> expressions;

    public ReturnStatement(ArrayList<Expression> expressions) {
        this.expressions = expressions;
    }

    @Override
    public String toString() {
        return "ReturnStatement{" +
                "expression=" + expressions +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReturnStatement that)) return false;
        return Objects.equals(getExpressions(), that.getExpressions());
    }

    public ArrayList<Expression> getExpressions() {
        return expressions;
    }

    public void setExpression(ArrayList<Expression> expressions) {
        this.expressions = expressions;
    }

    public Object accept(Visitor v) { return v.visit(this);}
}
