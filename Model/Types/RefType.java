package Model.Types;

import Model.Values.IValue;
import Model.Values.RefValue;

public class RefType implements Type {
    Type inner;

    public RefType(Type inner) {
        this.inner = inner;
    }

    public Type getInner() {
        return inner;
    }

    public boolean equals(Object another) {
        if (another instanceof RefType ref)
            return inner.equals(ref.getInner());
        else
            return false;
    }

    public String toString() {
        return "Ref(" + inner.toString() + ")";
    }

    public IValue defaultValue() {
        return new RefValue(0, inner);
    }
}