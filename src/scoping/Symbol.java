package scoping;

import tree_structure.ProcFunParamOp;
import tree_structure.Type;

import java.util.ArrayList;
import java.util.List;


public class Symbol {
    private String id;
    private Kind kind; // var, method, etc.
    private Type type; // Tipo del simbolo (Int, String, ecc.)
    private Object value; // Valore per variabili o costanti

    private ArrayList<Type> returnTypes; // Lista di valori di ritorno per le funzioni

    private ArrayList<Type> paramTypes; // Lista dei tipi dei parametri per le funzioni

    private ArrayList<ProcFunParamOp> parameters;


    public Symbol(String id, Kind kind, Type type) {
        this.id = id;
        this.kind = kind;
        this.type = type;
        returnTypes = new ArrayList<Type>();
        paramTypes = new ArrayList<Type>();
    }

    public Symbol(String id, Kind kind, Type type, ArrayList<Type> returnTypes) {
        this.id = id;
        this.kind = kind;
        this.type = type;
        this.returnTypes = returnTypes;
        paramTypes = new ArrayList<Type>();
    }

    public Symbol(String id, Kind kind) {
        this.id = id;
        this.kind = kind;
        returnTypes = new ArrayList<Type>();
        paramTypes = new ArrayList<Type>();

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

    public ArrayList<ProcFunParamOp> getParameters() {
        return parameters;
    }

    public void setParameters(ArrayList<ProcFunParamOp> parameters) {
        this.parameters = parameters;
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
