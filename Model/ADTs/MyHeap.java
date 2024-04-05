package Model.ADTs;

import java.util.Map;
import Model.MyException;

public class MyHeap<TKey,TVal> implements MyIHeap<Integer,TVal> {

    Map<Integer,TVal> dictionary;
    int nextFree = 1;

    public MyHeap(Map<Integer,TVal> dict) {
        dictionary = dict;
    }

    public int getNextFree(){
        return nextFree;
    }

    @Override
    public Integer put(TVal val) {
        dictionary.put(nextFree, val);
        nextFree++;
        return nextFree - 1;
    }

    @Override
    public void update(Integer key, TVal val) throws MyException {
        if(!dictionary.containsKey(key))
            throw new MyException("<HEAP> Key not found in dictionary!");
        dictionary.put(key, val);
    }

    @Override
    public TVal get(Integer key) throws MyException {
        if(!dictionary.containsKey(key))
            throw new MyException("<HEAP> Key not found in dictionary!");
        return dictionary.get(key);
    }

    @Override
    public void remove(Integer key) throws MyException{
        if(!dictionary.containsKey(key))
            throw new MyException("<HEAP> Key not found in dictionary!");
        dictionary.remove(key);
    }

    @Override
    public boolean isDefined(Integer key) {
        return dictionary.containsKey(key);
    }

    @Override
    public int size() {
        return dictionary.size();
    }

    @Override
    public boolean isEmpty() {
        return dictionary.isEmpty();
    }

    @Override
    public String toString(){
        String s = "";
        for(Integer key : dictionary.keySet())
            s += key.toString() + " -> " + dictionary.get(key).toString() + "\n";
        return s;
    }

    @Override
    public void clear() {
        dictionary.clear();
    }

    @Override
    public void set(Map<Integer, TVal> heap) {
        dictionary = heap;
    }

    @Override
    public Map<Integer, TVal> getContent() {
        return dictionary;
    }

    @Override
    public Integer[] keySet() {
        return dictionary.keySet().toArray(new Integer[0]);
    }
    
}
