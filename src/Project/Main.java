package Project;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    loginController con = new loginController();

    @Override
    public void start(Stage primaryStage) throws Exception{
        con.stage=primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("Signup.fxml"));

        con.stage.setScene(new Scene(root));
        con.stage.initStyle(StageStyle.UNDECORATED);
        con.stage.show();


    }



    public static void main(String[] args) {
        launch(args);
    }
}
