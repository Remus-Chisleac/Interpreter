package Model.Statements.ThreadStmts;

import java.io.BufferedReader;
import java.util.Stack;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import Model.MyException;
import Model.PrgState;
import Model.ADTs.MyDictionary;
import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.ADTs.MyIList;
import Model.ADTs.MyIStack;
import Model.ADTs.MyStack;
import Model.Statements.IStmt;
import Model.Types.Type;
import Model.Values.IValue;

public class ForkStmt implements IStmt {

    IStmt stmt;

    public ForkStmt(IStmt stmt) {
        this.stmt = stmt;
    }

    public String toString() {
        return "fork(" + stmt.toString() + ");";
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        //return the created prg state

        MyIHeap<Integer, IValue> heap =state.getHeap();
        MyIStack<IStmt> stk = new MyStack<IStmt>(new Stack<IStmt>());
        MyIList<IValue> ot = state.getOut();
        ConcurrentMap<String, IValue> dic = new ConcurrentHashMap<String, IValue>();
        dic = state.getSymTable().entrySet()
                    .stream()
                    .collect(Collectors.toConcurrentMap(e -> e.getKey(), e -> e.getValue().deepCopy()));
        MyIDictionary<String, IValue> symtbl = new MyDictionary<String, IValue>(dic);
        //symtbl = state.getSymTable().ToDictionary(Function(kv) kv.Key,Function(kv) kv.Value.Copy);
        MyIDictionary<String, BufferedReader> filetbl = state.getFileTable();
        return new PrgState(stk, heap, symtbl, ot, filetbl, stmt);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        stmt.typecheck(typeEnv);
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new ForkStmt(stmt.deepCopy());
    }
    
}
