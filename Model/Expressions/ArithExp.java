package Model.Expressions;

import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IValue;
import Model.Values.IntValue;
import Model.MyException;
import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;

public class ArithExp implements IExp {
    IExp e1;
    IExp e2;
    char op;

    public ArithExp(char op, IExp e1, IExp e2) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }

    @Override
    public String toString() {
        return e1.toString() + " " + op + " " + e2.toString();
    }

    @Override
    public IValue eval(MyIDictionary<String, IValue> tbl, MyIHeap<Integer, IValue> hp) throws MyException {
        IValue v1, v2;
        v1 = e1.eval(tbl, hp);
        if (v1.getType().equals(new IntType())) {
            v2 = e2.eval(tbl, hp);
            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;
                int n1, n2;
                n1 = i1.getVal();
                n2 = i2.getVal();

                switch (op) {
                    case '+':
                        return new IntValue(n1 + n2);
                    case '-':
                        return new IntValue(n1 - n2);
                    case '*':
                        return new IntValue(n1 * n2);
                    case '/':
                        if (n2 == 0)
                            throw new MyException("<ARITHMETIC_EXP> Division by zero");
                        else
                            return new IntValue(n1 / n2);
                }
            } else
                throw new MyException("<ARITHMETIC_EXP> Second operand is not an integer");
        } else
            throw new MyException("<ARITHMETIC_EXP> First operand is not an integer");
        return null;
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ1, typ2;
        typ1 = e1.typecheck(typeEnv);
        typ2 = e2.typecheck(typeEnv);

        if (!typ1.equals(new IntType())) 
            throw new MyException("[TCHK_ARITHMETIC_EXP] Second operand is not an integer");
        
        if (!typ2.equals(new IntType())) 
            throw new MyException("[TCHK_ARITHMETIC_EXP]  First operand is not an integer");

        return new IntType();
            
    }

    @Override
    public IExp deepCopy() {
        return new ArithExp(op, e1.deepCopy(), e2.deepCopy());
    }
}
