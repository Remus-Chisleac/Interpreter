package Model.Statements.VarStmts;

import Model.MyException;
import Model.PrgState;
import Model.ADTs.MyIDictionary;
import Model.Statements.IStmt;
import Model.Types.Type;

public class VarDeclStmt implements IStmt {
    String name;
    Type type;

    public VarDeclStmt(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return type.toString() + " " + name + ";";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        state.getSymTable().put(name, type.defaultValue());
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        typeEnv.put(name, type);
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new VarDeclStmt(name, type);
    }

}