package Project;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    Controller con = new Controller();

    @Override
    public void start(Stage primaryStage) throws Exception{
        con.stage=primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("Log-inForm.fxml"));

        con.stage.setScene(new Scene(root));
        con.stage.initStyle(StageStyle.UNDECORATED);
        con.stage.show();


    }



    public static void main(String[] args) {
        launch(args);
    }
}
