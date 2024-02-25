package scoping;
import visitors.ScopeType;

import java.util.Stack;

public class SymbolTableStack {

    private Stack<SymbolTable> stack;

    public SymbolTableStack() {
        this.stack = new Stack<SymbolTable>();
    }

    public void enterScope(String name){
        stack.push(new SymbolTable(name));
    }

    public void enterScope(String name, ScopeType scopeType){
        stack.push(new SymbolTable(name,scopeType));
    }

    public void exitScope(){
        stack.pop();
    }

    public Symbol lookup(String symbolId,Kind kind){

        for(int i=stack.size()-1; i>=0; i--){
            SymbolTable current = stack.elementAt(i);
            Symbol symbolFound = current.lookup(symbolId,kind);
            if(symbolFound!=null)
                return symbolFound;
        }

        return null;
    }

    public boolean probe(String symbolId,Kind kind){
        return stack.peek().lookup(symbolId,kind)!=null;
    }

    public SymbolTable peek(){
        return stack.peek();
    }
}
