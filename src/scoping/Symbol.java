package scoping;

import tree_structure.Type;

import java.util.ArrayList;
import java.util.List;


public class Symbol {
    private String id;
    private Kind kind; // var, method, etc.
    private Type type; // Tipo del simbolo (Int, String, ecc.)
    private Object value; // Valore per variabili o costanti

    //TODO da aggiungere valori di ritorno funzioni e probabilmente altre cose...
    private ArrayList<Type> returnTypes; // Lista di valori di ritorno per le funzioni

    private ArrayList<Type> paramTypes; // Lista di valori di ritorno per le funzioni


    public Symbol(String id, Kind kind, Type type) {
        this.id = id;
        this.kind = kind;
        this.type = type;
        returnTypes = new ArrayList<Type>();
    }

    public Symbol(String id, Kind kind, Type type, ArrayList<Type> returnTypes) {
        this.id = id;
        this.kind = kind;
        this.type = type;
        this.returnTypes = returnTypes;
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

    public List<Type> getParameters() {
        return returnTypes;
    }

    public void setParameters(ArrayList<Type> returnTypes) {
        this.returnTypes = returnTypes;
    }

    public void addReturnType(Type returnType){
        returnTypes.add(returnType);
    }

    public ArrayList<Type> getReturnTypes() {
        return returnTypes;
    }

    public void setReturnTypes(ArrayList<Type> returnTypes) {
        this.returnTypes = returnTypes;
    }

    public ArrayList<Type> getParamTypes() {
        return paramTypes;
    }

    public void setParamTypes(ArrayList<Type> paramTypes) {
        this.paramTypes = paramTypes;
    }

    public void addParamType(Type paramType){
        this.paramTypes.add(paramType);
    }

}
