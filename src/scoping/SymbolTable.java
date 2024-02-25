package scoping;

import visitors.ScopeType;

import java.util.HashMap;
import java.util.Objects;

public class SymbolTable {

    private String name;
    private HashMap<SymbolKey, Symbol> map;

    /*Se il nodo dell’AST è legato ad un costrutto di creazione di nuovo scope (ProgramOp, FunOp,
    ProcOp e BodyOp solo se non è figlio di FunOp o ProcOp) */
    private ScopeType scopeType;

    public SymbolTable(String name) {
        this.name = name;
        this.map = new HashMap<SymbolKey, Symbol>();
        this.scopeType = null;
    }

    public SymbolTable(String name, ScopeType scopeType) {
        this.name = name;
        this.map = new HashMap<SymbolKey, Symbol>();
        this.scopeType = scopeType;
    }

    public void addEntry(Symbol s)throws Exception{
        if(map.containsKey(new SymbolKey(s.getId(),s.getKind()))) {
            throw new Exception("Errore di dichiarazione multipla : "+ s.getId()+" \n");
        }else{
            map.put(new SymbolKey(s.getId(),s.getKind()),s);
        }
    }

    public Symbol lookup(String symbolId,Kind kind){
        return map.get(new SymbolKey(symbolId,kind));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ScopeType getScopeType() {
        return scopeType;
    }

    public void setScopeType(ScopeType scopeType) {
        this.scopeType = scopeType;
    }

    public class SymbolKey{
        private String id;
        private Kind kind;

        public SymbolKey(String id, Kind kind) {
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SymbolKey symbolKey = (SymbolKey) o;
            return Objects.equals(id, symbolKey.id) && kind == symbolKey.kind;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, kind);
        }
    }
}
