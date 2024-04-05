package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import View.PrgExecutor.ExecutorController;
import View.PrgList.ListController;

public class InterpreterGUI extends Application {
    
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader listLoader = new FXMLLoader();
        listLoader.setLocation(getClass().getResource("PrgList/PrgList.fxml"));
        Parent root = listLoader.load();
        ListController listController = listLoader.getController();
        primaryStage.setTitle("Prg Selector");
        primaryStage.setScene(new Scene(root, 500, 550));
        primaryStage.show();

        FXMLLoader programLoader = new FXMLLoader();
        programLoader.setLocation(getClass().getResource("PrgExecutor/PrgExecutor.fxml"));
        Parent programRoot = programLoader.load();
        ExecutorController executorController = programLoader.getController();
        listController.setExecutorController(executorController);
        Stage secondaryStage = new Stage();
        secondaryStage.setTitle("Interpreter");
        secondaryStage.setScene(new Scene(programRoot, 1400, 1000));
        secondaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
