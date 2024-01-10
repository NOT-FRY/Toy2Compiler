package scoping;

import visitors.ScopeType;

import java.util.HashMap;

public class SymbolTable {

    private String name;
    private HashMap<String, Symbol> map;
    private SymbolTable father;


    /*Se il nodo dell’AST è legato ad un costrutto di creazione di nuovo scope (ProgramOp, FunOp,
    ProcOp e BodyOp solo se non è figlio di FunOp o ProcOp) */
    private ScopeType scopeType;

    public SymbolTable(String name) {
        this.name = name;
        this.map = new HashMap<String, Symbol>();
        this.father = null;
        this.scopeType = null;
    }


    public SymbolTable(String name, SymbolTable father) {
        this.name = name;
        this.map = new HashMap<String, Symbol>();
        this.father = father;
        this.scopeType = null;
    }

    public SymbolTable(String name, SymbolTable father, ScopeType scopeType) {
        this.name = name;
        this.map = new HashMap<String, Symbol>();
        this.father = father;
        this.scopeType = scopeType;
    }

    public SymbolTable(String name, ScopeType scopeType) {
        this.name = name;
        this.map = new HashMap<String, Symbol>();
        this.father = null;
        this.scopeType = scopeType;
    }

    public void addEntry(Symbol s)throws Exception{
        if(map.containsKey(s.getId())) {
            Symbol alreadyDefinedId = map.get(s.getId());
            if (alreadyDefinedId.getKind() == s.getKind()) {
                throw new Exception("Errore di dichiarazione multipla : "+ s.getId()+" \n");
            } else {
                map.put(s.getId(), s);
            }
        }else{
            map.put(s.getId(),s);
        }
    }

    //Cerca in questa e nelle tabelle padre
    public Symbol lookup(String symbolId){
        if(map.get(symbolId) == null){
            if(this.father!=null){ //non l'ho trovato in questa tabella, vado a fare lookup nel padre
                return father.lookup(symbolId);
            }else{
                return null; //simbolo non trovato
            }
        }else{
            return map.get(symbolId);
        }
    }

    //Cerca solo in questa tabella
    public Symbol findSymbolInside(String symbolId){
        return map.get(symbolId);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SymbolTable getFather() {
        return father;
    }

    public void setFather(SymbolTable father) {
        this.father = father;
    }

    public ScopeType getScopeType() {
        return scopeType;
    }

    public void setScopeType(ScopeType scopeType) {
        this.scopeType = scopeType;
    }
}
