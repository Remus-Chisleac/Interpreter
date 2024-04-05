package Model.Expressions;

import Model.MyException;
import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.Types.RefType;
import Model.Types.Type;
import Model.Values.IValue;
import Model.Values.RefValue;

public class ReadHeapExp implements IExp {

    private IExp exp;

    public ReadHeapExp(IExp exp) {
        this.exp = exp;
    }

    public String toString() {
        return "rH(" + exp.toString() + ")";
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> tbl, MyIHeap<Integer, IValue> hp) throws MyException {
        IValue exp_val = exp.eval(tbl, hp);

        if (!(exp_val.getType() instanceof RefType))
            throw new MyException("<READ_HEAP_EXP> Expression is not of type reference!");

        int addr = ((RefValue) exp_val).getAddr();

        if (!hp.isDefined(addr))
            throw new MyException("<READ_HEAP_EXP> Address is not defined in the heap!");

        return hp.get(addr);
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ = exp.typecheck(typeEnv);
        if (!(typ instanceof RefType)) 
            throw new MyException("[TCHK_READ_HEAP_EXP]the rH argument is not a Ref Type");

        RefType reft = (RefType) typ;
        return reft.getInner(); 
    }

    @Override
    public IExp deepCopy() {
        return new ReadHeapExp(exp.deepCopy());
    }

}
