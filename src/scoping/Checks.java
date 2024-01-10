package scoping;

import tree_structure.DeclarationType;
import tree_structure.IdentifierExpression;
import tree_structure.VarDeclOp;

import java.util.ArrayList;

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
}
