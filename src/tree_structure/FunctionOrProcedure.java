package tree_structure;

import visitors.Visitor;

public interface FunctionOrProcedure {
    Object accept(Visitor v);
}
