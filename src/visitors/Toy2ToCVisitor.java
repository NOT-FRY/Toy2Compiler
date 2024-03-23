package visitors;

import scoping.ExpressionType;
import tree_structure.*;
import tree_structure.Expression.*;
import tree_structure.Statement.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Toy2ToCVisitor implements Visitor{

    private int functionOrProcedureCount;
    private boolean mainVisited;

    //Devo settare il tipo dell'argomento (OUT), così all'interno del body della funzione, si potrà utilizzarlo come puntatore
    //nome funzione, ArrayList delle variabili passate per riferimento
    private HashMap<String,ArrayList<String>> identifiersByReference;
    private String currentProcedure;

    public Toy2ToCVisitor(){
        functionOrProcedureCount = 0;
        mainVisited = false;
        identifiersByReference = new HashMap<String,ArrayList<String>>();
        currentProcedure = "";
    }

    public String writeHeaders(){
        String result = "";
        result += "#include<stdio.h>\n";
        result += "#include<stdlib.h>\n";
        result += "#include<string.h>\n";
        result += "#include <stdbool.h>\n";
        result += "#define MAX 512\n";


        //array per consentire la restituzione di valori multipli nelle funzioni
        result += "typedef struct\n" +
                "{\n" +
                "    int* integers;\n" +
                "    double* reals;\n" +
                "    char** strings;\n" +
                "    bool* booleans;\n" +
                "    int int_count;\n"+
                "    int real_count;\n"+
                "    int string_count;\n"+
                "    int boolean_count;\n"+
                "}ReturnValues;\n";

        result+="ReturnValues* createReturnValues(int int_count, int real_count, int string_count,int boolean_count) {\n" +
                "    ReturnValues* rv = (ReturnValues*)malloc(sizeof(ReturnValues));\n" +
                "    if (rv == NULL) {\n" +
                "        printf(\"Errore di allocazione della memoria.\\n\");\n" +
                "        exit(1);\n" +
                "    }\n" +
                "    rv->integers = (int*)malloc(int_count * sizeof(int));\n" +
                "    rv->reals = (double*)malloc(real_count * sizeof(double));\n" +
                "    rv->strings = (char**)malloc(string_count * sizeof(char*));\n" +
                "    rv->booleans = (bool*)malloc(boolean_count * sizeof(bool));\n" +
                "    for (int i = 0; i < string_count; i++) {\n" +
                "        rv->strings[i] = NULL;\n" +
                "    }\n" +
                "    rv->int_count = int_count;\n" +
                "    rv->real_count = real_count;\n" +
                "    rv->string_count = string_count;\n" +
                "    rv->boolean_count = boolean_count;\n" +
                "    return rv;\n" +
                "}\n";

        result += "void freeReturnValues(ReturnValues* rv) {\n" +
                "    if (rv != NULL) {\n" +
                "        free(rv->integers);\n" +
                "        free(rv->reals);\n" +
                "        free(rv->booleans);\n" +
                "        for (int i = 0; i < rv->string_count; i++) {\n" +
                "            free(rv->strings[i]);\n" +
                "        }\n" +
                "        free(rv->strings);\n" +
                "        free(rv);\n" +
                "    }\n" +
                "}\n";

        result+="char * concatString(char* str1 , char* str2) {\n" +
                "   char *out= malloc(strlen(str1)+strlen(str2));\n" +
                "   strcpy(out,str1);\n" +
                "   strcat(out , str2);\n" +
                "   return out;\n" +
                "}\n"+
                "char * stringCopy(char * string){\n"+
                "   char *out= malloc(MAX*sizeof(char));\n"+
                "   strcpy(out,string);\n"+
                "   return out;\n"+
                "}\n" +
                "char * concatInt(char *a, int b, bool invert){\n" +
                "   char *n = malloc(MAX*sizeof(char));\n" +
                "   sprintf(n, \"%d\", b);\n" +
                "   if (invert)\n" +
                "       return concatString(a, n);\n" +
                "   else\n" +
                "       return concatString(n, a);\n" +
                "}\n" +
                "char * concatDouble(char *a, double b, bool invert){\n" +
                "   char *n = malloc(MAX*sizeof(char));\n" +
                "   sprintf(n, \"%f\", b);\n" +
                "   if (invert)\n" +
                "       return concatString(a, n);\n" +
                "   else\n" +
                "       return concatString(n, a);\n" +
                "}\n" +
                "char * concatBool(char *a, bool b, bool invert){\n" +
                "   char *n = malloc(MAX*sizeof(char));\n" +
                "   sprintf(n, \"%d\", b);\n" +
                "   if (invert)\n" +
                "       return concatString(a, n);\n" +
                "   else\n" +
                "       return concatString(n, a);\n" +
                "}\n";


        return result;

    }

    @Override
    public Object visit(AddOp a) {
        String result = "";

        //caso concatenazione fra stringhe
        if(a.getType()==Type.STRING) {
            String string1 = (String) a.getLeft().accept(this);
            String string2 = (String) a.getRight().accept(this);
            //Stringa + Stringa
            if(a.getLeft().getType()==a.getRight().getType()) {
                result += "concatString(" + string1 + "," + string2 + ")";
            }//Stringa + REAL
            else if(a.getLeft().getType()==Type.REAL    ||  a.getRight().getType()==Type.REAL){
                if(a.getLeft().getType()==Type.REAL){
                    result += "concatDouble(" + string1 + "," + string2 + ",false)";
                } else if (a.getRight().getType()==Type.REAL) {
                    result += "concatDouble(" + string1 + "," + string2 + ",true)";
                }
            }//stringa + INT
            else if(a.getLeft().getType()==Type.INTEGER    ||  a.getRight().getType()==Type.INTEGER){
                if(a.getLeft().getType()==Type.INTEGER){
                    result += "concatInt(" + string1 + "," + string2 + ",false)";
                } else if (a.getRight().getType()==Type.INTEGER) {
                    result += "concatInt(" + string1 + "," + string2 + ",true)";
                }
            }//stringa + BOOL
            else if(a.getLeft().getType()==Type.BOOL    ||  a.getRight().getType()==Type.BOOL){
                if(a.getLeft().getType()==Type.BOOL){
                    result += "concatBool(" + string1 + "," + string2 + ",false)";
                } else if (a.getRight().getType()==Type.BOOL) {
                    result += "concatBool(" + string1 + "," + string2 + ",true)";
                }
            }
        }
        //caso addizione normale
        else{
            result += a.getLeft().accept(this);
            result += " + ";
            result += a.getRight().accept(this);
        }

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
        boolean isMultipleAssignment = false;

        ArrayList<Identifier> identifiers = a.getIdentifiers();
        /*for(int i = 0 ;i < identifiers.size(); i++){
            result += identifiers.get(i).accept(this);
            if(i+1< identifiers.size())
                result += ",";
        }

        result += " = ";

        ArrayList<Expression> expressions = a.getExpressions();
        for(Expression e : expressions){
            result += e.accept(this);
        }

        result +=";";*/

        if(identifiers.size()>1)
            isMultipleAssignment=true;

        if(isMultipleAssignment){
            boolean isFunctionWithMultipleAssignmentCalled = false;
            int indexFunctionWithMultipleAssignmentCalled =0;
            ArrayList<Expression> expressions = a.getExpressions();
            for(int i=0;i< expressions.size();i++){
                if(expressions.get(i) instanceof FunCallOp){
                    isFunctionWithMultipleAssignmentCalled = true;
                    indexFunctionWithMultipleAssignmentCalled = i;
                    //TODO più chiamate a funzione nelle assegnazioni?...errore su analisi sintattica
                }
            }
            if(isFunctionWithMultipleAssignmentCalled){
                String nameToAppend=identifiers.get(indexFunctionWithMultipleAssignmentCalled).getName();
                //Potrei chiamare due volte assegnazioni a valori multipli nello stesso scope, ma non posso dichiarare due variabili ReturnValues* con lo stesso nome nello stesso scope, quindi faccio il nome dinamico
                result += " ReturnValues* result_"+nameToAppend+" = " + expressions.get(indexFunctionWithMultipleAssignmentCalled).accept(this)+";\n";
                int structIntCounter=0,structStringCounter=0,structRealCounter=0,structBooleanCounter=0;
                for(int i=0;i< identifiers.size();i++){
                    if(identifiers.get(i).getType()==Type.INTEGER) {
                        result += identifiers.get(i).accept(this)+" = result_"+nameToAppend+"->integers["+structIntCounter+"];\n";
                        structIntCounter++;
                    }
                    else if(identifiers.get(i).getType()==Type.REAL){
                        result += identifiers.get(i).accept(this)+" = result_"+nameToAppend+"->reals["+structRealCounter+"];\n";
                        structRealCounter++;
                    }
                    else if (identifiers.get(i).getType()==Type.STRING){
                        result += identifiers.get(i).accept(this) + " = stringCopy(result_"+nameToAppend+"->strings["+structStringCounter+"]);\n";
                        structStringCounter++;
                    }
                    else if(identifiers.get(i).getType()==Type.BOOL){
                        result += identifiers.get(i).accept(this)+" = result_"+nameToAppend+"->booleans["+structBooleanCounter+"];\n";
                        structBooleanCounter++;
                    }else{
                        result += "##############ERROR CONVERTING MULTIPLE PARAMETERS################";
                    }
                }

                result += "freeReturnValues(result_"+nameToAppend+");\n";
            }else{
                int i=0;
                for(i=0;i< identifiers.size() && i<expressions.size();i++){
                    result += identifiers.get(i).accept(this)+" = " + expressions.get(i).accept(this)+";\n";
                }
                if(i< identifiers.size()||i<expressions.size())
                    result+="###################ERROR IN ASSIGNMENT##################";
            }
        }else{
            result += identifiers.get(0).accept(this);
            result += " = ";
            ArrayList<Expression> expressions = a.getExpressions();
            for(Expression e : expressions){
                if(e.getType()==Type.STRING)
                    result += "stringCopy("+e.accept(this)+")";
                else
                    result += e.accept(this);
            }
            result +=";\n";
        }

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
        //caso confronto fra stringhe
        if(e.getLeft().getType()==Type.STRING && e.getRight().getType()==Type.STRING){
            String string1 = (String) e.getLeft().accept(this);
            String string2 = (String) e.getRight().accept(this);
            result += "strcmp("+string1+","+string2+")==0";
        }else{
            result += e.getLeft().accept(this);
            result += " == ";
            result += e.getRight().accept(this);
        }

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


        /*
        for(Type t : f.getReturnTypes()){
            result += typeConverter(t)+" ";
        }
        */
        //gestione con più valori di ritono

        if(f.getReturnTypes().size()>1) {
            result += " ReturnValues* ";
        }
        else{
            result += typeConverter(f.getReturnTypes().get(0)) +" ";
        }
        result += f.getIdentifier().accept(this);

        result += " ( ";

        ArrayList<ProcFunParamOp> parameters = f.getProcFunParamOpList();

        for(int i = 0 ;i< parameters.size(); i++){
            result += typeConverter(parameters.get(i).getType()) + " ";
            result += parameters.get(i).accept(this);
            if(i+1< parameters.size())
                result += ",";
        }

        if(functionOrProcedureCount>0){
            functionOrProcedureCount--;
            result+=");\n";
            return result;
        }
        else
        {
            result += " ){\n";
        }

        result += f.getBody().accept(this);

        result += " }\n";

        return result;
    }


    @Override
    public Object visit(GEOp g) {
        String result = "";

        //caso confronto fra stringhe
        if(g.getLeft().getType()==Type.STRING && g.getRight().getType()==Type.STRING){
            String string1 = (String) g.getLeft().accept(this);
            String string2 = (String) g.getRight().accept(this);
            result += "strcmp("+string1+","+string2+")>=0";
        }else {
            result += g.getLeft().accept(this);
            result += " >= ";
            result += g.getRight().accept(this);
        }

        return result;
    }

    @Override
    public Object visit(GTOp g) {
        String result = "";
        //caso confronto fra stringhe
        if(g.getLeft().getType()==Type.STRING && g.getRight().getType()==Type.STRING){
            String string1 = (String) g.getLeft().accept(this);
            String string2 = (String) g.getRight().accept(this);
            result += "strcmp("+string1+","+string2+")>0";
        }else {
            result += g.getLeft().accept(this);
            result += " > ";
            result += g.getRight().accept(this);
        }

        return result;
    }

    @Override
    public Object visit(Identifier i) {
        String result = "";
        Qualifier q = i.getQualifier();

        if(q!=null){
            if(q == Qualifier.OUT)
                result += "*";
        }else{
            //Devo settare il tipo dell'argomento (OUT), così all'interno del body della funzione, si potrà utilizzarlo come puntatore
            ArrayList<String> identifierWithReferenceInCurrentProcedure = identifiersByReference.get(currentProcedure);
            if (identifierWithReferenceInCurrentProcedure != null) {
                for (String s : identifierWithReferenceInCurrentProcedure) {
                    if (i.getName().equals(s)) {
                        result += "*";
                        break;
                    }
                }
            }
        }

        /*if(i.getType()==Type.STRING)
            result += "\""+i.getName()+"\"";
        else*/
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
        //caso confronto fra stringhe
        if(l.getLeft().getType()==Type.STRING && l.getRight().getType()==Type.STRING){
            String string1 = (String) l.getLeft().accept(this);
            String string2 = (String) l.getRight().accept(this);
            result += "strcmp("+string1+","+string2+")<=0";
        }else {
            result += l.getLeft().accept(this);
            result += " <= ";
            result += l.getRight().accept(this);
        }

        return result;
    }

    @Override
    public Object visit(LTOp l) {
        String result = "";
        if(l.getLeft().getType()==Type.STRING && l.getRight().getType()==Type.STRING){
            String string1 = (String) l.getLeft().accept(this);
            String string2 = (String) l.getRight().accept(this);
            result += "strcmp("+string1+","+string2+")<0";
        }else {
            result += l.getLeft().accept(this);
            result += " < ";
            result += l.getRight().accept(this);
        }

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
        if(n.getLeft().getType()==Type.STRING && n.getRight().getType()==Type.STRING){
            String string1 = (String) n.getLeft().accept(this);
            String string2 = (String) n.getRight().accept(this);
            result += "strcmp("+string1+","+string2+")!=0";
        }else {
            result += n.getLeft().accept(this);
            result += " != ";
            result += n.getRight().accept(this);
        }

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

        if((p.getIdentifier().getName().equals("main") && functionOrProcedureCount>0 )|| (p.getIdentifier().getName().equals("main") && mainVisited)){
            return result;
        }

        result += "void ";

        if(p.getIdentifier().getName().equals("main")){
            mainVisited=true;
        }

        result += p.getIdentifier().accept(this);

        result +=" ( ";

        //Devo settare il tipo dell'argomento (OUT), così all'interno del body della funzione, si potrà utilizzarlo come puntatore
        currentProcedure = p.getIdentifier().getName();

        ArrayList<ProcFunParamOp> parameters = p.getProcFunParamOpList();

        for(int i = 0 ;i< parameters.size(); i++){
            result += typeConverter(parameters.get(i).getType())+" ";
            result += parameters.get(i).accept(this);
            if(i+1< parameters.size())
                result += ",";
            //Devo settare il tipo dell'argomento (OUT), così all'interno del body della funzione, si potrà utilizzarlo come puntatore
            if(parameters.get(i).getIdentifier().getQualifier()==Qualifier.OUT){
                ArrayList<String> otherReferences = identifiersByReference.get(p.getIdentifier().getName());
                if(otherReferences==null)
                    otherReferences = new ArrayList<String>();
                otherReferences.add(parameters.get(i).getIdentifier().getName());
                identifiersByReference.put(p.getIdentifier().getName(),otherReferences);
            }
        }

        if(functionOrProcedureCount>0){
            functionOrProcedureCount--;
            result+=");\n";
            return result;
        }
        else
        {
            result += " ){\n";
        }

        result += p.getBody().accept(this);

        currentProcedure = "";

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

        //Devo scrivere prima la firma delle funzioni/procedure, quindi le conto per poi saltarle quando vado ad esaminarle
        //per questo faccio la accept 2 volte

        ArrayList<? extends FunctionOrProcedure> paramOps = p.getFunProcList();
        for (FunctionOrProcedure n : paramOps) {
            if(n instanceof ProcedureOp){
                if(!((ProcedureOp) n).getIdentifier().getName().equals("main"))
                    functionOrProcedureCount++;
            }else{
                functionOrProcedureCount++;
            }
        }

        //prima visita per scrivere le firme
        paramOps = p.getFunProcList();
        for (FunctionOrProcedure n : paramOps) {
            result += n.accept(this);
        }

        //seconda visita per le definizioni
        paramOps = p.getFunProcList();
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
        boolean printSomething = false;

        for (int i= 0; i<expressions.size();i++) {
            IOArg ioArg = (IOArg)expressions.get(i);
            Type ioArgExpressionType = ioArg.getExpression().getType();
            if(ioArg.isDollarSign()) {
                flag = 1;
                result += getPrintfScanfType(ioArgExpressionType);
            }else{
                //solo stampa
                printSomething=true;
                toPrint += ((String)ioArg.accept(this)).replace("\"","");
            }

        }

        toPrint += "\\n\");\n";
        if(flag==1){
            result += "\",";
            for (int i= 0; i<expressions.size();i++) {
                IOArg ioArg = (IOArg)expressions.get(i);
                if(ioArg.isDollarSign()) {

                    if(ioArg.getExpression().getType()==Type.STRING)
                        result += ioArg.getExpression().accept(this);
                    else
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
        if(printSomething)
            return toPrint;
        else
            return result;
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

        //TODO fare free(t)

        /*result +=  "returnValues = (returnTypesStruct *) malloc(MAX);\n";
        ArrayList<Expression> expressions = r.getExpressions();
        for(int i=0;i<expressions.size();i++) {
            if(expressions.get(i).getType()==Type.STRING)
                result += " returnValues["+i+"]."+expressions.get(i).getType()+" = stringCopy("+expressions.get(i).accept(this)+");\n";
            else
                result += " returnValues["+i+"]."+expressions.get(i).getType()+" = "+expressions.get(i).accept(this)+";\n";
        }*/
        ArrayList<Expression> expressions = r.getExpressions();
        if(expressions.size()>1) {//return multiple values
            int integerCount = 0;
            int realCount = 0;
            int stringCount = 0;
            int booleanCount = 0;
            //conto solo
            for(int i=0;i<expressions.size();i++) {
                if(expressions.get(i).getType()==Type.INTEGER)
                    integerCount++;
                else if(expressions.get(i).getType()==Type.REAL)
                    realCount++;
                else if (expressions.get(i).getType()==Type.STRING)
                    stringCount++;
                else if(expressions.get(i).getType()==Type.BOOL)
                    booleanCount++;
            }

            result += "\nReturnValues* rv = createReturnValues("+integerCount+","+realCount+","+stringCount+","+booleanCount+");\n";

            int structIntCounter=0,structStringCounter=0,structRealCounter=0,structBooleanCounter=0;

            for(int i=0;i<expressions.size();i++) {
                if(expressions.get(i).getType()==Type.INTEGER) {
                    result += "rv->integers["+structIntCounter+"] = "+expressions.get(i).accept(this)+";\n";
                    structIntCounter++;
                }
                else if(expressions.get(i).getType()==Type.REAL){
                    result += "rv->reals["+structRealCounter+"] = "+expressions.get(i).accept(this)+";\n";
                    structRealCounter++;
                }
                else if (expressions.get(i).getType()==Type.STRING){
                    result += "rv->strings["+structStringCounter+"] = stringCopy("+expressions.get(i).accept(this)+");\n";
                    structStringCounter++;
                }
                else if(expressions.get(i).getType()==Type.BOOL){
                    result += "rv->booleans["+structBooleanCounter+"] = "+expressions.get(i).accept(this)+";\n";
                    structBooleanCounter++;
                }
            }

            result+="return rv;\n";
        }
        else {//return single expression
            result += "return " + expressions.get(0).accept(this)+";\n";
        }

        return result;
    }

    @Override
    public Object visit(String_const s) {
        String result = "";

        result += "\""+s.getValue()+"\"";

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
        boolean semiColonFlag = true;
        for(int i=0;i<identifierExpressionList.size();i++) {
            IdentifierExpression ie = identifierExpressionList.get(i);
            if(lastTypeDeclared != ie.getIdentifier().getType() || ie.getIdentifier().getType()==Type.STRING) {
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
                if(ie.getExpression().getType()==Type.STRING)
                    result += "stringCopy("+ie.getExpression().accept(this)+")";
                else
                    result += ie.getExpression().accept(this);
            }else if(v.getDeclarationType()==DeclarationType.DECLARATION && ie.getIdentifier().getType()==Type.STRING){//Allocazione dinamica delle stringhe
                result += " = (char*)malloc(MAX*sizeof(char))";
            }
            semiColonFlag=true;
            if((i+1 < identifierExpressionList.size() && lastTypeDeclared != identifierExpressionList.get(i+1).getIdentifier().getType()) ||ie.getIdentifier().getType()==Type.STRING){
                //caso int a = 1,double b = 2.2; non devo mettere la , ma andare a capo
                result+=";\n";
                semiColonFlag=false;
                lastTypeDeclared = Type.NOTYPE;
            }
            else if(i+1 < identifierExpressionList.size()) {
                result += ",";
            }
        }
        if(semiColonFlag)
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

        WritingType writingType = w.getWritingType();

        result += "printf(\"";

        ArrayList<Expression> expressions = w.getExpressions();

        ArrayList<Expression> printfArguments = new ArrayList<>();
        for(int i= 0; i<expressions.size();i++) {

            IOArg ioArg = (IOArg)expressions.get(i);
            Type ioArgExpressionType = ioArg.getExpression().getType();

            if(ioArg.isDollarSign()){
                result += getPrintfScanfType(ioArgExpressionType);
                printfArguments.add(ioArg);
            }
            else if(ioArgExpressionType==Type.STRING){
                //Risolto in AddOp
                /*(ioArg.getExpression().getExpressionType() == ExpressionType.PLUS){
                    //è una concatenazione tra stringhe, non devo inserire gli apici, quindi faccio manualmente la visita
                    result += ((AddOp)ioArg.getExpression()).getLeft().accept(this);
                    result += ((AddOp)ioArg.getExpression()).getRight().accept(this);
                }
                else*/
                    result += ((String)ioArg.accept(this)).replace("\"","");
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
            case INTEGER -> result += "%d";
            case BOOL -> result += "%d"; //TODO bool?
            case REAL -> result += "%lf";
            case STRING -> result += "%s";
        }
        return result;
    }

}
