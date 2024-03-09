package visitors;

import scoping.ExpressionType;
import tree_structure.*;
import tree_structure.Expression.*;
import tree_structure.Statement.*;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Toy2ToCVisitor implements Visitor{

    public Toy2ToCVisitor(){
    }

    public String writeHeaders(){
        String result = "";
        result += "#include<stdio.h>\n";
        result += "#include<stdlib.h>\n";
        result += "#include<string.h>\n";
        return result;
    }

    @Override
    public Object visit(AddOp a) {
        String result = "";

        if(a.getType()==Type.STRING)
            result += "\"";
        result += a.getLeft().accept(this);

        //caso concatenazione fra stringhe
        if(a.getType()!=Type.STRING)
            result += " + ";

        result += a.getRight().accept(this);

        if(a.getType()==Type.STRING)
            result += "\"";

        return result;
    }

    @Override
    public Object visit(AndOp a) {
        String result = "";

        result += a.getLeft().accept(this);

        result += " && ";

        result += a.getRight().accept(this);

        return result;
    }

    @Override
    public Object visit(AssignStatement a) {
        String result = "";

        //TODO controllare tipi
        ArrayList<Identifier> identifiers = a.getIdentifiers();
        for(int i = 0 ;i < identifiers.size(); i++){
            result += identifiers.get(i).accept(this);
            if(i+1< identifiers.size())
                result += ",";
        }

        result += " = ";

        ArrayList<Expression> expressions = a.getExpressions();
        for(Expression e : expressions){
            result += e.accept(this);
        }

        result +=";";

        return result;
    }

    @Override
    public Object visit(BodyOp b) {
        String result = "";

        ArrayList<VarDeclOp> varDeclList = b.getVarDeclList();
        for(VarDeclOp v : varDeclList){
            result += v.accept(this);
            result += "\n";
        }
        ArrayList<Statement> statements = b.getStatementList();
        for(Statement s : statements){
            result += s.accept(this);
            result += "\n";
        }

        return result;
    }

    @Override
    public Object visit(DiffOp d) {
        String result = "";

        result += d.getLeft().accept(this);

        result += " - ";

        result += d.getRight().accept(this);

        return result;
    }

    @Override
    public Object visit(DivOp d) {
        String result = "";

        result += d.getLeft().accept(this);
        result += " / ";
        result += d.getRight().accept(this);
        return result;
    }

    @Override
    public Object visit(ElifOp e) {
        String result = "";

        result +="else if ( ";

        result += e.getExpression().accept(this);

        result += " ){\n";
        result += e.getBody().accept(this);
        result +="}\n";

        return result;
    }

    @Override
    public Object visit(EQOp e) {
        String result = "";

        //TODO controllare tipi
        result += e.getLeft().accept(this);

        result += " == ";

        result += e.getRight().accept(this);

        return result;
    }

    @Override
    public Object visit(False_const f) {
        String result = "";

        result += "false";

        return result;
    }

    @Override
    public Object visit(FunCallOp f) {
        String result = "";

        result += f.getIdentifier().accept(this);
        result +=" ( ";

        ArrayList<Expression> arguments = f.getExpressions();

        for(int i = 0 ;i< arguments.size(); i++){
            result += arguments.get(i).accept(this);
            if(i+1< arguments.size())
                result += ",";
        }

        result += " ) ";

        return result;
    }

    @Override
    public Object visit(FunctionOp f) {
        String result = "";

        //TODO gestire più tipi di ritorno...
        for(Type t : f.getReturnTypes()){
            result += typeConverter(t)+" ";
        }

        result += f.getIdentifier().accept(this);

        result += " ( ";

        ArrayList<ProcFunParamOp> parameters = f.getProcFunParamOpList();

        for(int i = 0 ;i< parameters.size(); i++){
            result += parameters.get(i).accept(this);
            if(i+1< parameters.size())
                result += ",";
        }
        result +=" ){\n";


        result += f.getBody().accept(this);

        result += " }\n";

        return result;
    }


    @Override
    public Object visit(GEOp g) {
        String result = "";
        //TODO controlli di tipo se stringa es strcmp
        result += g.getLeft().accept(this);

        result += " >= ";

        result += g.getRight().accept(this);

        return result;
    }

    @Override
    public Object visit(GTOp g) {
        String result = "";
        //TODO controlli di tipo se stringa es strcmp
        result += g.getLeft().accept(this);

        result += " > ";

        result += g.getRight().accept(this);

        return result;
    }

    @Override
    public Object visit(Identifier i) {
        String result = "";
        Qualifier q = i.getQualifier();

        if(q!=null){
            //TODO passaggio per riferimento
            if(q == Qualifier.OUT)
                result += "*";
        }

        result += i.getName();

        return result;
    }

    @Override
    public Object visit(IfStatement i) {
        String result = "";

        result += "if ( ";

        result += i.getExpression().accept(this);
        result += " ){\n";
        result += i.getBody().accept(this);

        result +=" }\n";

        ArrayList<ElifOp> elifOps = i.getElifList();
        for (ElifOp p : elifOps) {
            result += p.accept(this);
        }
        if(i.getElseBody() != null)
            result += i.getElseBody().accept(this);

        return result;
    }

    @Override
    public Object visit(Integer_const i) {
        String result = "";

        result += i.getValue();

        return result;
    }

    @Override
    public Object visit(LEOp l) {
        String result = "";
        //TODO controlli di tipo se stringa es strcmp
        result += l.getLeft().accept(this);

        result += " <= ";

        result +=l.getRight().accept(this);

        return result;
    }

    @Override
    public Object visit(LTOp l) {
        String result = "";
        //TODO controlli di tipo se stringa es strcmp
        result += l.getLeft().accept(this);

        result +=" < ";

        result += l.getRight().accept(this);

        return result;
    }


    @Override
    public Object visit(MulOp m) {
        String result = "";

        result += m.getLeft().accept(this);

        result += " * ";

        result += m.getRight().accept(this);

        return result;
    }

    @Override
    public Object visit(NEOp n) {
        String result = "";
        //TODO controlli di tipo se stringa es strcmp
        result += n.getLeft().accept(this);

        result += " != ";

        result +=n.getRight().accept(this);

        return result;
    }

    @Override
    public Object visit(NotOp n) {
        String result = "";
        result += "!";
        result += n.getValue().accept(this);

        return result;
    }

    @Override
    public Object visit(OrOp o) {
        String result = "";

        result += o.getLeft().accept(this);

        result += " || ";

        result += o.getRight().accept(this);

        return result;
    }

    @Override
    public Object visit(ProcFunParamOp p){
        String result = "";

        result += p.getIdentifier().accept(this);

        return result;
    }

    @Override
    public Object visit(ProcedureOp p) {
        String result = "";

        result += "void ";

        result += p.getIdentifier().accept(this);

        result +=" ( ";

        ArrayList<ProcFunParamOp> parameters = p.getProcFunParamOpList();

        for(int i = 0 ;i< parameters.size(); i++){
            result += parameters.get(i).accept(this);
            if(i+1< parameters.size())
                result += ",";
        }
        result += " ){\n";

        result += p.getBody().accept(this);

        result +=" }\n";

        return result;
    }

    @Override
    public Object visit(ProgramOp p) {
        String result = "";

        result += writeHeaders();

        ArrayList<VarDeclOp> varDeclList = p.getVarDeclList();
        for (VarDeclOp v : varDeclList) {
            result += v.accept(this);
        }

        //

        ArrayList<? extends FunctionOrProcedure> paramOps = p.getFunProcList();
        for (FunctionOrProcedure n : paramOps) {
            result += n.accept(this);
        }

        return result;
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
        String result = "";

        result += "scanf(\"";

        String toPrint = "printf(\"";

        ArrayList<Expression> expressions = r.getExpressions();

        int flag = 0;

        for (int i= 0; i<expressions.size();i++) {
            IOArg ioArg = (IOArg)expressions.get(i);
            Type ioArgExpressionType = ioArg.getExpression().getType();
            if(ioArg.isDollarSign()) {
                flag = 1;
                result += getPrintfScanfType(ioArgExpressionType);
            }else{
                //solo stampa
                toPrint += ioArg.accept(this);
            }

        }

        toPrint += "\");\n";
        if(flag==1){
            result += "\",";
            for (int i= 0; i<expressions.size();i++) {
                IOArg ioArg = (IOArg)expressions.get(i);
                if(ioArg.isDollarSign()) {
                    result += "&" + ioArg.getExpression().accept(this);

                    for(int j = i+1; j<expressions.size();j++)
                    {//skippo tutte le eventuali print per vedere se ci sono altre espressioni per cui fare scanf
                        if(((IOArg)expressions.get(j)).isDollarSign())
                        {
                            result+=",";
                            break;
                        }
                    }
                }
            }
        }else if(flag==0){
            result += "\"";
        }
        result += ");\n";
        toPrint += result;

        return toPrint;
    }

    @Override
    public Object visit(Real_const r) {
        String result = "";

        result += r.getValue();

        return result;
    }

    @Override
    public Object visit(ReturnStatement r) {
        String result = "";

        result += "return ";
        ArrayList<Expression> expressions = r.getExpressions();
        for(int i=0;i<expressions.size();i++) {
            result += expressions.get(i).accept(this);
            if(i+1< expressions.size())
                result += ",";
        }

        result += ";\n";

        return result;
    }

    @Override
    public Object visit(String_const s) {
        String result = "";

        result += s.getValue();

        return result;
    }

    @Override
    public Object visit(True_const t) {
        String result = "";

        result += "true";

        return result;
    }

    @Override
    public Object visit(UminusOp u) {
        String result = "";

        result += " -";

        result += u.getValue().accept(this);

        return result;
    }

    @Override
    public Object visit(VarDeclOp v) {
        String result = "";
        ArrayList<IdentifierExpression> identifierExpressionList = v.getIdentifierExpressionsList();
        Type lastTypeDeclared = Type.NOTYPE;
        for(int i=0;i<identifierExpressionList.size();i++) {
            IdentifierExpression ie = identifierExpressionList.get(i);
            if(lastTypeDeclared != ie.getIdentifier().getType()) {
                //In C non si deve avere: double a,double b,double risultato;
                result += typeConverter(ie.getIdentifier().getType()) + " ";
                lastTypeDeclared = ie.getIdentifier().getType();
            }

            if(ie.getIdentifier()!=null){
                result += ie.getIdentifier().accept(this);
            }

            if(v.getDeclarationType()==DeclarationType.INITIALIZATION)
                result += " = ";

            if(ie.getExpression()!=null){
                result += ie.getExpression().accept(this);
            }

            if(i+1 < identifierExpressionList.size())
                result += ",";
        }
        result += ";";

        return result;
    }

    @Override
    public Object visit(WhileStatement w) {
        String result = "";

        result += "while(";

        result += w.getExpression().accept(this);

        result += "){\n";

        result += w.getBody().accept(this);

        result +="}\n";

        return result;
    }


    /*
    * --> "La somma finale è" $(a+b) "e va bene così";
        % istruzione di stampa in output, equivalente a printf("La
        somma finale è %d e va bene così", a+b) %
    *
    * */
    @Override
    public Object visit(WriteStatement w) {
        String result = "";
        //TODO concatenazione stringhe

        WritingType writingType = w.getWritingType();

        result += "printf(\"";

        ArrayList<Expression> expressions = w.getExpressions();

        ArrayList<Expression> printfArguments = new ArrayList<>();
        for(int i= 0; i<expressions.size();i++) {

            IOArg ioArg = (IOArg)expressions.get(i);
            Type ioArgExpressionType = ioArg.getExpression().getType();

            //TODO booleano?
            if(ioArg.isDollarSign()){
                result += getPrintfScanfType(ioArgExpressionType);
                printfArguments.add(ioArg);
            }
            else if(ioArgExpressionType==Type.STRING){
                if(ioArg.getExpression().getExpressionType() == ExpressionType.PLUS){
                    //è una concatenazione tra stringhe, non devo inserire gli apici, quindi faccio manualmente la visita
                    result += ((AddOp)ioArg.getExpression()).getLeft().accept(this);
                    result += ((AddOp)ioArg.getExpression()).getRight().accept(this);
                }
                else
                    result += ioArg.accept(this);
            }

        }
        if(writingType == WritingType.WRITE_RETURN)
            result += "\\n\"";
        else
            result += "\"";

        if(!printfArguments.isEmpty())
            result += ",";
        for(int i = 0; i<printfArguments.size();i++){
            result += printfArguments.get(i).accept(this);
            if(i+1<printfArguments.size())
                result += ",";
        }

        result += ");";

        return result;
    }

    @Override
    public Object visit(ElseOp e) {
        String result = "";

        result += " else{\n";

        result += e.getBody().accept(this);

        result +=" }\n";

        return result;
    }


    @Override
    public Object visit(IterOp i) {
        //Utilizzato solo per costruire l'albero nel parser
        return null;
    }

    @Override
    public Object visit(IdentifierExpression ie) {
        String result = "";

        //Bypassata: ho bisogno di sapere se è una dichiarazione o inizializzazione per mettere il simbolo " = ",
        //quindi li vado ad esaminare in VarDeclOp perchè li ho getDeclarationType

        if(ie.getIdentifier()!=null){
            result += ie.getIdentifier().accept(this);
        }
        if(ie.getExpression()!=null){
            result += ie.getExpression().accept(this);
        }

        return result;
    }

    @Override
    public Object visit(IOArg i) {
        String result = "";
        result += i.getExpression().accept(this);
        return result;
    }

    public Object visit(ProcedureExpression p) {
        String result = "";
        //TODO controlli per riferimento

        if(p.getIdentifier() != null) {
            if(p.isReference())
                result += "&";
            result += p.getIdentifier().accept(this);
        }
        if(p.getExpression() != null)
            result += p.getExpression().accept(this);

        return result;
    }

    @Override
    public Object visit(ProcCallOp p) {
        String result = "";

        result += p.getIdentifier().accept(this);

        result += " ( ";

        ArrayList<ProcedureExpression> arguments = p.getProcedureExpressions();

        for(int i = 0 ;i< arguments.size(); i++){
            result += arguments.get(i).accept(this);
            if(i+1< arguments.size())
                result += ",";
        }

        result += " );";

        return result;
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

    private static String getPrintfScanfType(Type ioArgExpressionType) {
        String result = "";
        switch(ioArgExpressionType) {
            case INTEGER -> result += " %d ";
            case BOOL -> result += " %d "; //TODO bool?
            case REAL -> result += " %f ";
            case STRING -> result += " %s ";
        }
        return result;
    }

}
