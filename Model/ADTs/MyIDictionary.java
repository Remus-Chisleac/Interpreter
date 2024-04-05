package Model.ADTs;

import java.util.concurrent.ConcurrentMap;
import java.util.Set;
import Model.MyException;

public interface MyIDictionary<TKey,TVal> extends Clearable {
    public void put(TKey key, TVal val);
    public void update(TKey key, TVal val) throws MyException;
    public TVal get(TKey key) throws MyException;

    public void remove(TKey key) throws MyException;

    public TVal lookup(TKey key) throws MyException;

    public boolean isDefined(TKey key);

    public int size();
    public boolean isEmpty();

    public void set(ConcurrentMap<TKey,TVal> dict);
    public ConcurrentMap<TKey,TVal> getContent();

    public MyIDictionary<TKey, TVal> clone();
    Set<ConcurrentMap.Entry<TKey, TVal>> entrySet();
    public Object keySet();
    
}
