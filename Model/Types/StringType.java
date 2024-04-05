package Model.Types;

import Model.Values.IValue;
import Model.Values.StringValue;

public class StringType implements Type {

    public boolean equals(Object another) {
        return (another instanceof StringType);
    }

    public String toString() {
        return "string";
    }
    
    @Override
    public IValue defaultValue() {
        return new StringValue("");
    }
    
}
