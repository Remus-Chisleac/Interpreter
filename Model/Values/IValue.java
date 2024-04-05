package Model.Values;

import Model.Types.Type;

public interface IValue {
    Type getType();
    boolean equals(Object another);
    IValue deepCopy();
}
