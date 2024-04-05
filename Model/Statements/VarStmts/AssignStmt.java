package Model.Statements.VarStmts;

import Model.Expressions.IExp;
import Model.Statements.IStmt;
import Model.Values.IValue;
import Model.Types.Type;
import Model.MyException;
import Model.PrgState;
import Model.ADTs.MyIDictionary;

public class AssignStmt implements IStmt {
    String id;
    IExp exp;

    public AssignStmt(String i, IExp e) {
        id = i;
        exp = e;
    }

    @Override
    public String toString() {
        return id + "=" + exp.toString() + ";";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, IValue> symTbl = state.getSymTable();
        if (symTbl.isDefined(id)) {
            IValue val;
            val = exp.eval(symTbl, state.getHeap());
            Type typId = (symTbl.get(id)).getType();
            if ((val.getType()).equals(typId)) {
                symTbl.update(id, val);
            } else {
                throw new MyException("<ASSIGN_STMT> Declared type of variable" + id
                        + " and type of the assigned expression do not match");
            }
        } else
            throw new MyException("<ASSIGN_STMT> The used variable" + id + " was not declared before");

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typevar = typeEnv.lookup(id);
        Type typexp = exp.typecheck(typeEnv);
        if (!typevar.equals(typexp))
            throw new MyException("[TCHK_ASSIGN_STMT] Right hand side and left hand side have different types");

        return typeEnv;      
    }

    @Override
    public IStmt deepCopy() {
        return new AssignStmt(id, exp.deepCopy());
    }
}
