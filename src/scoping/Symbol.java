package scoping;

import tree_structure.Type;

import java.util.List;


public class Symbol {
    private String id;
    private Kind kind; // var, method, etc.
    private Type type; // Tipo del simbolo (Int, String, ecc.)
    private Object value; // Valore per variabili o costanti
    private List<Symbol> parameters; // Lista di parametri per funzioni o procedure


    public Symbol(String id, Kind kind, Type type) {
        this.id = id;
        this.kind = kind;
        this.type = type;
    }

    public Symbol(String id, Kind kind, Type type, List<Symbol> parameters) {
        this.id = id;
        this.kind = kind;
        this.type = type;
        this.parameters = parameters;
    }

    public Symbol(String id, Kind kind) {
        this.id = id;
        this.kind = kind;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Kind getKind() {
        return kind;
    }

    public void setKind(Kind kind) {
        this.kind = kind;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public List<Symbol> getParameters() {
        return parameters;
    }

    public void setParameters(List<Symbol> parameters) {
        this.parameters = parameters;
    }
}
