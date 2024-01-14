package scoping;

import tree_structure.*;
import tree_structure.Expression.Expression;
import tree_structure.Expression.FunCallOp;
import tree_structure.Expression.Identifier;
import tree_structure.Statement.*;

import java.util.ArrayList;

/*
* Questa classe contiene i metodi di controllo della semantica, non si assegnano necessariamente dei tipi
*
* */
public class Checks {

    public static boolean checkDeclaration(VarDeclOp v){
        ArrayList<IdentifierExpression> identifierExpressionList = v.getIdentifierExpressionsList();

        if(v.getDeclarationType()== DeclarationType.DECLARATION)//i tipi sono già inferiti, non è un'assegnazione
            return true;
        for(IdentifierExpression ie : identifierExpressionList) {
            //Nell'inizializzazione il numero delle costanti deve essere pari al numero degli id
            if(ie.getIdentifier()==null || ie.getExpression()==null){
                return false;
            }
        }
        return true;
    }

    public static void checkFunctionOp(FunctionOp f)throws Exception{
        /*
        * [Deve contenere
            l’istruzione return seguita dalle espressioni di ritorno (che
            devono essere di pari numero ai tipi di ritorno
            dichiarati)
        *
        * */
        int numberOfReturnTypesDeclared = f.getReturnTypes().size();
        int numberOfEffectiveReturnTypes = 0;

        ArrayList<Statement> bodyStatements = f.getBody().getStatementList();

        for(Statement s : bodyStatements){
            if(s instanceof ReturnStatement){
                numberOfEffectiveReturnTypes++;
            }
        }

        /*ALMENO UN RETURN*/
        if(numberOfEffectiveReturnTypes==0)
            throw new Exception(">Semantic error: Necessario almeno un'istruzione di ritorno all'interno della funzione - at: "+f.getIdentifier().getName());

        if(numberOfReturnTypesDeclared != numberOfEffectiveReturnTypes)
            throw new Exception(">Semantic error: Numero di valori di ritorno effettivi diverso dal numero di valori di ritorno dichiarati - at: "+f.getIdentifier().getName());


        /*
        * I parametri di una funzione sono solo in lettura e non possono
        *  essere mai assegnati (immutable).
        * */
        ArrayList<ProcFunParamOp> parameters = f.getProcFunParamOpList();

        for(Statement s : bodyStatements){
            if(s instanceof AssignStatement){
                ArrayList<Identifier> identifiersAssigned = ((AssignStatement) s).getIdentifiers();
                for(Identifier i : identifiersAssigned){
                    for(ProcFunParamOp p : parameters) {
                        if (i.getName().equals(p.getIdentifier().getName())){
                            throw new Exception(">Semantic error: I parametri di una funzione possono essere in sola lettura - at: "+i.getName()+" inside : "+f.getIdentifier().getName());
                        }
                    }
                }
            }
        }

    }

    public static void checkProcedureOp(ProcedureOp p) throws Exception{
        /*
        * Sono come le funzioni ma non hanno valori di ritorno *[[ ed i
          parametri di input sono sia in lettura che in scrittura
          (mutable) inoltre l’istruzione return non deve essere
          presente.]]*
        *
        * */

        ArrayList<Statement> bodyStatements = p.getBody().getStatementList();

        for(Statement s : bodyStatements){
            if(s instanceof ReturnStatement){
                throw new Exception(">Semantic error: Le istruzioni di ritorno non sono consentite all'interno di procedure - at: "+p.getIdentifier().getName());
            }
        }
    }

    public static void checkReadStatement(ReadStatement r)throws Exception{
        /*
        * Le istruzioni di input e di output usano lo stesso
            operatore $ ma si noti che, per l’output, l’argomento può
            essere una qualsiasi espressione, mentre per l’input, esso
            deve essere per forza un identificatore.
        * */

        ArrayList<Expression> IOargs = r.getExpressions();

        for(Expression e : IOargs){
            if(e instanceof IOArg){
                if(((IOArg) e).isDollarSign()){
                    Expression IOArgExpression = ((IOArg) e).getExpression();
                    if(!(IOArgExpression instanceof Identifier)){
                        throw new Exception(">Semantic error: solo identificatori consentiti come argomenti di $ in lettura - at: " +IOArgExpression.getExpressionType());
                    }
                }
            }
        }

    }

    public static void checkIOArg(IOArg arg)throws Exception{
        if(!arg.isDollarSign()){
            //Consentite solo concatenazione di stringhe o singola stringa
            if(arg.getExpression().getType() != Type.STRING){
                throw new Exception(">Semantic error: solo stringhe consentite in IOargs");
            }
        }
    }

}
