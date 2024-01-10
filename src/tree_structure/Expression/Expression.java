package tree_structure.Expression;

import scoping.Type;
import visitors.Visitor;

public interface Expression {
    Object accept(Visitor v);

    Type getType();
}
