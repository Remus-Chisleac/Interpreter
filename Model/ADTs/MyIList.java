package Model.ADTs;

import java.util.List;

import Model.MyException;

public interface MyIList<T> extends Clearable {
    public void add(T v);
    public void add(int index, T v) throws MyException;
    public void addAll(List<T> list) throws MyException;
    public T get(int index) throws MyException;
    public int indexOf(T v) throws MyException;
    public boolean contains(T v);
    public int size();
    public boolean isEmpty();
    public List getList();
    
}
