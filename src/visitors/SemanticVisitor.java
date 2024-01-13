package visitors;
import scoping.Checks;
import scoping.Kind;
import scoping.Symbol;
import scoping.SymbolTable;
import tree_structure.*;
import tree_structure.Expression.*;
import tree_structure.Expression.Expression;

import tree_structure.Statement.*;
import tree_structure.Statement.Statement;

import java.util.ArrayList;
import java.util.Stack;

public class SemanticVisitor implements Visitor {

    private boolean mainFound = false;

    private Stack<SymbolTable> scopes;

    public SemanticVisitor() {
        scopes = new Stack<SymbolTable>();
    }

    @Override
    public Object visit(AddOp a) {

        //Caso E: operatore di espressioni o istruzioni

        a.getLeft().accept(this);

        a.getRight().accept(this);
        return null;
    }

    @Override
    public Object visit(AndOp a) {
        a.getLeft().accept(this);

        a.getRight().accept(this);

        return null;
    }

    @Override
    public Object visit(AssignStatement a) {
        ArrayList<Identifier> identifiers = a.getIdentifiers();
        for(Identifier i : identifiers){
            i.accept(this);
        }
        ArrayList<Expression> expressions = a.getExpressions();
        for(Expression e : expressions){
            e.accept(this);
        }
        return null;
    }

    @Override
    public Object visit(BodyOp b) {
        /*Se il nodo dell’AST è legato ad un costrutto di creazione di nuovo scope (ProgramOp, FunOp,
            ProcOp e BodyOp solo se non è figlio di FunOp o ProcOp) */
        ScopeType fatherScopeType = scopes.peek().getScopeType();
        if(fatherScopeType != ScopeType.FUNCTION && fatherScopeType != ScopeType.PROCEDURE) {
            SymbolTable fatherScope = scopes.peek();
            SymbolTable newBodyScope = new SymbolTable("body_scope",fatherScope,ScopeType.BODY);
            scopes.push(newBodyScope);
        }

        ArrayList<VarDeclOp> varDeclList = b.getVarDeclList();
        for(VarDeclOp v : varDeclList){
            v.accept(this);
        }
        ArrayList<Statement> statements = b.getStatementList();
        for(Statement s : statements){
            s.accept(this);
        }

        return null;
    }

    @Override
    public Object visit(DiffOp d) {

        d.getLeft().accept(this);

        d.getRight().accept(this);

        return null;
    }

    @Override
    public Object visit(DivOp d) {

        d.getLeft().accept(this);

        d.getRight().accept(this);

        return null;
    }

    @Override
    public Object visit(ElifOp e) {

        e.getExpression().accept(this);
        e.getBody().accept(this);

        return null;
    }

    @Override
    public Object visit(EQOp e) {


        e.getLeft().accept(this);

        e.getRight().accept(this);

        return null;
    }

    @Override
    public Object visit(False_const f) {

        f.getValue();

        return null;
    }

    @Override
    public Object visit(FunCallOp f) {

        f.getIdentifier().accept(this);

        SymbolTable currentTable = scopes.peek();
        Symbol s = currentTable.lookup(f.getIdentifier().getName());
        if(s==null){
            System.err.println(">Semantic error: Identificatore non dichiarato : "+ s.getId());
            System.exit(1);
        }else{
            //I TIPI DI RITORNO DELLA CHIAMATA DEVONO ESSERE GLI STESSI DELLA DEFINIZIONE!, riempio il nodo con le informazioni
            for(Type t : s.getReturnTypes()){
                f.addReturnType(t);
            }
        }

        ArrayList<Expression> expressions = f.getExpressions();
        for(Expression e : expressions){
            e.accept(this);
        }

        return null;
    }

    @Override
    public Object visit(FunctionOp f) {

        SymbolTable father = scopes.peek();

        /*
        * se la tabella corrente contiene già la dichiarazione dell’identificatore coinvolto allora
          restituisci “errore di dichiarazione multipla”
        *
        * */
        if(father.findSymbolInside(f.getIdentifier().getName())!=null){
            System.err.println(">Semantic error: Errore di dichiarazione multipla : "+ f.getIdentifier().getName());
            System.exit(1);
        }
        else {
            SymbolTable function = new SymbolTable(f.getIdentifier().getName(), father, ScopeType.FUNCTION);
            scopes.push(function);

            f.getIdentifier().accept(this);
        }

        //aggiungo la definizione della funzione alla symbol table corrente
        Symbol s = new Symbol(f.getIdentifier().getName(),Kind.METHOD);

        ArrayList<ProcFunParamOp> paramOps = f.getProcFunParamOpList();
        for(ProcFunParamOp p : paramOps){
            s.addParamType(p.getType());
            p.accept(this);
        }
        ArrayList<Type> types = f.getReturnTypes();
        for(Type t : types){
            s.addReturnType(t);
        }

        try {
            father.addEntry(s);
        }catch(Exception e){
            System.err.println(e.getMessage());
        }

        f.getBody().accept(this);

        return null;
    }


    @Override
    public Object visit(GEOp g) {

        g.getLeft().accept(this);

        g.getRight().accept(this);

        return null;
    }

    @Override
    public Object visit(GTOp g) {

        g.getLeft().accept(this);

        g.getRight().accept(this);

        return null;
    }

    @Override
    public Object visit(Identifier i) {
        Qualifier q = i.getQualifier();

        SymbolTable currentTable = scopes.peek();
        Symbol s = currentTable.lookup(i.getName());
        if(s==null){
            System.err.println(">Semantic error: Identificatore non dichiarato : "+ i.getName());
            System.exit(1);
        }else{
            i.setType(s.getType());
        }

        return null;
    }

    @Override
    public Object visit(IfStatement i) {

        SymbolTable father = scopes.peek();

        SymbolTable ifScope = new SymbolTable("if_scope",father,ScopeType.IF);
        scopes.push(ifScope);

        i.getExpression().accept(this);
        i.getBody().accept(this);
        ArrayList<ElifOp> elifOps = i.getElifList();
        for (ElifOp p : elifOps) {
            p.accept(this);
        }
        i.getElseBody().accept(this);

        return null;
    }

    @Override
    public Object visit(Integer_const i) {

        i.getValue();

        return null;
    }

    @Override
    public Object visit(LEOp l) {

        l.getLeft().accept(this);

        l.getRight().accept(this);

        return null;
    }

    @Override
    public Object visit(LTOp l) {

        l.getLeft().accept(this);

        l.getRight().accept(this);

        return null;
    }


    @Override
    public Object visit(MulOp m) {

        m.getLeft().accept(this);

        m.getRight().accept(this);

        return null;
    }

    @Override
    public Object visit(NEOp n) {

        n.getLeft().accept(this);

        n.getRight().accept(this);

        return null;
    }

    @Override
    public Object visit(NotOp n) {

        n.getValue().accept(this);

        return null;
    }

    @Override
    public Object visit(OrOp o) {

        o.getLeft().accept(this);

        o.getRight().accept(this);

        return null;
    }

    @Override
    public Object visit(ProcFunParamOp p){

        p.getIdentifier().accept(this);

        SymbolTable currentTable = scopes.peek();
        if(currentTable.findSymbolInside(p.getIdentifier().getName())!=null){
            System.err.println(">Semantic error: Errore di dichiarazione multipla : "+ p.getIdentifier().getName());
            System.exit(1);
        }else{
            Symbol s = new Symbol(p.getIdentifier().getName(), Kind.VAR,p.getType());
            try {
                currentTable.addEntry(s);
            }catch(Exception e){
                System.err.println(e.getMessage());
            }
        }

        return null;
    }

    @Override
    public Object visit(ProcedureOp p) {

        if(p.getIdentifier().getName().equals("main"))
            mainFound=true;

        SymbolTable father = scopes.peek();

        if(father.findSymbolInside(p.getIdentifier().getName())!=null){
            System.err.println(">Semantic error: Errore di dichiarazione multipla : "+ p.getIdentifier().getName());
            System.exit(1);
        }else {
            SymbolTable function = new SymbolTable(p.getIdentifier().getName(), father, ScopeType.PROCEDURE);
            scopes.push(function);
            p.getIdentifier().accept(this);
        }

        ArrayList<ProcFunParamOp> paramOps = p.getProcFunParamOpList();
        for (ProcFunParamOp pr : paramOps) {
            pr.accept(this);
        }


        p.getBody().accept(this);

        return null;
    }

    @Override
    public Object visit(ProgramOp p) {
        SymbolTable global = new SymbolTable("global",ScopeType.GLOBAL);
        scopes.push(global);

        ArrayList<VarDeclOp> varDeclList = p.getVarDeclList();
        for (VarDeclOp v : varDeclList) {
            v.accept(this);
        }
        ArrayList<? extends FunctionOrProcedure> paramOps = p.getFunProcList();
        for (FunctionOrProcedure n : paramOps) {
            n.accept(this);
        }

        if(!mainFound){
            System.err.println(">Semantic error: main procedure not found");
        }

        return null;
    }

    @Override
    public Object visit(ReadStatement r) {

        ArrayList<Expression> expressions = r.getExpressions();
        for (Expression v : expressions) {
            v.accept(this);
        }

        return null;
    }

    @Override
    public Object visit(Real_const r) {

        r.getValue();

        return null;
    }

    @Override
    public Object visit(ReturnStatement r) {

        ArrayList<Expression> expressions = r.getExpressions();
        for(Expression e : expressions) {
            e.accept(this);
        }

        return null;
    }

    @Override
    public Object visit(String_const s) {
        s.getValue();

        return null;
    }

    @Override
    public Object visit(True_const t) {
        t.getValue();
        return null;
    }

    @Override
    public Object visit(UminusOp u) {

        u.getValue().accept(this);

        return null;
    }

    @Override
    public Object visit(VarDeclOp v) {

        if(!Checks.checkDeclaration(v)){
            System.err.println(">Semantic error: Nell'inizializzazione il numero delle costanti deve essere pari al numero degli id ");
            System.exit(1);
        }

        ArrayList<IdentifierExpression> identifierExpressionList = v.getIdentifierExpressionsList();
        for(IdentifierExpression ie : identifierExpressionList) {
            SymbolTable currentTable = scopes.peek();

            if(currentTable.findSymbolInside(ie.getIdentifier().getName())!=null){
                System.err.println(">Semantic error: Errore di dichiarazione multipla : "+ ie.getIdentifier().getName());
                System.exit(1);
            }
            else {

                Symbol s = new Symbol(ie.getIdentifier().getName(), Kind.VAR);

                if(v.getDeclarationType() == DeclarationType.INITIALIZATION){
                    //Inferisco il tipo

                    Expression e = ie.getExpression();
                    if(e instanceof False_const){
                        s.setType(Type.BOOL);
                    }else if(e instanceof  True_const){
                        s.setType(Type.BOOL);
                    }else if(e instanceof  String_const){
                        s.setType(Type.STRING);
                    }else if(e instanceof  Integer_const){
                        s.setType(Type.INTEGER);
                    }else if(e instanceof  Real_const){
                        s.setType(Type.REAL);
                    }else{
                        System.err.println(">Semantic error: Errore di inferenza di tipo, il tipo non può essere dedotto: "+ ie.getIdentifier().getName());
                        System.exit(1);
                    }

                }else{//dichiarazione, il tipo mi viene dato
                    s.setType(v.getType());
                }

                try {
                    currentTable.addEntry(s);
                }catch(Exception e){
                    System.err.println(e.getMessage());
                }

                ie.accept(this);
            }

        }

        return null;
    }

    @Override
    public Object visit(WhileStatement w) {

        SymbolTable father = scopes.peek();

        SymbolTable whileScope = new SymbolTable("while_scope",father,ScopeType.WHILE);
        scopes.push(whileScope);

        w.getExpression().accept(this);
        w.getBody().accept(this);

        return null;
    }

    @Override
    public Object visit(WriteStatement w) {

        WritingType writingType = w.getWritingType();

        ArrayList<Expression> expressions = w.getExpressions();
        for(Expression e : expressions) {
            e.accept(this);
        }

        return null;
    }

    @Override
    public Object visit(ElseOp e) {

        e.getBody().accept(this);

        return null;
    }


    @Override
    public Object visit(IterOp i) {

        ArrayList<VarDeclOp> varDeclList = i.getVarDeclList();
        for(VarDeclOp v : varDeclList) {
            v.accept(this);
        }
        ArrayList<? extends FunctionOrProcedure> paramOps = i.getFunProcList();
        for(FunctionOrProcedure n : paramOps) {
            n.accept(this);
        }

        return null;
    }

    @Override
    public Object visit(IdentifierExpression ie) {

        if(ie.getIdentifier()!=null){
            ie.getIdentifier().accept(this);
        }
        if(ie.getExpression()!=null){
            ie.getExpression().accept(this);
        }

        return null;
    }

    @Override
    public Object visit(IOArg i) {

        i.isDollarSign();

        i.getExpression().accept(this);

        return null;
    }

    public Object visit(ProcedureExpression p) {
        if(p.getIdentifier() != null) {
            p.isReference();
            p.getIdentifier().accept(this);
        }
        if(p.getExpression() != null)
            p.getExpression().accept(this);

        return null;
    }

    @Override
    public Object visit(ProcCallOp p) {
        p.getIdentifier().accept(this);
        ArrayList<ProcedureExpression> Expressions = p.getProcedureExpressions();
        for(ProcedureExpression i : Expressions) {
            i.accept(this);
        }

        return null;
    }

}
