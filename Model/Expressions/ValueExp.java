package Model.Expressions;

import Model.Values.IValue;
import Model.MyException;
import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.Types.Type;

public class ValueExp implements IExp {
    IValue e;

    public ValueExp(IValue e) {
        this.e = e;
    }

    @Override
    public String toString() {
        return e.toString();
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> tbl,MyIHeap<Integer,IValue> hp) throws MyException {
        return e;
    }

    @Override
    public Type typecheck(MyIDictionary<String,Type> typeEnv) throws MyException{
        return e.getType();
    }

    @Override
    public IExp deepCopy() {
        return new ValueExp(e.deepCopy());
    }

}
