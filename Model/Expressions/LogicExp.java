package Model.Expressions;

import Model.Values.BoolValue;
import Model.Values.IValue;
import Model.MyException;
import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.Types.BoolType;
import Model.Types.Type;

class LogicExp implements IExp {
    IExp e1;
    IExp e2;
    String op;

    // ....
    LogicExp (String op, IExp e1, IExp e2) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op.toLowerCase();
    }

    @Override
    public String toString() {
        return e1.toString() + " " + op + " " + e2.toString();
    }

    @Override
    public IValue eval(MyIDictionary<String,IValue> tbl,MyIHeap<Integer,IValue> hp) throws MyException{
        IValue v1, v2;
        v1 = e1.eval(tbl,hp);
        if (v1.getType().equals(new BoolType())) {
            v2 = e2.eval(tbl,hp);
            if (v2.getType().equals(new BoolType())) {
                BoolValue b1 = (BoolValue) v1;
                BoolValue b2 = (BoolValue) v2;
                boolean n1, n2;
                n1 = b1.getVal();
                n2 = b2.getVal();

                switch (op) {
                    case "and":
                    case "&&":
                        return new BoolValue(n1 && n2);
                    case "or":
                    case "||":
                        return new BoolValue(n1 || n2);
                    default:
                        throw new MyException("<LOGIC_EXP> Invalid operator");
                }

            } else
                throw new MyException("<LOGIC_EXP> Second operand is not a boolean");
        } else
            throw new MyException("<LOGIC_EXP> First operand is not a boolean");
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ1, typ2;
        typ1 = e1.typecheck(typeEnv);
        typ2 = e2.typecheck(typeEnv);
        if (!typ1.equals(new BoolType())) 
            throw new MyException("[TCHK_LOGIC_EXP] First operand is not a boolean");

        if (!typ2.equals(new BoolType())) 
            throw new MyException("[TCHK_LOGIC_EXP] Second operand is not a boolean");

        return new BoolType();
    }

    @Override
    public IExp deepCopy() {
        return new LogicExp(op, e1.deepCopy(), e2.deepCopy());
    }
}
