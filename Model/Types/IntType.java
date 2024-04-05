package Model.Types;

import Model.Values.IValue;
import Model.Values.IntValue;

public class IntType implements Type {
    public boolean equals(Object another) {
        return (another instanceof IntType);
    }

    public String toString() {
        return "int";
    }

    @Override
    public IValue defaultValue() {
        return new IntValue(0);
    }
}
