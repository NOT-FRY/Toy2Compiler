package tree_structure;

import visitors.Visitor;

public abstract class Node {

    public abstract Object accept(Visitor v);
}
