package Model.ADTs;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import Model.MyException;
import Model.Statements.IStmt;

public class MyStack<T> implements MyIStack<T>{

    Stack<T> stack;

    public MyStack(Stack<T> stk) {
        stack = stk;
    }

    @Override
    public T pop() {
        return stack.pop();
    }

    @Override
    public T peek() throws MyException{
        if(stack.isEmpty())
            throw new MyException("<STACK> Stack is empty!");
        return stack.peek();
    }

    @Override
    public void push(T v) {
        stack.push(v);
    }

    @Override
    public int size() {
        return stack.size();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public String toString(){
        String ret  = "";
        List<String> s = new ArrayList<String>();

        for(T value: stack){
            s.add(value.toString() + "\n");
        }
        

        for(int i=s.size()-1;i>=0;i--){
            ret+=s.get(i);
        }
        return ret;
    }

    @Override
    public void clear() {
        stack.clear();
    }

    @Override
    public IStmt[] getContent() {
        return stack.toArray(new IStmt[0]);
    }

    @Override
    public Stack<T> getStack() {
        return stack;
    }

    
}
