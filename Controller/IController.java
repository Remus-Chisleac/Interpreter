package Controller;

import java.util.List;

import Model.MyException;
import Model.PrgState;
import javafx.scene.Node;

public interface IController {
    int getNextId();
    void oneStepAll(Node node) throws MyException;
    void executeAll(Node node) throws MyException;
    PrgState GetCrtPrg() throws MyException;
    List<PrgState> getPrgList();
    void WriteToFile(String s) throws MyException;
    void oneStepForAllPrg(List<PrgState> prgList) throws MyException, InterruptedException;
}
