package Model.Statements.ExpresionStmts;

import Model.MyException;
import Model.PrgState;
import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIList;
import Model.Expressions.IExp;
import Model.Statements.IStmt;
import Model.Types.Type;
import Model.Values.IValue;

public class PrintStmt implements IStmt {
    IExp exp;

    // ....
    public PrintStmt(IExp e) {
        exp = e;
    }

    @Override
    public String toString() {
        return "print(" + exp.toString() + ");";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIList<IValue> out = state.getOut();
        out.add(exp.eval(state.getSymTable(), state.getHeap()));
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        exp.typecheck(typeEnv);
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new PrintStmt(exp.deepCopy());
    }
}
