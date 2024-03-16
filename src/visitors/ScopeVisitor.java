package visitors;
import scoping.*;
import tree_structure.*;
import tree_structure.Expression.*;
import tree_structure.Expression.Expression;

import tree_structure.Statement.*;
import tree_structure.Statement.Statement;

import java.util.ArrayList;


/*Prima visita dell'albero che serve ad aggiungere informazioni ai nodi, come valori di ritorno delle funzioni
 e tipi dei parametri
         */
public class ScopeVisitor implements Visitor {

    private boolean mainFound = false;

    private SymbolTableStack symbolTableStack;

    public ScopeVisitor() {
        symbolTableStack = new SymbolTableStack();
    }

    @Override
    public Object visit(AddOp a) {
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
        ScopeType fatherScopeType = symbolTableStack.peek().getScopeType();
        if(fatherScopeType != ScopeType.FUNCTION && fatherScopeType != ScopeType.PROCEDURE) {
            SymbolTable st = new SymbolTable("body_scope",ScopeType.BODY);
            symbolTableStack.enterScope(st);
            b.setSymbolTable(symbolTableStack.peek());
        }

        ArrayList<VarDeclOp> varDeclList = b.getVarDeclList();
        for(VarDeclOp v : varDeclList){
            v.accept(this);
        }
        ArrayList<Statement> statements = b.getStatementList();
        for(Statement s : statements){
            s.accept(this);
        }

        if(fatherScopeType != ScopeType.FUNCTION && fatherScopeType != ScopeType.PROCEDURE) {
            symbolTableStack.exitScope();
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
        return null;
    }

    @Override
    public Object visit(FunCallOp f) {

        f.getIdentifier().accept(this);

        ArrayList<Expression> arguments = f.getExpressions();
        for(Expression e : arguments){
            e.accept(this);
        }

        return null;
    }

    @Override
    public Object visit(FunctionOp f) {

        if(symbolTableStack.lookup(f.getIdentifier().getName(),Kind.METHOD)!=null){
            System.err.println(">Semantic error: Errore di dichiarazione multipla : "+ f.getIdentifier().getName());
            System.exit(1);
        }
        else {
            SymbolTable father = symbolTableStack.peek();
            SymbolTable st = new SymbolTable(f.getIdentifier().getName(),ScopeType.FUNCTION);
            symbolTableStack.enterScope(st);

            f.setSymbolTable(symbolTableStack.peek());
            f.getIdentifier().accept(this);

            //aggiungo la definizione della funzione alla symbol table corrente(padre)
            Symbol s = new Symbol(f.getIdentifier().getName(), Kind.METHOD);

            ArrayList<ProcFunParamOp> paramOps = f.getProcFunParamOpList();
            for (ProcFunParamOp p : paramOps) {
                p.accept(this);
                s.addParamType(p.getType());
            }
            ArrayList<Type> returnTypes = f.getReturnTypes();
            for (Type t : returnTypes) {
                s.addReturnType(t);
            }

            try {
                father.addEntry(s);
            } catch (Exception e) {
                System.err.println(e.getMessage());
                System.exit(1);
            }

            f.getBody().accept(this);

            symbolTableStack.exitScope();

        }

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
        return null;
    }

    @Override
    public Object visit(IfStatement i) {

        SymbolTable current = symbolTableStack.peek();
        current.setScopeType(ScopeType.IF);

        i.setSymbolTable(current);

        i.getExpression().accept(this);
        i.getBody().accept(this);
        ArrayList<ElifOp> elifOps = i.getElifList();
        for (ElifOp p : elifOps) {
            p.accept(this);
        }
        if(i.getElseBody() != null)
            i.getElseBody().accept(this);

        return null;
    }

    @Override
    public Object visit(Integer_const i) {
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

        if(symbolTableStack.lookup(p.getIdentifier().getName(),Kind.VAR)!=null){
            System.err.println(">Semantic error: Errore di dichiarazione multipla : "+ p.getIdentifier().getName());
            System.exit(1);
        }else{
            Symbol s = new Symbol(p.getIdentifier().getName(), Kind.VAR,p.getType());
            try {
                symbolTableStack.peek().addEntry(s);
            }catch(Exception e){
                System.err.println(e.getMessage());
                System.exit(1);
            }
        }

        return null;
    }

    @Override
    public Object visit(ProcedureOp p) {

        if(p.getIdentifier().getName().equals("main"))
            mainFound=true;


        if(symbolTableStack.lookup(p.getIdentifier().getName(),Kind.METHOD)!=null){
            System.err.println(">Semantic error: Errore di dichiarazione multipla : "+ p.getIdentifier().getName());
            System.exit(1);
        }else {
            SymbolTable father = symbolTableStack.peek();

            SymbolTable st = new SymbolTable(p.getIdentifier().getName(),ScopeType.PROCEDURE);
            symbolTableStack.enterScope(st);
            p.setSymbolTable(symbolTableStack.peek());

            p.getIdentifier().accept(this);

            //aggiungo la definizione della funzione alla symbol table corrente
            Symbol s = new Symbol(p.getIdentifier().getName(),Kind.METHOD);

            ArrayList<ProcFunParamOp> paramOps = p.getProcFunParamOpList();
            s.setParameters(paramOps);
            for (ProcFunParamOp pr : paramOps) {
                s.addParamType(pr.getType());
                pr.accept(this);
            }

            try {
                father.addEntry(s);
            }catch(Exception e){
                System.err.println(e.getMessage());
                System.exit(1);
            }

            p.getBody().accept(this);

            symbolTableStack.exitScope();

        }

        return null;
    }

    @Override
    public Object visit(ProgramOp p) {
        SymbolTable st = new SymbolTable("global",ScopeType.GLOBAL);
        symbolTableStack.enterScope(st);
        p.setSymbolTable(symbolTableStack.peek());

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
            System.exit(1);
        }

        symbolTableStack.exitScope();
        return null;
    }

    @Override
    public Object visit(ReadStatement r) {

        ArrayList<Expression> expressions = r.getExpressions();
        for (Expression v : expressions) {
            v.accept(this);
        }

        r.setType(Type.NOTYPE);

        return null;
    }

    @Override
    public Object visit(Real_const r) {
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
        return null;
    }

    @Override
    public Object visit(True_const t) {
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
            SymbolTable currentTable = symbolTableStack.peek();

            ie.accept(this);

            if(symbolTableStack.lookup(ie.getIdentifier().getName(),Kind.METHOD)!=null){
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
                    System.exit(1);
                }

            }

        }

        return null;
    }

    @Override
    public Object visit(WhileStatement w) {

        SymbolTable current = symbolTableStack.peek();
        current.setScopeType(ScopeType.WHILE);

        w.setSymbolTable(current);

        w.getExpression().accept(this);
        w.getBody().accept(this);

        return null;
    }

    @Override
    public Object visit(WriteStatement w) {
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
        //Utilizzato solo per costruire l'albero nel parser
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
        i.getExpression().accept(this);
        return null;
    }

    public Object visit(ProcedureExpression p) {
        if(p.getIdentifier() != null) {
            p.getIdentifier().accept(this);
        }
        if(p.getExpression() != null)
            p.getExpression().accept(this);

        return null;
    }

    @Override
    public Object visit(ProcCallOp p) {

        p.getIdentifier().accept(this);

        ArrayList<ProcedureExpression> arguments = p.getProcedureExpressions();
        for(ProcedureExpression e : arguments){
            e.accept(this);
        }

        return null;
    }

}
