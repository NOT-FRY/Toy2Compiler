package tree_structure;

import tree_structure.Expression.Identifier;
import visitors.Visitor;

import java.util.Objects;

/*enum Qualifier {
    OUT,
    INOUT,
    IN
}*/
/*ProcParamId ::= ID
        | OUT ID;
 Per via di questa produzione, abbiamo deciso di inserire il qualifier nell'identificatore,
 un' altra soluzione sarebbe stata quella di creare un'altra classe ProcParamId con l'identificatore associato
 al qualifier.

 */
public class ProcFunParamOp extends Node{
    //private Qualifier qualifier;
    private Identifier identifier;
    private Type type;

    public ProcFunParamOp(Identifier identifier, Type type) {
        this.identifier = identifier;
        this.type = type;
    }

    @Override
    public String toString() {
        return "ProcFunParamOp{" +
                "identifier=" + identifier +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProcFunParamOp that)) return false;
        return  Objects.equals(getIdentifier(), that.getIdentifier()) && getType() == that.getType();
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Object accept(Visitor v){
        return v.visit(this);
    }
}
