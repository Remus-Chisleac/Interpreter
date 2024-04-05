package Model.Values;

import Model.Types.Type;
import Model.Types.IntType;


public class IntValue implements IValue {
    private int val;

    public IntValue(int v) {
        val = v;
    }

    public int getVal() {
        return val;
    }

    public String toString() {
        return Integer.toString(val);
    }

    @Override
    public Type getType() {
        return new IntType();
    }
    @Override
    public boolean equals(Object another) {
        if (another instanceof IntValue anotherInt) {
            // IntValue anotherInt = (IntValue) another;
            return val == anotherInt.getVal();
        } else {
            return false;
        }
    }

    @Override
    public IValue deepCopy() {
        return new IntValue(val);
    }
}
