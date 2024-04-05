package View.PrgExecutor;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Region;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

import Controller.Controller;
import Controller.MyEvent;
import Model.MyException;
import Model.PrgState;
import Model.ADTs.MyHeap;
import Model.ADTs.MyIHeap;
import Model.ADTs.MyIList;
import Model.ADTs.MyIStack;
import Model.ADTs.MyStack;
import Model.Statements.IStmt;
import Model.Values.IValue;

class Pair<T1, T2> {
    T1 first;
    T2 second;

    public Pair(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }
}

public class ExecutorController {

    private Controller controller;
    private Node node = new Node(){}; // dummy node to handle events

    public void setController(Controller controller) {
        this.controller = controller;
        Populate();
    }

    @FXML
    private TableView<Pair<Integer, IValue>> HeapTableView;
    @FXML
    private TableColumn<Pair<Integer, IValue>, Integer> HeapAddressColumn;
    @FXML
    private TableColumn<Pair<Integer, IValue>, String> HeapValueColumn;


    @FXML
    private ListView<String> OutputListView;
    @FXML
    private ListView<String> FileListView;

    @FXML
    private Label PrgStatesLabel;
    @FXML
    private ListView<Integer> PrgListView;

    @FXML
    private ListView<String> ExecStackListView;

    @FXML
    private TableView<Pair<String, IValue>> SymTableView;
    @FXML
    private TableColumn<Pair<String, IValue>, String> SymVarNameColumn;
    @FXML
    private TableColumn<Pair<String, IValue>, String> SymValueColumn;

    @FXML
    private Button RunOneStepButton;
    @FXML
    private Button ExecuteAllButton;





    @FXML
    public void initialize() {
        HeapAddressColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().first).asObject());
        HeapValueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().second.toString()));
        SymVarNameColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().first));
        SymValueColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().second.toString()));

        PrgListView.setOnMouseClicked(mouseEvent -> Populate());

        
        node.addEventHandler(MyEvent.Populate, event -> Populate());
        
        RunOneStepButton.setOnAction(actionEvent -> {

            if(controller == null){
                Alert alert = new Alert(Alert.AlertType.ERROR, "No program selected", ButtonType.OK);
                alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                alert.show();
                return;
            }

            Boolean isCompleted = controller.getPrgList().size() == 0;
            if (isCompleted) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Program has finished", ButtonType.OK);
                alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                alert.show();
                return;
            }

            try {
                controller.oneStepAll(node);
                Populate();
            } catch (MyException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                alert.showAndWait();
            }
        });

        ExecuteAllButton.setOnAction(actionEvent -> {
            
            if(controller == null){
                Alert alert = new Alert(Alert.AlertType.ERROR, "No program selected", ButtonType.OK);
                alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                alert.show();
                return;
            }

            Boolean isCompleted = controller.getPrgList().size() == 0;
            if (isCompleted) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Program has finished", ButtonType.OK);
                alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                alert.show();
            }

            try {
                controller.executeAll(node);
                Populate();
            } catch (MyException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                alert.showAndWait();
            }
        });
    }

    private void Populate(){
        populatePrgStates();
        populateExecutionStack();
        populateSymbolTable();
        populateOutput();
        populateFileTable();
        populateHeap();
    }

    private void populateHeap() {
        MyIHeap heap;
        if (controller.getPrgList().size() > 0)
            heap = controller.getPrgList().get(0).getHeap();
        else 
            heap = new MyHeap<Integer, IValue>(new HashMap<Integer, IValue>());
        List<Pair<Integer, IValue>> heapTableList = new ArrayList<>();
        

        for (Integer key : heap.keySet()) {
            try {
                heapTableList.add(new Pair<Integer, IValue>(key, (IValue)heap.get(key)));
            } catch (MyException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                alert.showAndWait();
            }
        }

        HeapTableView.setItems(FXCollections.observableList(heapTableList));
        HeapTableView.refresh();
    }

    private void populatePrgStates() {
        List<PrgState> programStates = controller.getPrgList();
        var idList = programStates.stream().map(ps -> ps.id).collect(Collectors.toList());
        PrgListView.setItems(FXCollections.observableList(idList));
        PrgStatesLabel.setText("PrgStates " + programStates.size());
    }

    private void populateFileTable() {
        ArrayList<String> files;
        if (controller.getPrgList().size() > 0)
            files = new ArrayList<>(controller.getPrgList().get(0).getFileTable().entrySet().stream().map(e -> e.getKey()).collect(Collectors.toList()));
        else 
            files = new ArrayList<>();
        FileListView.setItems(FXCollections.observableArrayList(files));
    }

    private void populateOutput() {
        MyIList<IValue> output;
        if (controller.getPrgList().size() > 0)
            output = controller.getPrgList().get(0).getOut();
        else 
            return;

        List<String> outputListAsString = new ArrayList<>();
        for (int i=0;i<output.size();i++) {
            try {
                outputListAsString.add(output.get(i).toString());
            } catch (MyException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                alert.showAndWait();
            }
        }
        OutputListView.setItems(FXCollections.observableList(outputListAsString));
        OutputListView.refresh();
    }

    private PrgState getCurrentProgramState(){
        if (controller.getPrgList().size() == 0)
            return null;
        int currentId = PrgListView.getSelectionModel().getSelectedIndex();
        if (currentId == -1)
            return controller.getPrgList().get(0);
        return controller.getPrgList().get(currentId);
    }

    private void populateSymbolTable() {
        PrgState state = getCurrentProgramState();
        List<Pair<String, IValue>> symbolTableList = new ArrayList<>();
        if (state != null)
            for (Map.Entry<String, IValue> entry : state.getSymTable().getContent().entrySet())
                symbolTableList.add(new Pair<>(entry.getKey(), entry.getValue()));
            SymTableView.setItems(FXCollections.observableList(symbolTableList));
            SymTableView.refresh();
    }

    private void populateExecutionStack() {
        PrgState state = getCurrentProgramState();
        List<String> executionStackListAsString = new ArrayList<>();
        MyIStack<IStmt> exeStack;
        if(state != null)
            exeStack= state.getExeStack();
        else
            exeStack = new MyStack<IStmt>(new Stack<IStmt>());
        if (state != null)
            for(IStmt exe : exeStack.getContent()){
                executionStackListAsString.add(exe.toString());
            }
        ExecStackListView.setItems(FXCollections.observableList(executionStackListAsString));
        ExecStackListView.refresh();
    }


    
}
