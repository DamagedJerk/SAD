package Project;



import animatefx.animation.FadeIn;
import animatefx.animation.FlipInX;
import animatefx.animation.Swing;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    loginController con = new loginController();

    @Override
    public void start(Stage primaryStage) throws Exception{
        con.stage=primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Log-InForm.fxml"));
        Parent root = loader.load();
        root.setStyle("-fx-background-insets: 20; -fx-background-radius: 20");
        con.stage.setScene(new Scene(root));

        con.stage.initStyle(StageStyle.UNDECORATED);
        con.stage.show();
        new FlipInX(root).play();

    }



    public static void main(String[] args) {
        launch(args);
    }
}
