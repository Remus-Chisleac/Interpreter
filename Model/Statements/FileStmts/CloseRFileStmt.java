package Model.Statements.FileStmts;

import java.io.BufferedReader;

import Model.MyException;
import Model.PrgState;
import Model.ADTs.MyIDictionary;
import Model.Expressions.IExp;
import Model.Statements.IStmt;
import Model.Types.StringType;
import Model.Types.Type;
import Model.Values.IValue;
import Model.Values.StringValue;

public class CloseRFileStmt implements IStmt {

    IExp exp;

    public CloseRFileStmt(IExp e) {
        exp = e;
    }

    public String toString() {
        return "closeRFile(" + exp.toString() + ");";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IValue typeCheck = exp.eval(state.getSymTable(),state.getHeap());
        if(!typeCheck.getType().equals(new StringType()))
            throw new MyException("<CLOSE_RFILE_STMT> File name is not a string");

        StringValue file = (StringValue) typeCheck;
        if(!state.getFileTable().isDefined(file.getVal()))
            throw new MyException("<CLOSE_RFILE_STMT> File is not opened");

        BufferedReader buff = state.getFileTable().get(file.getVal());
        try{
            buff.close();
        }
        catch(Exception e){
            throw new MyException("<CLOSE_RFILE_STMT> File does not exist");
        }
        state.getFileTable().remove(file.getVal());

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typexp = exp.typecheck(typeEnv);
        if (!typexp.equals(new StringType()))
            throw new MyException("[TCHK_CLOSE_RFILE_STMT] The condition of CLOSE_RFILE has not the type string");

        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new CloseRFileStmt(exp.deepCopy());
    }
    
}
