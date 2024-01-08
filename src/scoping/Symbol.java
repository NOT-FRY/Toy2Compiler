package scoping;

public class Symbol {
    private String id;
    private Kind kind; //var / method / etc..

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
}
