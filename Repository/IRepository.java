package Repository;

import java.util.List;

import Model.MyException;
import Model.PrgState;

public interface IRepository {
    void setPrgList(List<PrgState> list) throws MyException;
    List<PrgState> getPrgList();
    void logPrgStateExec(PrgState prg, String Extra) throws MyException;
    void WriteToFile(String s) throws MyException;
}
