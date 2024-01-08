package tree_structure.Statement;

import visitors.Visitor;

public interface Statement {
    Object accept(Visitor v);
}

