package Model.Expressions;

import Model.MyException;
import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.IValue;
import Model.Values.IntValue;

public class RelationExp implements IExp {
    IExp e1;
    IExp e2;
    String op;

    // ....
    public RelationExp (String op, IExp ex1, IExp ex2) {
        this.e1 = ex1;
        this.e2 = ex2;
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
        if (v1.getType().equals(new IntType())) {
            v2 = e2.eval(tbl,hp);
            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;
                int n1, n2;
                n1 = i1.getVal();
                n2 = i2.getVal();

                switch (op) {
                    case "<":
                        return new BoolValue(n1 < n2);
                    case "<=":
                        return new BoolValue(n1 <= n2);
                    case "==":
                        return new BoolValue(n1 == n2);
                    case "!=":
                        return new BoolValue(n1 != n2);
                    case ">":
                        return new BoolValue(n1 > n2);
                    case ">=":
                        return new BoolValue(n1 >= n2);
                    default:
                        throw new MyException("<RELATION_EXP> Invalid operator " + op );
                }

            } else
                throw new MyException("<RELATION_EXP> Second operand is not a integer "+ v2.toString());
        } else
            throw new MyException("<RELATION_EXP> First operand is not a integer " + v1.toString());
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ1, typ2;
        typ1 = e1.typecheck(typeEnv);
        typ2 = e2.typecheck(typeEnv);

        if (!typ1.equals(new IntType())) 
            throw new MyException("[TCHK_RELATION_EXP] Second operand is not an integer");
        
        if (!typ2.equals(new IntType())) 
            throw new MyException("[TCHK_RELATION_EXP]  First operand is not an integer");

        return new BoolType();
            
    }

    @Override
    public IExp deepCopy() {
        return new RelationExp(op, e1.deepCopy(), e2.deepCopy());
    }
}
