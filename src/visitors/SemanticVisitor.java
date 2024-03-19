package visitors;
import scoping.*;
import tree_structure.*;
import tree_structure.Expression.*;
import tree_structure.Expression.Expression;

import tree_structure.Statement.*;
import tree_structure.Statement.Statement;

import java.util.ArrayList;

public class SemanticVisitor implements Visitor {

    private SymbolTableStack symbolTableStack;
    private String fileName;

    public SemanticVisitor(String fileName) {
        symbolTableStack = new SymbolTableStack();
        this.fileName = fileName;
    }

    @Override
    public Object visit(AddOp a) {

        a.getLeft().accept(this);

        a.getRight().accept(this);

        Type t = TypeCheck.checkBinaryExprType(a.getLeft(),a.getRight(),ExpressionType.PLUS);
        if(t==Type.ERROR){
            System.err.println("Invalid "+fileName);
            System.err.println(">Semantic error: tipo non compatibile con l'operando -starting at:" +a.getLeft().toString());
            System.exit(1);
        }else{
            a.setType(t);
        }
        return null;
    }

    @Override
    public Object visit(AndOp a) {
        a.getLeft().accept(this);

        a.getRight().accept(this);

        Type t = TypeCheck.checkBinaryExprType(a.getLeft(),a.getRight(),ExpressionType.AND);
        if(t==Type.ERROR){
            System.err.println("Invalid "+fileName);
            System.err.println(">Semantic error: tipo non compatibile con l'operando -starting at:" +a.getLeft().toString());
            System.exit(1);
        }else{
            a.setType(t);
        }
        return null;
    }

    @Override
    public Object visit(AssignStatement a) {

        ArrayList<Identifier> identifiers = a.getIdentifiers();
        for(Identifier i : identifiers){
            i.accept(this);
        }
        ArrayList<Expression> expressions = a.getExpressions();
        for(Expression e : expressions){
            e.accept(this);
        }

        Type t=Type.ERROR;
        try{
            t = TypeCheck.checkAssignStatement(a);
        }catch(Exception e){
            System.err.println("Invalid "+fileName);
            System.err.println(e.getMessage());
            System.exit(1);
        }finally {
            a.setType(t);
        }

        return null;
    }

    @Override
    public Object visit(BodyOp b) {
        /*Se il nodo dell’AST è legato ad un costrutto di creazione di nuovo scope (ProgramOp, FunOp,
            ProcOp e BodyOp solo se non è figlio di FunOp o ProcOp)
        ScopeType fatherScopeType = scopes.peek().getScopeType();
        if(fatherScopeType != ScopeType.FUNCTION && fatherScopeType != ScopeType.PROCEDURE) {
            RICORDARSI CHE c'è un nuovo scope (importante?)
        }
        */
        if(b.getSymbolTable()!=null)
            symbolTableStack.enterScope(b.getSymbolTable());
        ArrayList<VarDeclOp> varDeclList = b.getVarDeclList();
        for(VarDeclOp v : varDeclList){
            v.accept(this);
        }
        ArrayList<Statement> statements = b.getStatementList();
        for(Statement s : statements){
            s.accept(this);
        }
        if(b.getSymbolTable()!=null)
            symbolTableStack.exitScope();
        b.setType(Type.NOTYPE);

        return null;
    }

    @Override
    public Object visit(DiffOp d) {
        d.getLeft().accept(this);
        d.getRight().accept(this);
        Type t = TypeCheck.checkBinaryExprType(d.getLeft(),d.getRight(),ExpressionType.MINUS);
        if(t==Type.ERROR){
            System.err.println("Invalid "+fileName);
            System.err.println(">Semantic error: tipo non compatibile con l'operando -starting at:" +d.getLeft().toString());
            System.exit(1);
        }else{
            d.setType(t);
        }
        return null;
    }

    @Override
    public Object visit(DivOp d) {
        d.getLeft().accept(this);
        d.getRight().accept(this);
        Type t = TypeCheck.checkBinaryExprType(d.getLeft(),d.getRight(),ExpressionType.DIV);
        if(t==Type.ERROR){
            System.err.println("Invalid "+fileName);
            System.err.println(">Semantic error: tipo non compatibile con l'operando -starting at:" +d.getLeft().toString());
            System.exit(1);
        }else{
            d.setType(t);
        }
        return null;
    }

    @Override
    public Object visit(ElifOp e) {

        e.getExpression().accept(this);
        e.getBody().accept(this);

        return null;
    }

    @Override
    public Object visit(EQOp e) {

        e.getLeft().accept(this);

        e.getRight().accept(this);

        Type t = TypeCheck.checkBinaryExprType(e.getLeft(),e.getRight(),ExpressionType.EQ);
        if(t==Type.ERROR){
            System.err.println("Invalid "+fileName);
            System.err.println(">Semantic error: tipo non compatibile con l'operando -starting at:" +e.getLeft().toString());
            System.exit(1);
        }else{
            e.setType(t);
        }

        return null;
    }

    @Override
    public Object visit(False_const f) {

        f.getValue();

        return null;
    }

    @Override
    public Object visit(FunCallOp f) {

        f.getIdentifier().accept(this);

        ArrayList<Expression> arguments = f.getExpressions();
        for(Expression e : arguments){
            e.accept(this);
        }

        Symbol s = symbolTableStack.lookup(f.getIdentifier().getName(),Kind.METHOD);

        if(s==null){
            System.err.println("Invalid "+fileName);
            System.err.println(">Semantic error: chiamata a funzione non dichiarata : " + f.getIdentifier().getName());
            System.exit(1);
        }
        /*
           * Controllo che il tipo dei parametri della chiamata sia corretto rispetto alla definizione
           * */
        ArrayList<Type> definitionParameterTypes = s.getParamTypes();
        int i=0;
        for(i=0;i<definitionParameterTypes.size() && i<arguments.size();i++){
            if(definitionParameterTypes.get(i) != arguments.get(i).getType()){
                System.err.println("Invalid "+fileName);
                System.err.println(">Semantic error: Tipo degli argomenti non compatibile con la definizione della funzione : "+ f.getIdentifier().getName() + " argomento: " +arguments.get(i).getType());
                System.exit(1);
            }
        }
        if(i < definitionParameterTypes.size() || i < arguments.size()){
            System.err.println("Invalid "+fileName);
            System.err.println(">Semantic error: Numero degli argomenti non compatibile con la definizione della funzione : "+ f.getIdentifier().getName());
            System.exit(1);
        }
        /* Assegnazione dei tipi di ritorno alla chiamata della funzione
           I TIPI DI RITORNO DELLA CHIAMATA DEVONO ESSERE GLI STESSI DELLA DEFINIZIONE!, riempio il nodo con le informazioni
           */
        for(Type t : s.getReturnTypes()){
            f.addReturnType(t);
        }

        //Tipo della funzione, nel caso in cui la si voglia utilizzare come parametro di un'altra funzione
        //N.B.
        /*
        * se f(x) è invece una chiamata a funzione che restituisce più valori di ritorno allora NON deve essere possibile:
            1. qualsiasi operazione unaria o binaria su f(x)
            2. passare f(x) come argomento ad una funzione (questo sarebbe possibile solo se f(x) restituisse  un unico valore).
        *
        * */
        if(s.getReturnTypes().size()==1)
            f.setType(s.getReturnTypes().get(0));


        return null;
    }

    @Override
    public Object visit(FunctionOp f) {

        symbolTableStack.enterScope(f.getSymbolTable());

        f.getIdentifier().accept(this);

        f.getBody().accept(this);

        try{
            Checks.checkFunctionOp(f);
        }catch(Exception e){
            System.err.println("Invalid "+fileName);
            System.err.println(e.getMessage());
            System.exit(1);
        }

        symbolTableStack.exitScope();

        return null;
    }


    @Override
    public Object visit(GEOp g) {

        g.getLeft().accept(this);

        g.getRight().accept(this);

        Type t = TypeCheck.checkBinaryExprType(g.getLeft(),g.getRight(),ExpressionType.GE);
        if(t==Type.ERROR){
            System.err.println("Invalid "+fileName);
            System.err.println(">Semantic error: tipo non compatibile con l'operando -starting at:" +g.getLeft().toString());
            System.exit(1);
        }else{
            g.setType(t);
        }

        return null;
    }

    @Override
    public Object visit(GTOp g) {

        g.getLeft().accept(this);

        g.getRight().accept(this);

        Type t = TypeCheck.checkBinaryExprType(g.getLeft(),g.getRight(),ExpressionType.GT);
        if(t==Type.ERROR){
            System.err.println("Invalid "+fileName);
            System.err.println(">Semantic error: tipo non compatibile con l'operando -starting at:" +g.getLeft().toString());
            System.exit(1);
        }else{
            g.setType(t);
        }

        return null;
    }

    @Override
    public Object visit(Identifier i) {
        Qualifier q = i.getQualifier();

        if(i.getIdentifierType() == Kind.VAR) {
            Symbol s = symbolTableStack.lookup(i.getName(), i.getIdentifierType());
            if (s == null) {
                System.err.println("Invalid "+fileName);
                System.err.println(">Semantic error: Identificatore non dichiarato : " + i.getName());
                System.exit(1);
            } else {
                i.setType(s.getType());
            }
        }

        return null;
    }

    @Override
    public Object visit(IfStatement i) {

        i.getExpression().accept(this);
        i.getBody().accept(this);
        ArrayList<ElifOp> elifOps = i.getElifList();
        for (ElifOp p : elifOps) {
            p.accept(this);
        }
        if(i.getElseBody() != null)
            i.getElseBody().accept(this);

        Type ifType;
        Type expressionType = i.getExpression().getType();
        Type bodyType = i.getBody().getType();
        Type elseBodyType = null;
        if(i.getElseBody() != null)
            elseBodyType = i.getElseBody().getBody().getType();

        //if con elif completo (i controlli su null vengono fatti da checkIfElsifStatement )
        if(!elifOps.isEmpty()){
            ifType = TypeCheck.checkIfElsifStatement(expressionType,bodyType,i.getElifList(),elseBodyType);
        }
        else if(i.getElseBody()!=null){//caso if else
            ifType = TypeCheck.checkIfElseStatement(expressionType,bodyType,elseBodyType);
        }
        //caso semplice if
        else{
            ifType = TypeCheck.checkIfStatement(expressionType,bodyType);
        }

        if(ifType==Type.ERROR){
            System.err.println("Invalid "+fileName);
            System.err.println(">Semantic error: condizione o tipo non compatibile nel corpo dell if");
            System.exit(1);
        }else{
            i.setType(ifType);
        }

        return null;
    }

    @Override
    public Object visit(Integer_const i) {

        i.getValue();

        return null;
    }

    @Override
    public Object visit(LEOp l) {

        l.getLeft().accept(this);

        l.getRight().accept(this);

        Type t = TypeCheck.checkBinaryExprType(l.getLeft(),l.getRight(),ExpressionType.LE);
        if(t==Type.ERROR){
            System.err.println("Invalid "+fileName);
            System.err.println(">Semantic error: tipo non compatibile con l'operando -starting at:" +l.getLeft().toString());
            System.exit(1);
        }else{
            l.setType(t);
        }

        return null;
    }

    @Override
    public Object visit(LTOp l) {

        l.getLeft().accept(this);

        l.getRight().accept(this);

        Type t = TypeCheck.checkBinaryExprType(l.getLeft(),l.getRight(),ExpressionType.LT);
        if(t==Type.ERROR){
            System.err.println("Invalid "+fileName);
            System.err.println(">Semantic error: tipo non compatibile con l'operando -starting at:" +l.getLeft().toString());
            System.exit(1);
        }else{
            l.setType(t);
        }

        return null;
    }


    @Override
    public Object visit(MulOp m) {

        m.getLeft().accept(this);

        m.getRight().accept(this);

        Type t = TypeCheck.checkBinaryExprType(m.getLeft(),m.getRight(),ExpressionType.TIMES);
        if(t==Type.ERROR){
            System.err.println("Invalid "+fileName);
            System.err.println(">Semantic error: tipo non compatibile con l'operando -starting at:" +m.getLeft().toString());
            System.exit(1);
        }else{
            m.setType(t);
        }

        return null;
    }

    @Override
    public Object visit(NEOp n) {

        n.getLeft().accept(this);

        n.getRight().accept(this);

        Type t = TypeCheck.checkBinaryExprType(n.getLeft(),n.getRight(),ExpressionType.NE);
        if(t==Type.ERROR){
            System.err.println("Invalid "+fileName);
            System.err.println(">Semantic error: tipo non compatibile con l'operando -starting at:" +n.getLeft().toString());
            System.exit(1);
        }else{
            n.setType(t);
        }

        return null;
    }

    @Override
    public Object visit(NotOp n) {

        n.getValue().accept(this);

        Type t = TypeCheck.checkUnaryExprType(n.getValue(),ExpressionType.NOT);
        if(t==Type.ERROR){
            System.err.println("Invalid "+fileName);
            System.err.println(">Semantic error: tipo non compatibile con l'operando -starting at:" +n.getValue().toString());
            System.exit(1);
        }else{
            n.setType(t);
        }

        return null;
    }

    @Override
    public Object visit(OrOp o) {

        o.getLeft().accept(this);

        o.getRight().accept(this);

        Type t = TypeCheck.checkBinaryExprType(o.getLeft(),o.getRight(),ExpressionType.OR);
        if(t==Type.ERROR){
            System.err.println("Invalid "+fileName);
            System.err.println(">Semantic error: tipo non compatibile con l'operando -starting at:" +o.getLeft().toString());
            System.exit(1);
        }else{
            o.setType(t);
        }

        return null;
    }

    @Override
    public Object visit(ProcFunParamOp p){

        p.getIdentifier().accept(this);

        return null;
    }

    @Override
    public Object visit(ProcedureOp p) {

        symbolTableStack.enterScope(p.getSymbolTable());

        p.getIdentifier().accept(this);

        //Devo settare il tipo dell'argomento (OUT), così all'interno del body della funzione, si potrà utilizzarlo come puntatore, ma lo faccio nella traduzione in C
        //perchè qui è troppo complesso

        p.getBody().accept(this);

        try {
            Checks.checkProcedureOp(p);
        }catch (Exception e){
            System.err.println("Invalid "+fileName);
            System.err.println(e.getMessage());
            System.exit(1);
        }

        symbolTableStack.exitScope();

        return null;
    }

    @Override
    public Object visit(ProgramOp p) {

        symbolTableStack.enterScope(p.getSymbolTable());

        ArrayList<VarDeclOp> varDeclList = p.getVarDeclList();
        for (VarDeclOp v : varDeclList) {
            v.accept(this);
        }
        ArrayList<? extends FunctionOrProcedure> paramOps = p.getFunProcList();
        for (FunctionOrProcedure n : paramOps) {
            n.accept(this);
        }

        symbolTableStack.exitScope();
        return null;
    }

    @Override
    public Object visit(ReadStatement r) {

        ArrayList<Expression> expressions = r.getExpressions();
        for (Expression v : expressions) {
            v.accept(this);
        }

        r.setType(Type.NOTYPE);

        try {
            Checks.checkReadStatement(r);
        }catch (Exception e){
            System.err.println("Invalid "+fileName);
            System.err.println(e.getMessage());
            System.exit(1);
        }

        return null;
    }

    @Override
    public Object visit(Real_const r) {

        r.getValue();

        return null;
    }

    @Override
    public Object visit(ReturnStatement r) {

        ArrayList<Expression> expressions = r.getExpressions();
        for(Expression e : expressions) {
            e.accept(this);
        }

        r.setType(Type.NOTYPE);

        return null;
    }

    @Override
    public Object visit(String_const s) {
        s.getValue();

        return null;
    }

    @Override
    public Object visit(True_const t) {
        t.getValue();
        return null;
    }

    @Override
    public Object visit(UminusOp u) {

        u.getValue().accept(this);

        Type t = TypeCheck.checkUnaryExprType(u.getValue(),ExpressionType.UMINUS);
        if(t==Type.ERROR){
            System.err.println("Invalid "+fileName);
            System.err.println(">Semantic error: tipo non compatibile con l'operando -starting at:" +u.getValue().toString());
            System.exit(1);
        }else{
            u.setType(t);
        }

        return null;
    }

    @Override
    public Object visit(VarDeclOp v) {

        ArrayList<IdentifierExpression> identifierExpressionList = v.getIdentifierExpressionsList();
        for(IdentifierExpression ie : identifierExpressionList) {

            ie.accept(this);

        }

        if(!Checks.checkDeclaration(v)){
            System.err.println("Invalid "+fileName);
            System.err.println(">Semantic error: Nell'inizializzazione il numero delle costanti deve essere pari al numero degli id ");
            System.exit(1);
        }

        return null;
    }

    @Override
    public Object visit(WhileStatement w) {

        w.getExpression().accept(this);
        w.getBody().accept(this);

        Type t = TypeCheck.checkWhileStatement(w.getExpression().getType(), w.getBody().getType());
        if(t==Type.ERROR){
            System.err.println("Invalid "+fileName);
            System.err.println(">Semantic error: condizione o tipo non compatibile nel corpo del while");
            System.exit(1);
        }else{
            w.setType(t);
        }

        return null;
    }

    @Override
    public Object visit(WriteStatement w) {

        WritingType writingType = w.getWritingType();

        ArrayList<Expression> expressions = w.getExpressions();
        for(Expression e : expressions) {
            e.accept(this);
        }

        w.setType(Type.NOTYPE);

        return null;
    }

    @Override
    public Object visit(ElseOp e) {

        e.getBody().accept(this);

        return null;
    }


    @Override
    public Object visit(IterOp i) {
        //Utilizzato solo per costruire l'albero nel parser
        return null;
    }

    @Override
    public Object visit(IdentifierExpression ie) {

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

        try {
            Checks.checkIOArg(i);
        }catch (Exception e){
            System.err.println("Invalid "+fileName);
            System.err.println(e.getMessage());
            System.exit(1);
        }

        return null;
    }

    public Object visit(ProcedureExpression p) {
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

        ArrayList<ProcedureExpression> arguments = p.getProcedureExpressions();
        for(ProcedureExpression e : arguments){
            e.accept(this);
        }


        Symbol s = symbolTableStack.lookup(p.getIdentifier().getName(),Kind.METHOD);

        if(s==null){
            System.err.println("Invalid "+fileName);
            System.err.println(">Semantic error: chiamata ad una procedura non definita : "+ p.getIdentifier().getName());
            System.exit(1);
        }else{
            /*
             * Controllo che il tipo dei parametri della chiamata sia corretto rispetto alla definizione
             * */
            ArrayList<Type> definitionParameterTypes = s.getParamTypes();
            ArrayList<ProcFunParamOp> definitionParameters = s.getParameters();

            if(definitionParameters==null){//Si è verificato errore, probabilmente è stata chiamata una funzione che in realtà è stata presa dal parser come procedura
                System.err.println("Invalid "+fileName);
                System.err.println(">Semantic error: errore nella chiamata a procedura : "+ p.getIdentifier().getName());
                System.exit(1);
            }

            int i=0;

            for(i=0;i<definitionParameterTypes.size() && i<arguments.size();i++){

                if(arguments.get(i).isReference()) {//se è per riferimento, questo argomento è per forza un identificatore
                    if(definitionParameterTypes.get(i) != arguments.get(i).getIdentifier().getType()){
                        System.err.println("Invalid "+fileName);
                        System.err.println(">Semantic error: Tipo degli argomenti non compatibile con la definizione della procedura : "+ p.getIdentifier().getName() + " tipo dell' argomento: " +arguments.get(i).getIdentifier().getType()+" tipo atteso: "+definitionParameterTypes.get(i));
                        System.exit(1);
                    }
                    //Controllo sul passaggio per riferimento
                    if(definitionParameters.get(i).getIdentifier().getQualifier() != Qualifier.OUT){
                        System.err.println("Invalid "+fileName);
                        System.err.println(">Semantic error: Passaggio per riferimento non consentito nella definizione della funzione - at: "+ p.getIdentifier().getName() + " argomento: " +arguments.get(i).getExpression());
                        System.exit(1);
                    }
                }
                else{ //è un espressione qualsiasi, senza il riferimento
                    if(definitionParameterTypes.get(i) != arguments.get(i).getExpression().getType()){
                        System.err.println("Invalid "+fileName);
                        System.err.println(">Semantic error: Tipo degli argomenti non compatibile con la definizione della procedura : "+ p.getIdentifier().getName() + " tipo dell' argomento: " +arguments.get(i).getExpression().getType()+" tipo atteso: "+definitionParameterTypes.get(i));
                        System.exit(1);
                    }
                    //Controllo sul passaggio per riferimento, che dovrebbe essere lo stesso della definizione
                    if(definitionParameters.get(i).getIdentifier().getQualifier() == Qualifier.OUT){
                        System.err.println("Invalid "+fileName);
                        //non sono entrato nell'if precedente isReference, quindi è errore
                        System.err.println(">Semantic error: Passaggio per riferimento necessario nella chiamata a funzione - at: "+ p.getIdentifier().getName() + " argomento: " +arguments.get(i).getExpression());
                        System.exit(1);
                    }
                }
            }

            if(i < definitionParameterTypes.size() || i < arguments.size()){
                System.err.println("Invalid "+fileName);
                System.err.println(">Semantic error: Numero degli argomenti non compatibile con la definizione della procedura : "+ p.getIdentifier().getName());
                System.exit(1);
            }



        }

        p.setType(Type.NOTYPE);

        return null;
    }

}
