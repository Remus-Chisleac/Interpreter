package View.PrgList;

import java.util.List;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

import Model.MyException;
import Model.PrgState;
import Model.ADTs.MyDictionary;
import Model.Statements.IStmt;
import Model.Types.Type;
import Model.Values.IValue;
import Repository.MemoryRepo;
import Model.ADTs.MyHeap;
import Model.ADTs.MyIDictionary;
import Model.ADTs.MyIHeap;
import Model.ADTs.MyIList;
import Model.ADTs.MyIStack;
import Model.ADTs.MyList;
import Model.ADTs.MyStack;

import View.Examples;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

import Controller.Controller;
import View.PrgExecutor.ExecutorController;

public class ListController {
    private ExecutorController executorController;

    public void setExecutorController(ExecutorController executorController) {
        this.executorController = executorController;
    }

    @FXML
    private ListView<IStmt> PrgListView;

    @FXML
    private Button LoadPrgButton;

    @FXML
    public void initialize() {
        List<IStmt> examples = Examples.getExamples();
        PrgListView.setItems(FXCollections.observableArrayList(examples));
        LoadPrgButton.setOnAction(actionEvent -> {
            int index = PrgListView.getSelectionModel().getSelectedIndex();
            if (index < 0)
                return;

            try {

                IStmt stmt = examples.get(index);
                stmt.typecheck(new MyDictionary<String, Type>(new ConcurrentHashMap<String, Type>()));
                MyIHeap<Integer, IValue> heap = new MyHeap<Integer, IValue>(new HashMap<Integer, IValue>());
                MyIStack<IStmt> stk = new MyStack<IStmt>(new Stack<IStmt>());
                MyIList<IValue> ot = new MyList<IValue>(new ArrayList<IValue>());
                MyIDictionary<String, IValue> symtbl = new MyDictionary<String, IValue>(
                        new ConcurrentHashMap<String, IValue>());
                MyIDictionary<String, BufferedReader> filetbl = new MyDictionary<String, BufferedReader>(
                        new ConcurrentHashMap<String, BufferedReader>());
                PrgState prg = new PrgState(stk, heap, symtbl, ot, filetbl, stmt);
                prg.reset();
                List<PrgState> prgList = List.of(prg);
                Controller ctrl = new Controller(new MemoryRepo(prgList, "log.txt"));
                executorController.setController(ctrl);

            } catch (MyException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                alert.setTitle("Typecheck error");
                alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                alert.showAndWait();
            }
        });
    }

}
