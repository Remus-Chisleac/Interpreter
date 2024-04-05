package Model.ADTs;

import java.util.Stack;

import Model.MyException;
import Model.Statements.IStmt;

public interface MyIStack<T> extends Clearable {

    T pop();
    T peek() throws MyException;

    void push(T v);

    int size();

    boolean isEmpty();

    Stack<T> getStack();
    IStmt[] getContent();

}
