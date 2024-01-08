package visitors;

import tree_structure.*;
import tree_structure.Expression.*;
import tree_structure.Statement.*;
import visitors.Visitor;

public class ExprEvalVisitor_OLD implements Visitor {
    @Override
    public Object visit(AddOp a) {
        int leftVal =
                (Integer) a.getLeft().accept(this);
        int rightVal =
                (Integer) a.getRight().accept(this);

        return leftVal + rightVal;
    }

    @Override
    public Object visit(AndOp a) {
        boolean leftVal =
                (Boolean) a.getLeft().accept(this);
        boolean rightVal =
                (Boolean) a.getRight().accept(this);

        return leftVal && rightVal;
    }

    @Override
    public Object visit(AssignStatement a) {
        return null;
    }

    @Override
    public Object visit(BodyOp b) {
        return null;
    }

    @Override
    public Object visit(DiffOp d) {
        int leftVal =
                (Integer) d.getLeft().accept(this);
        int rightVal =
                (Integer) d.getRight().accept(this);

        return leftVal - rightVal;
    }

    @Override
    public Object visit(DivOp d) {
        int leftVal =
                (Integer) d.getLeft().accept(this);
        int rightVal =
                (Integer) d.getRight().accept(this);

        return leftVal / rightVal;
    }

    @Override
    public Object visit(ElifOp e) {
        return null;
    }

    @Override
    public Object visit(EQOp e) {
        Expression leftVal =
                (Expression) e.getLeft().accept(this);
        Expression rightVal =
                (Expression) e.getRight().accept(this);

        return (leftVal == rightVal);
    }

    @Override
    public Object visit(False_const f) {
        return f.getValue();
    }

    @Override
    public Object visit(FunCallOp f) {
        return null;
    }

    @Override
    public Object visit(FunctionOp f) {
        return null;
    }

    @Override
    public Object visit(GEOp g) {
        int leftVal =
                (Integer) g.getLeft().accept(this);
        int rightVal =
                (Integer) g.getRight().accept(this);

        return (leftVal >= rightVal);
    }

    @Override
    public Object visit(GTOp g) {
        int leftVal =
                (Integer) g.getLeft().accept(this);
        int rightVal =
                (Integer) g.getRight().accept(this);

        return (leftVal > rightVal);
    }

    @Override
    public Object visit(Identifier i) {
        return null;
    }

    @Override
    public Object visit(IfStatement i) {
        return null;
    }

    @Override
    public Object visit(Integer_const i) {
        return i.getValue();
    }

    @Override
    public Object visit(LEOp l) {
        int leftVal =
                (Integer) l.getLeft().accept(this);
        int rightVal =
                (Integer) l.getRight().accept(this);

        return (leftVal <= rightVal);
    }

    @Override
    public Object visit(LTOp l) {
        int leftVal =
                (Integer) l.getLeft().accept(this);
        int rightVal =
                (Integer) l.getRight().accept(this);

        return (leftVal < rightVal);
    }

    @Override
    public Object visit(MulOp m) {
        int leftVal =
                (Integer) m.getLeft().accept(this);
        int rightVal =
                (Integer) m.getRight().accept(this);

        return leftVal * rightVal;
    }

    @Override
    public Object visit(NEOp n) {
        Expression leftVal =
                (Expression) n.getLeft().accept(this);
        Expression rightVal =
                (Expression) n.getRight().accept(this);

        return (leftVal != rightVal);
    }

    @Override
    public Object visit(NotOp n) {
        boolean value =
                (Boolean) n.getValue().accept(this);

        return !value;
    }

    @Override
    public Object visit(OrOp o) {
        boolean leftVal =
                (Boolean) o.getLeft().accept(this);
        boolean rightVal =
                (Boolean) o.getRight().accept(this);

        return leftVal || rightVal;
    }

    @Override
    public Object visit(ProcFunParamOp p) {
        return null;
    }

    @Override
    public Object visit(ProcedureOp p) {
        return null;
    }

    @Override
    public Object visit(ProgramOp p) {
        return null;
    }

    @Override
    public Object visit(ReadStatement r) {
        return null;
    }

    @Override
    public Object visit(Real_const r) {
        return r.getValue();
    }

    @Override
    public Object visit(ReturnStatement r) {
        return null;
    }

    @Override
    public Object visit(String_const s) {
        return s.getValue();
    }

    @Override
    public Object visit(True_const t) {
        return t.getValue();
    }

    @Override
    public Object visit(UminusOp u) {
        return null;
    }

    @Override
    public Object visit(VarDeclOp v) {
        return null;
    }

    @Override
    public Object visit(WhileStatement w) {
        return null;
    }

    @Override
    public Object visit(WriteStatement w) {
        return null;
    }

    @Override
    public Object visit(ProcedureExpression p) {
        return null;
    }

    @Override
    public Object visit(ProcCallOp p) {
        return null;
    }

    @Override
    public Object visit(IOArg ioArg) {
        return null;
    }

    @Override
    public Object visit(ElseOp elseOp) {
        return null;
    }

    @Override
    public Object visit(IterOp iterOp) {
        return null;
    }
}
