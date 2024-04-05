package Model.Statements.LoopStmts;

import Model.MyException;
import Model.PrgState;
import Model.ADTs.MyIDictionary;
import Model.Expressions.IExp;
import Model.Statements.IStmt;
import Model.Types.BoolType;
import Model.Values.BoolValue;
import Model.Values.IValue;

public class WhileStmt implements IStmt{

    IExp exp;

    public WhileStmt(IExp exp) {
        this.exp = exp;
    }

    public String toString() {
        return "while(" + exp.toString() + ") ";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IValue exp_val = exp.eval(state.getSymTable(), state.getHeap());
        if (!exp_val.getType().equals(new BoolType()))
            throw new MyException("<WHILE_STMT> Condition is not of type boolean!");
        BoolValue val = (BoolValue)exp_val;
        if (val.getVal() == true) {
            //state.getExeStack().pop();
            IStmt stmt = state.getExeStack().peek().deepCopy();
            //state.getExeStack().push(this);
            state.getExeStack().push(new WhileStmt(exp.deepCopy()));
            state.getExeStack().push(stmt);
        }
        else{
            state.getExeStack().pop();

        }
        return null;
    }

    @Override
    public MyIDictionary<String, Model.Types.Type> typecheck(MyIDictionary<String, Model.Types.Type> typeEnv) throws MyException {
        Model.Types.Type typexp = exp.typecheck(typeEnv);
        if (!typexp.equals(new BoolType()))
            throw new MyException("[TCHK_WHILE_STMT] The condition of WHILE has not the type bool");

        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new WhileStmt(exp.deepCopy());
    }
    
}
