package visitors;
import tree_structure.*;
import tree_structure.Expression.*;
import tree_structure.Expression.Expression;

import tree_structure.Statement.*;
import tree_structure.Statement.Statement;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class PrintXMLTreeVisitor implements Visitor {

    PrintWriter out;
    private int indentLevel = 0;

    public PrintXMLTreeVisitor(String filename) throws FileNotFoundException {
        out = new PrintWriter(filename+".xml");
    }

    public void dispose(){
        out.close();
    }
    private void increaseIndent() {
        indentLevel++;
    }

    private void decreaseIndent() {
        if (indentLevel > 0) {
            indentLevel--;
        }
    }

    private String getIndent() {
        return "    ".repeat(indentLevel);
    }

    // Implementazioni dei metodi visit con indentazione

    @Override
    public Object visit(AddOp a) {
        out.println(getIndent() + "<AddOp>");
        increaseIndent();
        out.println(getIndent() + "<leftVal>");
        increaseIndent();
        a.getLeft().accept(this);
        decreaseIndent();
        out.println(getIndent() + "</leftVal>");
        out.println(getIndent() + "<rightVal>");
        increaseIndent();
        a.getRight().accept(this);
        decreaseIndent();
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
        increaseIndent();
        a.getLeft().accept(this);
        decreaseIndent();
        out.println(getIndent() + "</leftVal>");
        out.println(getIndent() + "<rightVal>");
        increaseIndent();
        a.getRight().accept(this);
        decreaseIndent();
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
        out.println(getIndent() + "<BodyOp>");
        increaseIndent();
        ArrayList<VarDeclOp> varDeclList = b.getVarDeclList();
        for(VarDeclOp v : varDeclList){
            v.accept(this);
        }
        ArrayList<Statement> statements = b.getStatementList();
        for(Statement s : statements){
            s.accept(this);
        }
        decreaseIndent();
        out.println(getIndent() + "</BodyOp>");
        return null;
    }

    @Override
    public Object visit(DiffOp d) {
        out.println(getIndent() + "<DiffOp>");
        increaseIndent();
        out.println(getIndent() + "<leftVal>");
        increaseIndent();
        d.getLeft().accept(this);
        decreaseIndent();
        out.println(getIndent() + "</leftVal>");
        out.println(getIndent() + "<rightVal>");
        increaseIndent();
        d.getRight().accept(this);
        decreaseIndent();
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
        increaseIndent();
        d.getLeft().accept(this);
        decreaseIndent();
        out.println(getIndent() + "</leftVal>");
        out.println(getIndent() + "<rightVal>");
        increaseIndent();
        d.getRight().accept(this);
        decreaseIndent();
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
        increaseIndent();
        e.getLeft().accept(this);
        decreaseIndent();
        out.println(getIndent() + "</leftVal>");
        out.println(getIndent() + "<rightVal>");
        increaseIndent();
        e.getRight().accept(this);
        decreaseIndent();
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
        increaseIndent();
        out.println(getIndent() + "<Parameters>");
        for(Expression e : expressions){
            e.accept(this);
        }
        decreaseIndent();
        out.println(getIndent() + "</Parameters>");
        decreaseIndent();
        out.println(getIndent() + "</FunCallOp>");
        return null;
    }

    @Override
    public Object visit(FunctionOp f) {
        out.println(getIndent() + "<FunctionOp>");
        increaseIndent();
        f.getIdentifier().accept(this);
        ArrayList<ProcFunParamOp> paramOps = f.getProcFunParamOpList();
        for(ProcFunParamOp p : paramOps){
            p.accept(this);
        }
        out.println(getIndent() + "<ReturnTypes>");
        ArrayList<Type> types = f.getReturnTypes();
        for(Type t : types){
            increaseIndent();
            out.println(getIndent() + t);
            decreaseIndent();
        }
        out.println(getIndent() + "</ReturnTypes>");
        f.getBody().accept(this);
        decreaseIndent();
        out.println(getIndent() + "</FunctionOp>");
        return null;
    }


    @Override
    public Object visit(GEOp g) {
        out.println(getIndent() + "<GEOp>");
        increaseIndent();
        out.println(getIndent() + "<leftVal>");
        increaseIndent();
        g.getLeft().accept(this);
        decreaseIndent();
        out.println(getIndent() + "</leftVal>");
        out.println(getIndent() + "<rightVal>");
        increaseIndent();
        g.getRight().accept(this);
        decreaseIndent();
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
        increaseIndent();
        g.getLeft().accept(this);
        decreaseIndent();
        out.println(getIndent() + "</leftVal>");
        out.println(getIndent() + "<rightVal>");
        increaseIndent();
        g.getRight().accept(this);
        decreaseIndent();
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
        out.println(getIndent() + "<IfStatement>");
        increaseIndent();
        i.getExpression().accept(this);
        i.getBody().accept(this);
        ArrayList<ElifOp> elifOps = i.getElifList();
        for (ElifOp p : elifOps) {
            p.accept(this);
        }
        i.getElseBody().accept(this);
        decreaseIndent();
        out.println(getIndent() + "</IfStatement>");
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
        increaseIndent();
        l.getLeft().accept(this);
        decreaseIndent();
        out.println(getIndent() + "</leftVal>");
        out.println(getIndent() + "<rightVal>");
        increaseIndent();
        l.getRight().accept(this);
        decreaseIndent();
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
        increaseIndent();
        l.getLeft().accept(this);
        decreaseIndent();
        out.println(getIndent() + "</leftVal>");
        out.println(getIndent() + "<rightVal>");
        increaseIndent();
        l.getRight().accept(this);
        decreaseIndent();
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
        increaseIndent();
        m.getLeft().accept(this);
        decreaseIndent();
        out.println(getIndent() + "</leftVal>");
        out.println(getIndent() + "<rightVal>");
        increaseIndent();
        m.getRight().accept(this);
        decreaseIndent();
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
        increaseIndent();
        n.getLeft().accept(this);
        decreaseIndent();
        out.println(getIndent() + "</leftVal>");
        out.println(getIndent() + "<rightVal>");
        increaseIndent();
        n.getRight().accept(this);
        decreaseIndent();
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
        increaseIndent();
        o.getLeft().accept(this);
        decreaseIndent();
        out.println(getIndent() + "</leftVal>");
        out.println(getIndent() + "<rightVal>");
        increaseIndent();
        o.getRight().accept(this);
        decreaseIndent();
        out.println(getIndent() + "</rightVal>");
        decreaseIndent();
        out.println(getIndent() + "</OrOp>");
        return null;
    }

    @Override
    public Object visit(ProcFunParamOp p) {
        out.println(getIndent() + "<ProcFunParamOp>");
        increaseIndent();
        p.getIdentifier().accept(this);
        Type type = p.getType();
        out.println(getIndent() + "<Type>");
        increaseIndent();
        out.println(getIndent() + type);
        decreaseIndent();
        out.println(getIndent() + "</Type>");
        decreaseIndent();
        out.println(getIndent() + "</ProcFunParamOp>");
        return null;
    }

    @Override
    public Object visit(ProcedureOp p) {
        out.println(getIndent() + "<ProcedureOp>");
        increaseIndent();
        p.getIdentifier().accept(this);
        ArrayList<ProcFunParamOp> paramOps = p.getProcFunParamOpList();
        for (ProcFunParamOp pr : paramOps) {
            pr.accept(this);
        }
        p.getBody().accept(this);
        decreaseIndent();
        out.println(getIndent() + "</ProcedureOp>");
        return null;
    }

    @Override
    public Object visit(ProgramOp p) {
        out.println(getIndent() + "<ProgramOp>");
        increaseIndent();
        ArrayList<VarDeclOp> varDeclList = p.getVarDeclList();
        for (VarDeclOp v : varDeclList) {
            v.accept(this);
        }
        ArrayList<? extends FunctionOrProcedure> paramOps = p.getFunProcList();
        for (FunctionOrProcedure n : paramOps) {
            n.accept(this);
        }
        decreaseIndent();
        out.println(getIndent() + "</ProgramOp>");
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
        ArrayList<Expression> expressions = r.getExpressions();
        for(Expression e : expressions) {
            e.accept(this);
        }
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
        ArrayList<IdentifierExpression> identifierExpressionList = v.getIdentifierExpressionsList();
        for(IdentifierExpression ie : identifierExpressionList) {
            ie.accept(this);
        }
        Type type = v.getType();
        if(type!=null) {
            out.println(getIndent() + "<Type>");
            increaseIndent();
            out.println(getIndent() + type);
            decreaseIndent();
            out.println(getIndent() + "</Type>");
        }
        DeclarationType dt = v.getDeclarationType();
        out.println(getIndent() + "<DeclarationType>");
        increaseIndent();
        out.println(getIndent() + dt);
        decreaseIndent();
        out.println(getIndent() + "</DeclarationType>");
        decreaseIndent();
        out.println(getIndent() + "</VarDeclOp>");
        return null;
    }

    @Override
    public Object visit(WhileStatement w) {
        out.println(getIndent() + "<WhileStatement>");
        increaseIndent();
        w.getExpression().accept(this);
        w.getBody().accept(this);
        decreaseIndent();
        out.println(getIndent() + "</WhileStatement>");
        return null;
    }

    @Override
    public Object visit(WriteStatement w) {
        out.println(getIndent() + "<WriteStatement>");
        increaseIndent();
        WritingType writingType = w.getWritingType();
        out.println(getIndent() + "<WritingType>");
        increaseIndent();
        out.println(getIndent() + writingType);
        decreaseIndent();
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
        ArrayList<VarDeclOp> varDeclList = i.getVarDeclList();
        for(VarDeclOp v : varDeclList) {
            v.accept(this);
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
    public Object visit(IdentifierExpression ie) {
        out.println(getIndent() + "<IdentifierExpression>");
        increaseIndent();
        if(ie.getIdentifier()!=null){
            ie.getIdentifier().accept(this);
        }
        if(ie.getExpression()!=null){
            ie.getExpression().accept(this);
        }

        decreaseIndent();
        out.println(getIndent() + "</IdentifierExpression>");
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
        p.getIdentifier().accept(this);
        ArrayList<ProcedureExpression> Expressions = p.getProcedureExpressions();
        for(ProcedureExpression i : Expressions) {
            i.accept(this);
        }
        decreaseIndent();
        out.println(getIndent() + "</ProcCallOp>");
        return null;
    }

}
