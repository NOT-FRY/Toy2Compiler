package tree_structure.Expression;

import scoping.ExpressionType;
import tree_structure.Type;
import visitors.Visitor;

public interface Expression {
    Object accept(Visitor v);

    Type getType();

    ExpressionType getExpressionType();
}
