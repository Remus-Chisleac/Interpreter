package Model.Statements;

import Model.MyException;
import Model.PrgState;
import Model.ADTs.MyIDictionary;
import Model.Types.Type;

public interface IStmt {
    PrgState execute(PrgState state) throws MyException;
    MyIDictionary<String,Type> typecheck(MyIDictionary<String,Type> typeEnv) throws MyException;
    IStmt deepCopy();
    String toString();
}
