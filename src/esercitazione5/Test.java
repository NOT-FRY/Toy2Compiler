package esercitazione5;

import tree_structure.ProgramOp;
import visitors.ScopeVisitor;
import visitors.PrintXMLTreeVisitor;

import java.io.File;
import java.io.FileReader;

public class Test {
    public static void main(String[] args) throws Exception{
        File file = new File("test\\" + args[0]+".txt");
        parser p = new parser(new Yylex(new FileReader(file)));

        ProgramOp program = (ProgramOp)p.debug_parse().value;

        PrintXMLTreeVisitor treeVisitor = new PrintXMLTreeVisitor("xmloutput\\"+args[0]+".xml");
        treeVisitor.visit(program);
        treeVisitor.dispose();

        ScopeVisitor scopeVisitor = new ScopeVisitor();
        scopeVisitor.visit(program);
        //SemanticVisitor semanticVisitor = new SemanticVisitor();
        //semanticVisitor.visit(program);

    }
}
