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
        if (args.length == 0) {
            System.out.println("Argomenti insufficienti\n Inserire il nome del file come parametro");
            System.exit(1);
        }

        //File file = new File("test_files"+ File.separator + args[0]+".txt");
        File file = new File(args[0]);

        try {
            parser p = new parser(new Yylex(new FileReader(file)));

            ProgramOp program = (ProgramOp) p.parse().value;

            File xmlFile = new File("xml_out" +File.separator +args[0].replace(".txt",".xml"));

            if(!xmlFile.exists()){
                xmlFile.getParentFile().mkdirs();
                xmlFile.createNewFile();
            }

            PrintXMLTreeVisitor treeVisitor = new PrintXMLTreeVisitor(xmlFile);
            treeVisitor.visit(program);
            treeVisitor.dispose();

            String fileName = args[0].substring(args[0].lastIndexOf("/")+1);

            ScopeVisitor scopeVisitor = new ScopeVisitor(fileName);
            scopeVisitor.visit(program);

            SemanticVisitor semanticVisitor = new SemanticVisitor(fileName);
            semanticVisitor.visit(program);

            Toy2ToCVisitor toy2ToCVisitor = new Toy2ToCVisitor();
            String cFile = (String) toy2ToCVisitor.visit(program);

            fileName = fileName.replace(".txt",".c");

            File cFilePath = new File("test_files"+File.separator+"c_out"+ File.separator + fileName);

            if(!cFilePath.exists()){
                cFilePath.getParentFile().mkdirs();
                cFilePath.createNewFile();
            }
            PrintWriter out = new PrintWriter(cFilePath);

            out.write(cFile);
            out.flush();

        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
