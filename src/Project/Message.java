package Project;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Message implements Initializable {


    @FXML
    private JFXButton btnClose;

    @FXML
    private JFXButton btnCancel;

    @FXML
    private JFXButton btnExit;

    @FXML
    private Label LabelError;
    private boolean response=false;
    private String Message="";

    public void setMessage(String message) {
        Message = message;
    }

    public String getMessage() {
        return Message;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    public boolean isResponse() {
        return response;
    }

    @FXML
    private void close(){
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }
    public Message(){}
    @FXML
    private void confirm(){
        setResponse(true);
        close();
    }
    @FXML
    private void cancel(){
        close();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LabelError.setText(getMessage());
    }
}
