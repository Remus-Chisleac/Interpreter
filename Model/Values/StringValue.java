package Model.Values;

import Model.Types.StringType;
import Model.Types.Type;

public class StringValue implements IValue {

    String val;

    public StringValue(String v) {
        val = v;
    }

    public String getVal() {
        return val;
    }

    public String toString() {
        return val;
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public boolean equals(Object another) {
        if (another instanceof StringValue anotherString) {
            return val.equals(anotherString.getVal());
        }
        return false;
    }

    @Override
    public IValue deepCopy() {
        return new StringValue(val);
    }
    
}
