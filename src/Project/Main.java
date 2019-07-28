package Project;

import com.jfoenix.controls.JFXButton;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    private Stage stage;
    private JFXButton btnClose= new JFXButton();
    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("Log-InForm.fxml"));

        stage.setScene(new Scene(root));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();


    }
    @FXML
    private void close(){
        stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
        System.exit(1);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
