package Repository;



import Model.MyException;
import Model.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

public class MemoryRepo implements IRepository {

    List<PrgState> prgList;
    String logFilePath;
    int index = 0;

    public MemoryRepo(List<PrgState> prgList, String logFilePath) {
        this.prgList = prgList;
        this.logFilePath = logFilePath;
    }

    @Override
    public void setPrgList(List<PrgState> list) throws MyException {
        this.prgList = list;
    }

    @Override
    public List<PrgState> getPrgList() {
        return this.prgList;
    }

    @Override
    public void logPrgStateExec(PrgState prg, String Extra) throws MyException {
        PrintWriter logFile =null;
        try{
            logFile= new PrintWriter(
                new BufferedWriter(
                new FileWriter(logFilePath, true)));
            logFile.print(Extra);
            if(prg!=null)
                logFile.println(prg.toString());

        } catch (Exception e) {
            throw new MyException(e.getMessage());
        }
        finally{
            logFile.close();
        }
        
    }

    @Override
    public void WriteToFile(String s) throws MyException{
        PrintWriter logFile =null;
        try{
            logFile= new PrintWriter(
                new BufferedWriter(
                new FileWriter(logFilePath, true)));

            logFile.println(s.toString());

        } catch (Exception e) {
            throw new MyException(e.getMessage());
        }
        finally{
            logFile.close();
        }
    }


}
