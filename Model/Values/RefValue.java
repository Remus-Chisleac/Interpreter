package Model.Values;


import Model.Types.RefType;
import Model.Types.Type;

public class RefValue implements IValue {
    int address;
    Type locationType;

    public RefValue(int address, Type locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddr() {
        return address;
    }

    public Type getType() {
        return new RefType(locationType);
    }

    public String toString() {
        //if(address != 0)
        return "(" + Integer.toString(address) + ", " + locationType.toString() + ")";
        //return  "(" + Integer.toString(address) + ", " + "null" + ")";
    }

    @Override
    public boolean equals(Object another) {
        
        if (another instanceof RefValue ref) {
            return locationType == ref.getType();
        }
        return false;
        
    }

    @Override
    public IValue deepCopy() {
        return new RefValue(address, locationType);
    }


}
