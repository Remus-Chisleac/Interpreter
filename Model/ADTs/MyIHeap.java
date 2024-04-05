package Model.ADTs;

import java.util.Map;

import Model.MyException;


public interface MyIHeap<TKey, TVal> extends Clearable {
    public TKey put(TVal val);
    public void update(TKey key, TVal val) throws MyException;
    public TVal get(TKey key) throws MyException;

    public void remove(TKey key) throws MyException;

    public boolean isDefined(TKey key);

    public int size();
    public boolean isEmpty();

    public void set(Map<Integer,TVal> heap);
    public Map<Integer,TVal> getContent();
    public Integer[] keySet();
}
