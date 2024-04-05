package Controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import Model.MyException;
import Model.PrgState;
import Model.Values.IValue;
import Model.Values.RefValue;
import Repository.IRepository;
import javafx.event.Event;
import javafx.scene.Node;

public class Controller implements IController {

    IRepository repo;
    int crtprg = 0;
    int id = 1;
    ExecutorService executor;

    public Controller(IRepository repo) {
        this.repo = repo;
    }

    @Override
    public int getNextId() {
        return id++;
    }

    void GarbageCollector(List<PrgState> prgList) {
        List<Integer> addr = prgList.stream()
                .map(p -> getReachableAddr(p.getSymTable().getContent().values(), p.getHeap().getContent()))
                .reduce(List.of(), (l1, l2) -> Stream.concat(l1.stream(), l2.stream())
                        .distinct()
                        .collect(Collectors.toList()));

        Map<Integer, IValue> newHeap = newHeap(addr, prgList.get(0).getHeap().getContent());
        prgList.forEach(p -> p.getHeap().set(newHeap));
    }

    Map<Integer, IValue> newHeap(List<Integer> ReachableAddr, Map<Integer, IValue> heap) {
        return heap.entrySet().stream()
                .filter(e -> ReachableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    Stream<Integer> getRecursiveAddrFromAddr(Map<Integer, IValue> heap, int addr) {
        if (!(heap.containsKey(addr)))
            return Stream.empty();
        if (!(heap.get(addr) instanceof RefValue v))
            return Stream.of(addr);
        return Stream.concat(Stream.of(addr), getRecursiveAddrFromAddr(heap, v.getAddr()));
    }

    List<Integer> getReachableAddr(Collection<IValue> symTableValues, Map<Integer, IValue> heap) {
        return Stream.concat(
                symTableValues.stream()
                        .filter(v -> v instanceof RefValue)
                        .map(v -> ((RefValue) v).getAddr()),
                symTableValues.stream()
                        .filter(v -> v instanceof RefValue)
                        .map(v -> ((RefValue) v).getAddr())
                        .flatMap(addr -> getRecursiveAddrFromAddr(heap, addr)))
                .distinct()
                .collect(Collectors.toList());

    }

    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList) {
        return inPrgList.stream()
                .filter(PrgState::isNotCompleted)
                .collect(Collectors.toList());
    }

    void log(List<PrgState> prgList,String Extra) {
        if(prgList==null)
            try {
                repo.logPrgStateExec(null,Extra);
            } catch (MyException e) {
                System.out.println(e.getMessage());
            }
        else
            prgList.forEach(prg -> {
                try {
                    repo.logPrgStateExec(prg,Extra);
                } catch (MyException e) {
                    System.out.println(e.getMessage());
                }
            });
    }

    public void oneStepForAllPrg(List<PrgState> prgList) throws MyException {
        // before the execution, print the PrgState List into the log file
        log(null,"\nBefore\n");
        log(prgList,"");

        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>) (() -> {
                    return p.executeOneStep();
                }))
                .collect(Collectors.toList());

        List<PrgState> newPrgList;
        try {
            newPrgList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (ExecutionException | InterruptedException e) {
                            System.out.println(e.getMessage());
                        }
                        return null;
                    })
                    .filter(p -> p != null)
                    // .map(p->{
                    //     if(p.getId()==-1) 
                    //         p.setId(getNextId()); 
                    //     return p;
                    // })
                    .collect(Collectors.toList());}catch(

    InterruptedException e)
    {
        System.out.println(e.getMessage());
        return;
    }

    prgList.addAll(newPrgList);
    log(null,"\nAfter\n");
    log(prgList,"");
    log(null,"\n----------------------------------------------------------------------------------------------------\n");

    repo.setPrgList(prgList);

    }

    @Override
    public void oneStepAll(Node node) throws MyException{
        executor = Executors.newFixedThreadPool(2);

        List<PrgState> prgList = removeCompletedPrg(repo.getPrgList());

        oneStepForAllPrg(prgList);
        GarbageCollector(prgList);

        //call the method populate from ExecutorController

        Event myEvent = new MyEvent(MyEvent.Populate);
        node.fireEvent(myEvent);

        prgList = removeCompletedPrg(repo.getPrgList());
        executor.shutdownNow();

        repo.setPrgList(prgList);
    }

    @Override
    public void executeAll(Node node) throws MyException {
        executor = Executors.newFixedThreadPool(2);
        // remove the completed programs
        List<PrgState> prgList = removeCompletedPrg(repo.getPrgList());
        while (prgList.size() > 0) {
            oneStepForAllPrg(prgList);
            
            // remove the completed programs
            GarbageCollector(prgList);

            node.fireEvent(new MyEvent(MyEvent.Populate));

            prgList = removeCompletedPrg(repo.getPrgList());
        }
        executor.shutdownNow();
        // HERE the repository still contains at least one Completed Prg
        // and its List<PrgState> is not empty. Note that oneStepForAllPrg calls the
        // method
        // setPrgList of repository in order to change the repository
        // update the repository state
        repo.setPrgList(prgList);
    }

    public List<PrgState> getPrgList() {
        return repo.getPrgList();
    }


    @Override
    public PrgState GetCrtPrg() throws MyException {
        List<PrgState> list = repo.getPrgList();
        return list.get(crtprg);
    }

    @Override
    public void WriteToFile(String s) throws MyException {
        repo.WriteToFile(s);
    }

}
