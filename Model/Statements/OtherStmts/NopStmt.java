package Model.Statements.OtherStmts;

import Model.MyException;
import Model.PrgState;
import Model.ADTs.MyIDictionary;
import Model.Statements.IStmt;

public class NopStmt implements IStmt{
    public String toString() {
        return "nop;";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        return null;
    }

    @Override
    public MyIDictionary<String, Model.Types.Type> typecheck(MyIDictionary<String, Model.Types.Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new NopStmt();
    }

}
