package Model.Statements.HeapStmts;

import Model.MyException;
import Model.PrgState;
import Model.ADTs.MyIDictionary;
import Model.Expressions.IExp;
import Model.Statements.IStmt;
import Model.Types.RefType;
import Model.Values.IValue;
import Model.Values.RefValue;

public class WriteHeapStmt implements IStmt {

    private String var_name;
    private IExp exp;

    public WriteHeapStmt(String var_name, IExp exp) {
        this.var_name = var_name;
        this.exp = exp;
    }

    public String toString() {
        return "wH(" + var_name + ", " + exp.toString() + ");";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        if(!state.getSymTable().isDefined(var_name))
            throw new MyException("<WRITE_HEAP_STMT> Variable is not defined!");

        IValue val = state.getSymTable().get(var_name);
        if (!(val.getType() instanceof RefType ref_type))
            throw new MyException("<WRITE_HEAP_STMT> Variable is not of type reference!");
        
        RefValue ref_val = (RefValue)val;

        int addr = ref_val.getAddr();
        if(!state.getHeap().isDefined(addr))
            throw new MyException("<WRITE_HEAP_STMT> Address is not defined in the heap!");

        IValue exp_val = exp.eval(state.getSymTable(), state.getHeap());
        if(!(exp_val.getType().equals(ref_type.getInner())))
            throw new MyException("<WRITE_HEAP_STMT> Type of expression and type of variable do not match!");

        
        state.getHeap().update(addr, exp_val);

        return null; 
    }

    @Override
    public MyIDictionary<String, Model.Types.Type> typecheck(MyIDictionary<String, Model.Types.Type> typeEnv) throws MyException {
        Model.Types.Type typevar = typeEnv.lookup(var_name);
        Model.Types.Type typexp = exp.typecheck(typeEnv);
        if (!(typevar instanceof RefType))
            throw new MyException("[TCHK_WRITE_HEAP_STMT] Right hand side is not of type reference!");

        RefType reft = (RefType) typevar;
        if (!typexp.equals(reft.getInner()))
            throw new MyException("[TCHK_WRITE_HEAP_STMT] Right hand side and left hand side have different types");

        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new WriteHeapStmt(var_name, exp.deepCopy());
    }
    
}
