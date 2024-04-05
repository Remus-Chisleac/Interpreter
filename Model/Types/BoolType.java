package Model.Types;

import Model.Values.BoolValue;
import Model.Values.IValue;

public class BoolType implements Type {
    public boolean equals(Object another) {
        return (another instanceof BoolType);
    }

    public String toString() {
        return "boolean";
    }

    @Override
    public IValue defaultValue() {
        return new BoolValue(false);
    }
}
