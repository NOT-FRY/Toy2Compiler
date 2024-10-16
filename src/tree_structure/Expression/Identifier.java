package tree_structure.Expression;

import scoping.ExpressionType;
import scoping.Kind;
import scoping.SymbolTable;
import tree_structure.Node;
import tree_structure.Qualifier;
import tree_structure.Type;
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

    /*
    * i valori out (riferimento) e inout (non riferimento)  qualificano i parametri di procedura
    *    mentre in (solo lettura) qualifica i parametri di funzione
    * */

    private Qualifier qualifier;

    private Type type;

    private ExpressionType expressionType = ExpressionType.ID;

    private SymbolTable symbolTable;

    private Kind identifierType;

    public Identifier(String name,Kind identifierType) {
        this.name = name;
        this.qualifier = null;
        this.identifierType = identifierType;
    }
    public Identifier(String name, Qualifier qualifier, Kind identifierType) {
        this.name = name;
        this.qualifier = qualifier;
        this.identifierType = identifierType;
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
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

    public Kind getIdentifierType() {
        return identifierType;
    }

    public void setIdentifierType(Kind identifierType) {
        this.identifierType = identifierType;
    }
}
