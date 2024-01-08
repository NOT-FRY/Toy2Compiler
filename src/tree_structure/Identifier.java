package tree_structure;

import tree_structure.Expression.Expression;
import visitors.Visitor;

import java.util.Objects;
/* enum Qualifier {
    OUT,
    INOUT,
    IN
}
ProcParamId ::= ID
        | OUT ID;
 Per via di questa produzione, abbiamo deciso di inserire il qualifier nell'identificatore,
 un' altra soluzione sarebbe stata quella di creare un'altra classe ProcParamId con l'identificatore associato
 al qualifier.

 */
public class Identifier extends Node implements Expression {
    private String name;
    private Qualifier qualifier;

    public Identifier(String name) {
        this.name = name;
        this.qualifier = null;
    }
    public Identifier(String name, Qualifier qualifier) {
        this.name = name;
        this.qualifier = qualifier;
    }

    public Qualifier getQualifier() {
        return qualifier;
    }

    public void setQualifier(Qualifier qualifier) {
        this.qualifier = qualifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Identifier that = (Identifier) o;
        return Objects.equals(name, that.name) && qualifier == that.qualifier;
    }

    @Override
    public String toString() {
        return "Identifier{" +
                "name='" + name + '\'' +
                ", qualifier=" + qualifier +
                '}';
    }

    @Override
    public Object accept(Visitor v) {
        return v.visit(this);
    }
}
