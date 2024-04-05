package Model.Expressions;

import Model.Values.IValue;
import Model.MyException;
import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.Types.Type;

public class VarExp implements IExp {
    String id;

    public VarExp(String id) {
        this.id = id;
    }
    
    @Override
    public String toString() {
        return id;
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> tbl,MyIHeap<Integer,IValue> hp) throws MyException {
        return tbl.get(id);
    }

    @Override
    public Type typecheck(MyIDictionary<String,Type> typeEnv) throws MyException{
        return typeEnv.lookup(id);
    }

    @Override
    public IExp deepCopy() {
        return new VarExp(id);
    }
}
