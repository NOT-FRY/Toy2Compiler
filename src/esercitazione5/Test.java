package esercitazione5;

import tree_structure.ProgramOp;
import visitors.ScopeVisitor;
import visitors.PrintXMLTreeVisitor;
import visitors.SemanticVisitor;
import visitors.Toy2ToCVisitor;

import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

public class Test {
    public static void main(String[] args){
        File file = new File("test_files"+ File.separator + args[0]+".txt");
        try {
            parser p = new parser(new Yylex(new FileReader(file)));

            ProgramOp program = (ProgramOp) p.debug_parse().value;

            PrintXMLTreeVisitor treeVisitor = new PrintXMLTreeVisitor("xmloutput"+ File.separator + args[0] + ".xml");
            treeVisitor.visit(program);
            treeVisitor.dispose();

            ScopeVisitor scopeVisitor = new ScopeVisitor();
            scopeVisitor.visit(program);

            SemanticVisitor semanticVisitor = new SemanticVisitor();
            semanticVisitor.visit(program);

            Toy2ToCVisitor toy2ToCVisitor = new Toy2ToCVisitor();
            String cFile = (String) toy2ToCVisitor.visit(program);

            PrintWriter out = new PrintWriter("c_output"+ File.separator + args[0] + ".c");
            out.write(cFile);
            out.flush();

        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
