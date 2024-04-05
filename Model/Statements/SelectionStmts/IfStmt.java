package Model.Statements.SelectionStmts;

import Model.MyException;
import Model.PrgState;
import Model.ADTs.MyIDictionary;
import Model.Expressions.IExp;
import Model.Statements.IStmt;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.BoolValue;

public class IfStmt implements IStmt {
    IExp exp;
    IStmt thenS;
    IStmt elseS;

    public IfStmt(IExp e, IStmt t, IStmt el) {
        exp = e;
        thenS = t;
        elseS = el;
    }

    @Override
    public String toString() {
        return "IF(" + exp.toString() + ") THEN(" + thenS.toString()
                + ")ELSE(" + elseS.toString() + ");";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {

        if ((exp.eval(state.getSymTable(), state.getHeap()).getType()).equals(new BoolType())) {
            BoolValue val = (BoolValue) exp.eval(state.getSymTable(), state.getHeap());
            if (val.getVal()) {
                state.getExeStack().push(thenS);
            } else {
                state.getExeStack().push(elseS);
            }
            return null;
        } else {
            throw new MyException("<IF_STMT> Condition is not a boolean");
        }

    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typexp = exp.typecheck(typeEnv);
        if (!typexp.equals(new BoolType()))
            throw new MyException("[TCHK_IF_STMT] The condition of IF has not the type bool");

        thenS.typecheck(typeEnv.clone());
        elseS.typecheck(typeEnv.clone());
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new IfStmt(exp.deepCopy(), thenS.deepCopy(), elseS.deepCopy());
    }
}
