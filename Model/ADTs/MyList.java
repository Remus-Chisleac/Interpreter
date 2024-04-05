package Model.ADTs;

import java.util.List;

import Model.MyException;

public class MyList<T> implements MyIList<T>{

    List<T> list;

    public MyList (List<T> list) {
        this.list = list;
    }

    @Override
    public void add(T v) {
        list.add(v);
    }

    @Override
    public void add(int index, T v) throws MyException {
        if(index<0 || index>=list.size())
            throw new MyException("<LIST> Index out of bounds!");
        list.add(index, v);
    }

    @Override
    public T get(int index) throws MyException {
        if(index<0 || index>=list.size())
            throw new MyException("<LIST> Index out of bounds!");
        return list.get(index);
    }

    @Override
    public int indexOf(T v) throws MyException{
        if(!list.contains(v))
            throw new MyException("<LIST> Value not found in list!");
        return list.indexOf(v);
    }

    @Override
    public boolean contains(T v) {
        return list.contains(v);
    }

    @Override
    public void addAll(List<T> list) throws MyException {
        if(list == null)
            throw new MyException("<LIST> Null list!");
        this.list.addAll(list);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }
    
    @Override
    public String toString(){
        String s = "";
        for(T e : list)
            s += e.toString() + ",";
        if(s.length()>0)
            s = s.substring(0, s.length()-1);
        s+="\n";
        return s;
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public List<T> getList() {
        return list;
    }


}
