package visitors;

import tree_structure.*;
import tree_structure.Expression.*;
import tree_structure.Statement.*;

public interface Visitor {

    Object visit(AddOp a);
    Object visit(AndOp a);
    Object visit(AssignStatement a);
    Object visit(BodyOp b);
    Object visit(DiffOp d);
    Object visit(DivOp d);
    Object visit(ElifOp e);
    Object visit(EQOp e);
    Object visit(False_const f);
    Object visit(FunCallOp f);
    Object visit(FunctionOp f);
    Object visit(GEOp g);
    Object visit(GTOp g);
    Object visit(Identifier i);
    Object visit(IfStatement i);
    Object visit(Integer_const i);
    Object visit(LEOp l);
    Object visit(LTOp l);
    Object visit(MulOp m);
    Object visit(NEOp n);
    Object visit(NotOp n);
    Object visit(OrOp o);
    Object visit(ProcFunParamOp p);
    Object visit(ProcedureOp p);
    Object visit(ProgramOp p);
    Object visit(ReadStatement r);
    Object visit(Real_const r);
    Object visit(ReturnStatement r);
    Object visit(String_const s);
    Object visit(True_const t);
    Object visit(UminusOp u);
    Object visit(VarDeclOp v);
    Object visit(WhileStatement w);
    Object visit(WriteStatement w);
    Object visit(ProcedureExpression p);
    Object visit(ProcCallOp p);
    Object visit(IOArg ioArg);
    Object visit(ElseOp elseOp);

    Object visit(IterOp iterOp);

    Object visit(IdentifierExpression ie);
}
