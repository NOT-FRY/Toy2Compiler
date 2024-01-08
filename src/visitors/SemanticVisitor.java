package visitors;
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

    private Stack<SymbolTable> scopes;

    public SemanticVisitor() {
        scopes = new Stack<SymbolTable>();
    }

    @Override
    public Object visit(AddOp a) {
        out.println(getIndent() + "<AddOp>");
        increaseIndent();
        out.println(getIndent() + "<leftVal>");
        a.getLeft().accept(this);
        out.println(getIndent() + "</leftVal>");
        out.println(getIndent() + "<rightVal>");
        a.getRight().accept(this);
        out.println(getIndent() + "</rightVal>");
        decreaseIndent();
        out.println(getIndent() + "</AddOp>");
        return null;
    }

    @Override
    public Object visit(AndOp a) {
        out.println(getIndent() + "<AndOp>");
        increaseIndent();
        out.println(getIndent() + "<leftVal>");
        a.getLeft().accept(this);
        out.println(getIndent() + "</leftVal>");
        out.println(getIndent() + "<rightVal>");
        a.getRight().accept(this);
        out.println(getIndent() + "</rightVal>");
        decreaseIndent();
        out.println(getIndent() + "</AndOp>");
        return null;
    }

    @Override
    public Object visit(AssignStatement a) {
        out.println(getIndent() + "<AssignStatement>");
        increaseIndent();
        ArrayList<Identifier> identifiers = a.getIdentifiers();
        for(Identifier i : identifiers){
            i.accept(this);
        }
        ArrayList<Expression> expressions = a.getExpressions();
        for(Expression e : expressions){
            e.accept(this);
        }
        decreaseIndent();
        out.println(getIndent() + "</AssignStatement>");
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

        ArrayList<VarDeclInterface> varDeclList = b.getVarDeclList();
        for(VarDeclInterface v : varDeclList){
            if(v instanceof VarDeclOp)
                visit((VarDeclOp) v);
            else if(v instanceof AssignStatement)
                visit((AssignStatement) v);
        }
        ArrayList<Statement> statements = b.getStatementList();
        for(Statement s : statements){
            s.accept(this);
        }

        return null;
    }

    @Override
    public Object visit(DiffOp d) {
        out.println(getIndent() + "<DiffOp>");
        increaseIndent();
        out.println(getIndent() + "<leftVal>");
        d.getLeft().accept(this);
        out.println(getIndent() + "</leftVal>");
        out.println(getIndent() + "<rightVal>");
        d.getRight().accept(this);
        out.println(getIndent() + "</rightVal>");
        decreaseIndent();
        out.println(getIndent() + "</DiffOp>");
        return null;
    }

    @Override
    public Object visit(DivOp d) {
        out.println(getIndent() + "<DivOp>");
        increaseIndent();
        out.println(getIndent() + "<leftVal>");
        d.getLeft().accept(this);
        out.println(getIndent() + "</leftVal>");
        out.println(getIndent() + "<rightVal>");
        d.getRight().accept(this);
        out.println(getIndent() + "</rightVal>");
        decreaseIndent();
        out.println(getIndent() + "</DivOp>");
        return null;
    }

    @Override
    public Object visit(ElifOp e) {
        out.println(getIndent() + "<ElifOp>");
        increaseIndent();
        e.getExpression().accept(this);
        e.getBody().accept(this);
        decreaseIndent();
        out.println(getIndent() + "</ElifOp>");
        return null;
    }

    @Override
    public Object visit(EQOp e) {
        out.println(getIndent() + "<EQOp>");
        increaseIndent();
        out.println(getIndent() + "<leftVal>");
        e.getLeft().accept(this);
        out.println(getIndent() + "</leftVal>");
        out.println(getIndent() + "<rightVal>");
        e.getRight().accept(this);
        out.println(getIndent() + "</rightVal>");
        decreaseIndent();
        out.println(getIndent() + "</EQOp>");
        return null;
    }

    @Override
    public Object visit(False_const f) {
        out.println(getIndent() + "<false_const>");
        increaseIndent();
        out.println(getIndent() + f.getValue());
        decreaseIndent();
        out.println(getIndent() + "</false_const>");
        return null;
    }

    @Override
    public Object visit(FunCallOp f) {
        out.println(getIndent() + "<FunCallOp>");
        increaseIndent();
        f.getIdentifier().accept(this);
        ArrayList<Expression> expressions = f.getExpressions();
        for(Expression e : expressions){
            e.accept(this);
        }
        decreaseIndent();
        out.println(getIndent() + "</FunCallOp>");
        return null;
    }

    @Override
    public Object visit(FunctionOp f) {
        f.getIdentifier().accept(this);
        ArrayList<ProcFunParamOp> paramOps = f.getProcFunParamOpList();
        for(ProcFunParamOp p : paramOps){
            p.accept(this);
        }
        ArrayList<Type> types = f.getReturnTypes();
        //for(Type t : types){
        //    out.println(getIndent() + t);
        //}


        SymbolTable father = scopes.peek();

        /*
        * se la tabella corrente contiene già la dichiarazione dell’identificatore coinvolto allora
          restituisci “errore di dichiarazione multipla”
        *
        * */
        if(father.lookup(f.getIdentifier().getName())!=null){
            System.err.println("Errore di dichiarazione multipla : "+ f.getIdentifier().getName());
            System.exit(1);
        }
        else {
            SymbolTable function = new SymbolTable(f.getIdentifier().getName(), father, ScopeType.FUNCTION);
            scopes.push(function);

            f.getBody().accept(this);
        }
        return null;
    }


    @Override
    public Object visit(GEOp g) {
        out.println(getIndent() + "<GEOp>");
        increaseIndent();
        out.println(getIndent() + "<leftVal>");
        g.getLeft().accept(this);
        out.println(getIndent() + "</leftVal>");
        out.println(getIndent() + "<rightVal>");
        g.getRight().accept(this);
        out.println(getIndent() + "</rightVal>");
        decreaseIndent();
        out.println(getIndent() + "</GEOp>");
        return null;
    }

    @Override
    public Object visit(GTOp g) {
        out.println(getIndent() + "<GTOp>");
        increaseIndent();
        out.println(getIndent() + "<leftVal>");
        g.getLeft().accept(this);
        out.println(getIndent() + "</leftVal>");
        out.println(getIndent() + "<rightVal>");
        g.getRight().accept(this);
        out.println(getIndent() + "</rightVal>");
        decreaseIndent();
        out.println(getIndent() + "</GTOp>");
        return null;
    }

    @Override
    public Object visit(Identifier i) {
        Qualifier q = i.getQualifier();
        String qualifierString = "";
        if (q != null) {
            qualifierString += " qualifier='" + q + "'";
        }
        out.println(getIndent() + "<Identifier" + qualifierString + ">");
        increaseIndent();
        out.println(getIndent() + i.getName());
        decreaseIndent();
        out.println(getIndent() + "</Identifier>");
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
        out.println(getIndent() + "<Integer_const>");
        increaseIndent();
        out.println(getIndent() + i.getValue());
        decreaseIndent();
        out.println(getIndent() + "</Integer_const>");
        return null;
    }

    @Override
    public Object visit(LEOp l) {
        out.println(getIndent() + "<LEOp>");
        increaseIndent();
        out.println(getIndent() + "<leftVal>");
        l.getLeft().accept(this);
        out.println(getIndent() + "</leftVal>");
        out.println(getIndent() + "<rightVal>");
        l.getRight().accept(this);
        out.println(getIndent() + "</rightVal>");
        decreaseIndent();
        out.println(getIndent() + "</LEOp>");
        return null;
    }

    @Override
    public Object visit(LTOp l) {
        out.println(getIndent() + "<LTOp>");
        increaseIndent();
        out.println(getIndent() + "<leftVal>");
        l.getLeft().accept(this);
        out.println(getIndent() + "</leftVal>");
        out.println(getIndent() + "<rightVal>");
        l.getRight().accept(this);
        out.println(getIndent() + "</rightVal>");
        decreaseIndent();
        out.println(getIndent() + "</LTOp>");
        return null;
    }


    @Override
    public Object visit(MulOp m) {
        out.println(getIndent() + "<MulOp>");
        increaseIndent();
        out.println(getIndent() + "<leftVal>");
        m.getLeft().accept(this);
        out.println(getIndent() + "</leftVal>");
        out.println(getIndent() + "<rightVal>");
        m.getRight().accept(this);
        out.println(getIndent() + "</rightVal>");
        decreaseIndent();
        out.println(getIndent() + "</MulOp>");
        return null;
    }

    @Override
    public Object visit(NEOp n) {
        out.println(getIndent() + "<NEOp>");
        increaseIndent();
        out.println(getIndent() + "<leftVal>");
        n.getLeft().accept(this);
        out.println(getIndent() + "</leftVal>");
        out.println(getIndent() + "<rightVal>");
        n.getRight().accept(this);
        out.println(getIndent() + "</rightVal>");
        decreaseIndent();
        out.println(getIndent() + "</NEOp>");
        return null;
    }

    @Override
    public Object visit(NotOp n) {
        out.println(getIndent() + "<NotOp>");
        increaseIndent();
        out.println(getIndent() + "<value>");
        n.getValue().accept(this);
        out.println(getIndent() + "</value>");
        decreaseIndent();
        out.println(getIndent() + "</NotOp>");
        return null;
    }

    @Override
    public Object visit(OrOp o) {
        out.println(getIndent() + "<OrOp>");
        increaseIndent();
        out.println(getIndent() + "<leftVal>");
        o.getLeft().accept(this);
        out.println(getIndent() + "</leftVal>");
        out.println(getIndent() + "<rightVal>");
        o.getRight().accept(this);
        out.println(getIndent() + "</rightVal>");
        decreaseIndent();
        out.println(getIndent() + "</OrOp>");
        return null;
    }

    @Override
    public Object visit(ProcFunParamOp p){

        p.getIdentifier().accept(this);

        SymbolTable currentTable = scopes.peek();
        if(currentTable.lookup(p.getIdentifier().getName())!=null){
            System.err.println("Errore di dichiarazione multipla : "+ p.getIdentifier().getName());
            System.exit(1);
        }else{
            Symbol s = new Symbol(p.getIdentifier().getName(), Kind.VAR);
            try {
                currentTable.addEntry(s);
            }catch(Exception e){
                System.err.println(e.getMessage());
            }
        }
        Type type = p.getType();

        return null;
    }

    @Override
    public Object visit(ProcedureOp p) {
        p.getIdentifier().accept(this);
        ArrayList<ProcFunParamOp> paramOps = p.getProcFunParamOpList();
        for (ProcFunParamOp pr : paramOps) {
            pr.accept(this);
        }

        SymbolTable father = scopes.peek();

        if(father.lookup(p.getIdentifier().getName())!=null){
            System.err.println("Errore di dichiarazione multipla : "+ p.getIdentifier().getName());
            System.exit(1);
        }else {
            SymbolTable function = new SymbolTable(p.getIdentifier().getName(), father, ScopeType.PROCEDURE);
            scopes.push(function);
            p.getBody().accept(this);
        }

        return null;
    }

    @Override
    public Object visit(ProgramOp p) {
        SymbolTable global = new SymbolTable("global",ScopeType.GLOBAL);
        scopes.push(global);

        ArrayList<VarDeclInterface> varDeclList = p.getVarDeclList();
        for (VarDeclInterface v : varDeclList) {
            if (v instanceof VarDeclOp)
                visit((VarDeclOp) v);
            else if (v instanceof AssignStatement)
                visit((AssignStatement) v);
        }
        ArrayList<? extends FunctionOrProcedure> paramOps = p.getFunProcList();
        for (FunctionOrProcedure n : paramOps) {
            n.accept(this);
        }

        return null;
    }

    @Override
    public Object visit(ReadStatement r) {
        out.println(getIndent() + "<ReadStatement>");
        increaseIndent();
        ArrayList<Expression> expressions = r.getExpressions();
        for (Expression v : expressions) {
            v.accept(this);
        }
        decreaseIndent();
        out.println(getIndent() + "</ReadStatement>");
        return null;
    }

    @Override
    public Object visit(Real_const r) {
        out.println(getIndent() + "<Real_const>");
        increaseIndent();
        out.println(getIndent() + r.getValue());
        decreaseIndent();
        out.println(getIndent() + "</Real_const>");
        return null;
    }

    @Override
    public Object visit(ReturnStatement r) {
        out.println(getIndent() + "<ReturnStatement>");
        increaseIndent();
        out.println(getIndent() + "<Values>");
        ArrayList<Expression> expressions = r.getExpressions();
        for(Expression e : expressions) {
            e.accept(this);
        }
        out.println(getIndent() + "</Values>");
        decreaseIndent();
        out.println(getIndent() + "</ReturnStatement>");
        return null;
    }

    @Override
    public Object visit(String_const s) {
        out.println(getIndent() + "<String_const>");
        increaseIndent();
        out.println(getIndent() + s.getValue());
        decreaseIndent();
        out.println(getIndent() + "</String_const>");
        return null;
    }

    @Override
    public Object visit(True_const t) {
        out.println(getIndent() + "<True_const>");
        increaseIndent();
        out.println(getIndent() + t.getValue());
        decreaseIndent();
        out.println(getIndent() + "</True_const>");
        return null;
    }

    @Override
    public Object visit(UminusOp u) {
        out.println(getIndent() + "<UminusOp>");
        increaseIndent();
        out.println(getIndent() + "<Value>");
        u.getValue().accept(this);
        out.println(getIndent() + "</Value>");
        decreaseIndent();
        out.println(getIndent() + "</UminusOp>");
        return null;
    }

    @Override
    public Object visit(VarDeclOp v) {
        out.println(getIndent() + "<VarDeclOp>");
        increaseIndent();
        ArrayList<Expression> Expressions = v.getExpressionList();
        for(Expression i : Expressions) {
            i.accept(this);
        }
        Type type = v.getType();
        out.println(getIndent() + "<Type>");
        out.println(getIndent() + type);
        out.println(getIndent() + "</Type>");
        decreaseIndent();
        out.println(getIndent() + "</VarDeclOp>");
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
        out.println(getIndent() + "<WriteStatement>");
        increaseIndent();
        WritingType writingType = w.getWritingType();
        out.println(getIndent() + "<WritingType>");
        out.println(getIndent() + writingType);
        out.println(getIndent() + "</WritingType>");
        ArrayList<Expression> expressions = w.getExpressions();
        for(Expression e : expressions) {
            e.accept(this);
        }
        decreaseIndent();
        out.println(getIndent() + "</WriteStatement>");
        return null;
    }

    @Override
    public Object visit(ElseOp e) {
        out.println(getIndent() + "<ElseOp>");
        increaseIndent();
        e.getBody().accept(this);
        decreaseIndent();
        out.println(getIndent() + "</ElseOp>");
        return null;
    }


    @Override
    public Object visit(IterOp i) {
        out.println(getIndent() + "<IterOp>");
        increaseIndent();
        ArrayList<VarDeclInterface> varDeclList = i.getVarDeclList();
        for(VarDeclInterface v : varDeclList) {
            if(v instanceof VarDeclOp)
                visit((VarDeclOp) v);
            else if(v instanceof AssignStatement)
                visit((AssignStatement) v);
        }
        ArrayList<? extends FunctionOrProcedure> paramOps = i.getFunProcList();
        for(FunctionOrProcedure n : paramOps) {
            n.accept(this);
        }
        decreaseIndent();
        out.println(getIndent() + "</IterOp>");
        return null;
    }

    @Override
    public Object visit(IOArg i) {
        out.println(getIndent() + "<IOArg>");
        increaseIndent();
        if(i.isDollarSign()) {
            out.println(getIndent() + "<DollarSign/>");
        }
        i.getExpression().accept(this);
        decreaseIndent();
        out.println(getIndent() + "</IOArg>");
        return null;
    }

    public Object visit(ProcedureExpression p) {
        if(p.getIdentifier() != null) {
            out.println(getIndent() + "<ProcedureExpression reference='" + p.isReference() + "'>");
            increaseIndent();
            p.getIdentifier().accept(this);
        } else {
            out.println(getIndent() + "<ProcedureExpression>");
            increaseIndent();
        }
        if(p.getExpression() != null)
            p.getExpression().accept(this);
        decreaseIndent();
        out.println(getIndent() + "</ProcedureExpression>");
        return null;
    }

    @Override
    public Object visit(ProcCallOp p) {
        out.println(getIndent() + "<ProcCallOp>");
        increaseIndent();
        ArrayList<ProcedureExpression> Expressions = p.getProcedureExpressions();
        for(ProcedureExpression i : Expressions) {
            i.accept(this);
        }
        p.getIdentifier().accept(this);
        decreaseIndent();
        out.println(getIndent() + "</ProcCallOp>");
        return null;
    }

}
