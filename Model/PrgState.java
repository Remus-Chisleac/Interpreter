package Model;

import Model.Values.IValue;

import java.io.BufferedReader;

import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.ADTs.MyIList;
import Model.ADTs.MyIStack;
import Model.Statements.IStmt;

public class PrgState {
    //? final? static? how to set the id?
    private static int crtId = 0;
    public int id;
    private MyIStack<IStmt> exeStack;
    private MyIHeap<Integer,IValue> heap;
    private MyIDictionary<String, IValue> symTable;
    private MyIList<IValue> out;
    private MyIDictionary<String, BufferedReader> fileTable;
    private IStmt originalProgram; // optional field, but good to have

    private synchronized void setId(){
        id = crtId++;
    }

    public PrgState (
        MyIStack<IStmt> stk, 
        MyIHeap<Integer,IValue> hp,
        MyIDictionary<String, IValue> symtbl, 
        MyIList<IValue> ot,
        MyIDictionary<String, BufferedReader> filetbl, 
        IStmt prg) 
    {
        setId();
        exeStack = stk;
        heap = hp;
        symTable = symtbl;
        out = ot;
        fileTable = filetbl;
        originalProgram = prg.deepCopy();// recreate the entire original prg
        stk.push(prg);
    }
//region getters and setters
    public int getId() {
        return id;
    }

    public MyIStack<IStmt> getExeStack() {
        return exeStack;
    }

    public MyIHeap<Integer,IValue> getHeap() {
        return heap;
    }

    public MyIDictionary<String, IValue> getSymTable() {
        return symTable;
    }

    public MyIList<IValue> getOut() {
        return out;
    }

    public MyIDictionary<String, BufferedReader> getFileTable() {
        return fileTable;
    }

    public IStmt getOriginalProgram() {
        return originalProgram;
    }

    public void setId(int id) {
        if(this.id == -1)
            this.id = id;
    }

    public void setExeStack(MyIStack<IStmt> stk) {
        exeStack = stk;
    }

    public void setHeap(MyIHeap<Integer,IValue> hp) {
        heap = hp;
    }

    public void setSymTable(MyIDictionary<String, IValue> symtbl) {
        symTable = symtbl;
    }

    public void setOut(MyIList<IValue> ot) {
        out = ot;
    }

    public void setFileTable(MyIDictionary<String, BufferedReader> ft) {
        fileTable = ft;
    }

    public void setOriginalProgram(IStmt prg) {
        originalProgram = prg;
    }
//endregion

    public boolean isNotCompleted(){
        return !exeStack.isEmpty();
    }

    public PrgState executeOneStep() throws MyException{
        if(getExeStack().isEmpty())
            throw new MyException("<PRG_STATE>PrgState stack is empty");
        IStmt crtStmt=getExeStack().pop();
        return crtStmt.execute(this);
    }

    public String toString2(){
        String Ret="{";

        Ret+="\tID: ";
        Ret+=id;
        Ret+="\n";

        Ret+="\t-Heap-\n";
        Ret+=heap.toString();

        Ret+="\t-ExeStack-\n";
        Ret+=exeStack.toString();

        Ret+="\t-SymTable:\n";
        Ret+=symTable.toString();

        Ret+="\t-Out-\n";
        Ret+=out.toString();

        Ret+="\t-FileTable-\n";
        Ret+=fileTable.toString();

        Ret+="}";
        return Ret;

    }

    public String toString(){
        String Ret="{",Temp="";

        Ret+="\tID: ";
        Ret+=id;
        Ret+="\n";

        Temp = exeStack.toString();
        if(Temp.length()>0){
            Ret+="\t-ExeStack-\n";
            Ret+=Temp;
        }
        Temp=heap.toString();
        if(Temp.length()>0){
            Ret+="\t-Heap-\n";
            Ret+=Temp;
        }
        Temp = symTable.toString();
        if(Temp.length()>0){
            Ret+="\t-SymTable-\n";
            Ret+=Temp;
        }
        Temp = fileTable.toString();
        if(Temp.length()>0){
            Ret+="\t-FileTable-\n";
            Ret+=Temp;
        } 
        Temp = out.toString();
        if(Temp.length()>0){
            Ret+="\t-Out-\n";
            Ret+=Temp;
        }
        

        Ret+="}";
        return Ret;

    }

    public void reset() {
        exeStack.clear();
        heap.clear();
        symTable.clear();
        out.clear();
        fileTable.clear();
        IStmt prg = originalProgram.deepCopy();
        exeStack.push(prg);
    }

}
