package Model.Statements.FileStmts;

import Model.MyException;
import Model.PrgState;
import Model.ADTs.MyIDictionary;
import Model.Expressions.IExp;
import Model.Statements.IStmt;
import Model.Types.IntType;
import Model.Types.StringType;
import Model.Types.Type;
import Model.Values.IValue;
import Model.Values.IntValue;
import Model.Values.StringValue;

public class ReadFileStmt implements IStmt {

    IExp exp;
    IExp varName;

    public ReadFileStmt(IExp e, IExp v) {
        exp = e;
        varName = v;
    }

    @Override
    public String toString() {
        return "readFile(" + exp.toString() + ", " + varName.toString() + ");";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {

        if (!state.getSymTable().isDefined(varName.toString()))
            throw new MyException("<READ_FILE_STMT> Variable is not defined");

        MyIDictionary<String, IValue> symTbl = state.getSymTable();
        IValue TypeChecker = exp.eval(symTbl,state.getHeap());

        if (!TypeChecker.getType().equals(new StringType()))
            throw new MyException("<READ_FILE_STMT> Expression is not a string");

        StringValue file = (StringValue) TypeChecker;

        if (!state.getFileTable().isDefined(file.getVal()))
            throw new MyException("<READ_FILE_STMT> File is not opened");

        String line = null;
        try {
            line = state.getFileTable().get(file.getVal()).readLine();
        } catch (Exception e) {
            throw new MyException("<READ_FILE_STMT> File does not exist");
        }
        IValue val;
        if (line == null)
            val = new IntValue(0);
        else
            try {
                val = new IntValue(Integer.parseInt(line));
            } catch (Exception e) {
                throw new MyException("<READ_FILE_STMT> File does not contain an integer");
            }

        symTbl.update(varName.toString(), val);

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String,Type> typeEnv) throws MyException {
        Type typexp = exp.typecheck(typeEnv);
        Type typvar = varName.typecheck(typeEnv);
        if (!typexp.equals(new StringType()))
            throw new MyException("[TCHK_READ_FILE_STMT] The condition of READ_FILE has not the type string1");
        if (!typvar.equals(new IntType()))
            throw new MyException("[TCHK_READ_FILE_STMT] The condition of READ_FILE has not the type string2");

        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new ReadFileStmt(exp.deepCopy(), varName);
    }

}
