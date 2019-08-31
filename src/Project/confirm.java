package Project;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class confirm implements Initializable {

    @FXML
    private JFXButton btnClose;
    @FXML
    private JFXTreeTableView<products> OrderLIstTable;
    @FXML
    void close() {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();

    }
    public confirm(){

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int width=0;
        
    }
}
