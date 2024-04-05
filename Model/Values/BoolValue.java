package Model.Values;

import Model.Types.Type;
import Model.Types.BoolType;

public class BoolValue implements IValue {
    boolean val;

    public BoolValue(String val) {
        val.toLowerCase();
        if (val.equals("true")) {
            this.val = true;
        } else
            this.val = false;
        
    }

    public BoolValue(int val) {
        if (val == 1) {
            this.val = true;
        } else
            this.val = false;
    
    }

    public BoolValue(boolean val) {
        this.val = val;
    }

    public boolean getVal() {
        return val;
    }

    public String toString() {
        return Boolean.toString(val);

    }

    @Override
    public Type getType() {
        return new BoolType();
    }

    @Override
    public boolean equals(Object another) {
        if (another instanceof BoolValue) {
            BoolValue anotherBool = (BoolValue) another;
            return val == anotherBool.getVal();
        }
        return false;
    }

    @Override
    public IValue deepCopy() {
        return new BoolValue(val);
    }

}
