package Model.Statements.HeapStmts;

import Model.MyException;
import Model.PrgState;
import Model.ADTs.MyIDictionary;
import Model.Expressions.IExp;
import Model.Statements.IStmt;
import Model.Types.RefType;
import Model.Values.IValue;
import Model.Values.RefValue;

public class NewHeapStmt implements IStmt {
    String var_name;
    IExp exp;

    public NewHeapStmt(String var_name, IExp exp) {
        this.var_name = var_name;
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "new(" + var_name + ", " + exp.toString() + ");";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IValue val = state.getSymTable().get(var_name);
        if (!(val.getType() instanceof RefType RefType))
            throw new MyException("<NEW_HEAP_STMT> Variable is not of type reference!");

        IValue val_exp = exp.eval(state.getSymTable(), state.getHeap());

        if (!val_exp.getType().equals(RefType.getInner()))
            throw new MyException("<NEW_HEAP_STMT> Type of expression and type of variable do not match!");

        int addr = state.getHeap().put(val_exp);
        state.getSymTable().update(var_name, new RefValue(addr, val_exp.getType()));

        return null;
    }

    @Override
    public MyIDictionary<String, Model.Types.Type> typecheck(MyIDictionary<String, Model.Types.Type> typeEnv) throws MyException {
        Model.Types.Type typevar = typeEnv.lookup(var_name);
        Model.Types.Type typexp = exp.typecheck(typeEnv);
        if (!(typevar instanceof RefType))
            throw new MyException("[TCHK_NEW_HEAP_STMT] Right hand side and left hand side have different types");

        RefType reft = (RefType) typevar;
        if (!typexp.equals(reft.getInner()))
            throw new MyException("[TCHK_NEW_HEAP_STMT] Right hand side and left hand side have different types");

        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new NewHeapStmt(var_name, exp.deepCopy());
    }

}
