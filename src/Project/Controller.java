package Project;


import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.stage.Stage;


public class Controller {


    public Stage stage;
    @FXML
    private JFXButton minmize;
    @FXML
    private JFXButton btnSignUp;
    @FXML
    private JFXButton btnLogIn;
    @FXML
    private JFXButton btnClose;
    @FXML
    private void close(){
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
        //System.exit(1);
    }
    @FXML
    private void minimize(){
        Stage stage = (Stage) minmize.getScene().getWindow();
        stage.setIconified(true);
    }
    @FXML
    private void login(){
        Stage stage = (Stage) btnLogIn.getScene().getWindow();
        stage.setIconified(true);
    }
    public Controller(){

    }
}
