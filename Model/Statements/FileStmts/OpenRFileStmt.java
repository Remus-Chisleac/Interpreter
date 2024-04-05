package Model.Statements.FileStmts;

import java.io.BufferedReader;
import java.io.FileReader;

import Model.MyException;
import Model.PrgState;
import Model.ADTs.MyIDictionary;
import Model.Expressions.IExp;
import Model.Statements.IStmt;
import Model.Types.StringType;
import Model.Types.Type;
import Model.Values.StringValue;
import Model.Values.IValue;

public class OpenRFileStmt implements IStmt {

    IExp exp;

    public OpenRFileStmt(IExp e) {
        exp = e;
    }

    @Override
    public String toString() {
        return "openRFile(" + exp.toString() + ");";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IValue typeCheck = exp.eval(state.getSymTable(),state.getHeap());
        if(!typeCheck.getType().equals(new StringType()))
            throw new MyException("<OPEN_RFILE_STMT> File name is not a string");

        StringValue file = (StringValue) typeCheck;
        if(state.getFileTable().isDefined(file.getVal()))
            throw new MyException("<OPEN_RFILE_STMT> File already opened");

        try{
            BufferedReader buff = new BufferedReader (new FileReader(file.getVal()));
            state.getFileTable().put(file.getVal(), buff);
        }
        catch(Exception e){
            throw new MyException("<OPEN_RFILE_STMT> File does not exist");
        }

        return null;

    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String,Type> typeEnv) throws MyException {
        Type typexp = exp.typecheck(typeEnv);
        if (!typexp.equals(new StringType()))
            throw new MyException("[TCHK_OPEN_RFILE_STMT] The condition of OPEN_RFILE has not the type string");

        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new OpenRFileStmt(exp.deepCopy());
    }
    
}
