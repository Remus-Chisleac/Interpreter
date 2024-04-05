package Model.ADTs;


import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.Set;

import Model.MyException;

public class MyDictionary<TKey ,TVal > implements MyIDictionary<TKey,TVal> {

    ConcurrentMap<TKey,TVal> dictionary;


    public MyDictionary(ConcurrentMap<TKey,TVal> dict) {
        dictionary = dict;
    }

    @Override
    public void put(TKey key, TVal val){
        dictionary.put(key, val);
    }

    @Override
    public void update(TKey key, TVal val) throws MyException {
        if(!dictionary.containsKey(key))
            throw new MyException("<DICTIONARY> Key not found in dictionary!");
        dictionary.put(key, val);
    }

    @Override
    public TVal get(TKey key) throws MyException {
        if(!dictionary.containsKey(key))
            throw new MyException("<DICTIONARY> Key not found in dictionary!");
        return dictionary.get(key);
    }

    @Override
    public void remove(TKey key) throws MyException{
        if(!dictionary.containsKey(key))
            throw new MyException("<DICTIONARY> Key not found in dictionary!");
        dictionary.remove(key);
    }

    @Override
    public TVal lookup(TKey key) throws MyException {
        if(!dictionary.containsKey(key))
            throw new MyException("<DICTIONARY> Key not found in dictionary!");
        return dictionary.get(key);
    }

    @Override
    public boolean isDefined(TKey key) {
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
        for(TKey key : dictionary.keySet())
            s += key.toString() + " -> " + dictionary.get(key).toString() + "\n";
        return s;
    }

    @Override
    public void clear() {
        dictionary.clear();
    }

    @Override
    public void set(ConcurrentMap<TKey, TVal> dict) {
        dictionary = dict;
    }

    @Override
    public ConcurrentMap<TKey, TVal> getContent() {
        return dictionary;
    }

    @Override
    public MyDictionary<TKey, TVal> clone() {
        ConcurrentMap<TKey, TVal> newDict = new ConcurrentHashMap<>();
        for (ConcurrentMap.Entry<TKey, TVal> entry : dictionary.entrySet()) {
            TKey keyClone = entry.getKey(); 
            TVal valueClone = entry.getValue();
            newDict.put(keyClone, valueClone);
        }
        MyDictionary<TKey, TVal> newMyDictionary = new MyDictionary<>(newDict);
        return newMyDictionary;
    }

    @Override
    public Set<ConcurrentMap.Entry<TKey, TVal>> entrySet() {
        return dictionary.entrySet();
    }
    
    @Override
    public Object keySet() {
        return dictionary.keySet();
    }


}

