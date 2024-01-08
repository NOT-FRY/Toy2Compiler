package tree_structure.Statement;

import tree_structure.Expression.Expression;
import tree_structure.Node;
import visitors.Visitor;

import java.util.ArrayList;
import java.util.Objects;

public class WriteStatement extends Node implements Statement{
    private WritingType writingType;
    private ArrayList<Expression> expressions;

    public WriteStatement(WritingType writingType, ArrayList<Expression> expressions) {
        this.writingType = writingType;
        this.expressions = expressions;
    }

    @Override
    public String toString() {
        return "WriteStatement{" +
                "writingType=" + writingType +
                ", expressions=" + expressions +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WriteStatement that)) return false;
        return getWritingType() == that.getWritingType() && Objects.equals(getExpressions(), that.getExpressions());
    }

    public WritingType getWritingType() {
        return writingType;
    }

    public void setWritingType(WritingType writingType) {
        this.writingType = writingType;
    }

    public ArrayList<Expression> getExpressions() {
        return expressions;
    }

    public void setExpressions(ArrayList<Expression> expressions) {
        this.expressions = expressions;
    }

    public Object accept(Visitor v){
        return v.visit(this);
    }
}
