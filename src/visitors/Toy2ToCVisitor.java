package visitors;

import scoping.ExpressionType;
import tree_structure.*;
import tree_structure.Expression.*;
import tree_structure.Statement.*;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Toy2ToCVisitor implements Visitor{

    PrintWriter out;

    public Toy2ToCVisitor(String fileName)throws FileNotFoundException {
        out = new PrintWriter(fileName);
        writeHeaders();
    }

    public void dispose(){
        out.close();
    }

    public void writeHeaders(){
        out.println("#include<stdio.h>");
        out.println("#include<stdlib.h>");
        out.println("#include<string.h>");
    }

    @Override
    public Object visit(AddOp a) {

        if(a.getType()==Type.STRING)
            out.print("\"");
        a.getLeft().accept(this);

        //caso concatenazione fra stringhe
        if(a.getType()!=Type.STRING)
            out.print(" + ");

        a.getRight().accept(this);

        if(a.getType()==Type.STRING)
            out.print("\"");

        return null;
    }

    @Override
    public Object visit(AndOp a) {
        a.getLeft().accept(this);

        out.print(" && ");

        a.getRight().accept(this);

        return null;
    }

    @Override
    public Object visit(AssignStatement a) {

        //TODO controllare tipi
        ArrayList<Identifier> identifiers = a.getIdentifiers();
        for(int i = 0 ;i < identifiers.size(); i++){
            identifiers.get(i).accept(this);
            if(i+1< identifiers.size())
                out.print(",");
        }

        out.print(" = ");

        ArrayList<Expression> expressions = a.getExpressions();
        for(Expression e : expressions){
            e.accept(this);
        }

        out.print(";");

        return null;
    }

    @Override
    public Object visit(BodyOp b) {

        ArrayList<VarDeclOp> varDeclList = b.getVarDeclList();
        for(VarDeclOp v : varDeclList){
            v.accept(this);
            out.println();
        }
        ArrayList<Statement> statements = b.getStatementList();
        for(Statement s : statements){
            s.accept(this);
            out.println();
        }

        return null;
    }

    @Override
    public Object visit(DiffOp d) {
        d.getLeft().accept(this);

        out.print(" - ");

        d.getRight().accept(this);

        return null;
    }

    @Override
    public Object visit(DivOp d) {
        d.getLeft().accept(this);
        out.print(" / ");
        d.getRight().accept(this);
        return null;
    }

    @Override
    public Object visit(ElifOp e) {

        out.print("else if ( ");

        e.getExpression().accept(this);

        out.println(" ){");
        e.getBody().accept(this);
        out.println("}");

        return null;
    }

    @Override
    public Object visit(EQOp e) {

        //TODO controllare tipi
        e.getLeft().accept(this);

        out.print(" == ");

        e.getRight().accept(this);

        return null;
    }

    @Override
    public Object visit(False_const f) {

        out.print("false");

        return null;
    }

    @Override
    public Object visit(FunCallOp f) {

        f.getIdentifier().accept(this);
        out.print(" ( ");

        ArrayList<Expression> arguments = f.getExpressions();

        for(int i = 0 ;i< arguments.size(); i++){
            arguments.get(i).accept(this);
            if(i+1< arguments.size())
                out.write(",");
        }

        out.print(" ) ");

        return null;
    }

    @Override
    public Object visit(FunctionOp f) {
        //TODO gestire più tipi di ritorno...
        for(Type t : f.getReturnTypes()){
            out.print(typeConverter(t)+" ");
        }

        f.getIdentifier().accept(this);

        out.print(" ( ");

        ArrayList<ProcFunParamOp> parameters = f.getProcFunParamOpList();

        for(int i = 0 ;i< parameters.size(); i++){
            parameters.get(i).accept(this);
            if(i+1< parameters.size())
                out.write(",");
        }
        out.println(" ){");


        f.getBody().accept(this);

        out.println(" } ");

        return null;
    }


    @Override
    public Object visit(GEOp g) {
        //TODO controlli di tipo se stringa es strcmp
        g.getLeft().accept(this);

        out.print(" >= ");

        g.getRight().accept(this);

        return null;
    }

    @Override
    public Object visit(GTOp g) {
        //TODO controlli di tipo se stringa es strcmp
        g.getLeft().accept(this);

        out.print(" > ");

        g.getRight().accept(this);

        return null;
    }

    @Override
    public Object visit(Identifier i) {
        Qualifier q = i.getQualifier();

        if(q!=null){
            //TODO passaggio per riferimento
            if(q == Qualifier.OUT)
                out.print("&");
        }

        out.print(i.getName());

        return null;
    }

    @Override
    public Object visit(IfStatement i) {

        out.print("if ( ");

        i.getExpression().accept(this);
        out.println(" ){ ");
        i.getBody().accept(this);

        out.println(" } ");

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

        out.print(i.getValue());

        return null;
    }

    @Override
    public Object visit(LEOp l) {
        //TODO controlli di tipo se stringa es strcmp
        l.getLeft().accept(this);

        out.print(" <= ");

        l.getRight().accept(this);

        return null;
    }

    @Override
    public Object visit(LTOp l) {
        //TODO controlli di tipo se stringa es strcmp
        l.getLeft().accept(this);

        out.print(" < ");

        l.getRight().accept(this);

        return null;
    }


    @Override
    public Object visit(MulOp m) {

        m.getLeft().accept(this);

        out.print(" * ");

        m.getRight().accept(this);

        return null;
    }

    @Override
    public Object visit(NEOp n) {
        //TODO controlli di tipo se stringa es strcmp
        n.getLeft().accept(this);

        out.print(" != ");

        n.getRight().accept(this);

        return null;
    }

    @Override
    public Object visit(NotOp n) {
        out.print("!");
        n.getValue().accept(this);

        return null;
    }

    @Override
    public Object visit(OrOp o) {

        o.getLeft().accept(this);

        out.print(" || ");

        o.getRight().accept(this);

        return null;
    }

    @Override
    public Object visit(ProcFunParamOp p){

        p.getIdentifier().accept(this);

        return null;
    }

    @Override
    public Object visit(ProcedureOp p) {

        out.print("void ");

        p.getIdentifier().accept(this);

        out.print(" ( ");

        ArrayList<ProcFunParamOp> parameters = p.getProcFunParamOpList();

        for(int i = 0 ;i< parameters.size(); i++){
            parameters.get(i).accept(this);
            if(i+1< parameters.size())
                out.write(",");
        }
        out.println(" ){");

        p.getBody().accept(this);

        out.println(" } ");

        return null;
    }

    @Override
    public Object visit(ProgramOp p) {

        ArrayList<VarDeclOp> varDeclList = p.getVarDeclList();
        for (VarDeclOp v : varDeclList) {
            v.accept(this);
        }
        ArrayList<? extends FunctionOrProcedure> paramOps = p.getFunProcList();
        for (FunctionOrProcedure n : paramOps) {
            n.accept(this);
        }

        return null;
    }


    /*
    * <— "Inserisci" + " un numero intero" $(a) "ed un numero reale"
        $(b);
        % è equivalente a printf("Inserisci un numero intero:\n");
        scanf ("%d", &a); printf("ed un numero reale\n"); scanf ("%f",
        &b);%
    *
    * */
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

        out.print(r.getValue());

        return null;
    }

    @Override
    public Object visit(ReturnStatement r) {

        out.print("return ");
        ArrayList<Expression> expressions = r.getExpressions();
        for(int i=0;i<expressions.size();i++) {
            expressions.get(i).accept(this);
            if(i+1< expressions.size())
                out.print(",");
        }

        out.println(";");

        return null;
    }

    @Override
    public Object visit(String_const s) {

        out.print(s.getValue());

        return null;
    }

    @Override
    public Object visit(True_const t) {

        out.print("true");

        return null;
    }

    @Override
    public Object visit(UminusOp u) {

        out.print(" -");

        u.getValue().accept(this);

        return null;
    }

    @Override
    public Object visit(VarDeclOp v) {
        ArrayList<IdentifierExpression> identifierExpressionList = v.getIdentifierExpressionsList();
        Type lastTypeDeclared = Type.NOTYPE;
        for(int i=0;i<identifierExpressionList.size();i++) {
            IdentifierExpression ie = identifierExpressionList.get(i);
            if(lastTypeDeclared != ie.getIdentifier().getType()) {
                //In C non si deve avere: double a,double b,double risultato;
                out.print(typeConverter(ie.getIdentifier().getType()) + " ");
                lastTypeDeclared = ie.getIdentifier().getType();
            }

            if(ie.getIdentifier()!=null){
                ie.getIdentifier().accept(this);
            }

            if(v.getDeclarationType()==DeclarationType.INITIALIZATION)
                out.print(" = ");

            if(ie.getExpression()!=null){
                ie.getExpression().accept(this);
            }

            if(i+1 < identifierExpressionList.size())
                out.print(",");
        }
        out.print(";");

        return null;
    }

    @Override
    public Object visit(WhileStatement w) {

        out.print("while(");

        w.getExpression().accept(this);

        out.println("){");

        w.getBody().accept(this);

        out.println("}");

        return null;
    }


    /*
    * --> "La somma finale è" $(a+b) "e va bene così";
        % istruzione di stampa in output, equivalente a printf("La
        somma finale è %d e va bene così", a+b) %
    *
    * */
    @Override
    public Object visit(WriteStatement w) {
        //TODO concatenazione stringhe

        WritingType writingType = w.getWritingType();

        out.print("printf(\"");

        ArrayList<Expression> expressions = w.getExpressions();

        ArrayList<Expression> printfArguments = new ArrayList<>();
        for(int i= 0; i<expressions.size();i++) {

            IOArg ioArg = (IOArg)expressions.get(i);
            Type ioArgExpressionType = ioArg.getExpression().getType();

            //TODO booleano?
            if(ioArg.isDollarSign()){
                switch(ioArgExpressionType) {
                    case INTEGER -> out.print(" %d ");
                    case BOOL -> {
                    }
                    case REAL -> out.print(" %f ");
                    case STRING -> out.print(" %s ");
                }
                printfArguments.add(ioArg);
            }
            else if(ioArgExpressionType==Type.STRING){
                if(ioArg.getExpression().getExpressionType() == ExpressionType.PLUS){
                    //è una concatenazione tra stringhe, non devo inserire gli apici, quindi faccio manualmente la visita
                    ((AddOp)ioArg.getExpression()).getLeft().accept(this);
                    ((AddOp)ioArg.getExpression()).getRight().accept(this);
                }
                else
                    ioArg.accept(this);
            }

        }
        if(writingType == WritingType.WRITE_RETURN)
            out.print("\\n\"");
        else
            out.print("\"");

        if(!printfArguments.isEmpty())
            out.print(",");
        for(int i = 0; i<printfArguments.size();i++){
            printfArguments.get(i).accept(this);
            if(i+1<printfArguments.size())
                out.write(",");
        }

        out.print(");");

        return null;
    }

    @Override
    public Object visit(ElseOp e) {

        out.println(" else{");

        e.getBody().accept(this);

        out.println(" } ");

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

        //Bypassata: ho bisogno di sapere se è una dichiarazione o inizializzazione per mettere il simbolo " = ",
        //quindi li vado ad esaminare in VarDeclOp perchè li ho getDeclarationType

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
        //TODO controlli per riferimento

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

        out.print(" ( ");

        ArrayList<ProcedureExpression> arguments = p.getProcedureExpressions();

        for(int i = 0 ;i< arguments.size(); i++){
            arguments.get(i).accept(this);
            if(i+1< arguments.size())
                out.write(",");
        }

        out.print(" ) ");

        return null;
    }

    //Converte un tipo del linguaggio toy2 nel corrispondente tipo C
    public String typeConverter(Type type){
        return switch (type) {
            case INTEGER -> "int";
            case BOOL -> "bool";
            case REAL -> "double";
            case STRING -> "char*";
            default -> "error";
        };
    }

}
