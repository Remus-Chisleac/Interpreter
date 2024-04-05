package Model.Expressions;

import Model.Values.IValue;

import Model.MyException;
import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.Types.Type;

public interface IExp {
    IValue eval(MyIDictionary<String,IValue> tbl, MyIHeap<Integer,IValue> hp) throws MyException;
    Type typecheck(MyIDictionary<String,Type> typeEnv) throws MyException;
    IExp deepCopy();
}
