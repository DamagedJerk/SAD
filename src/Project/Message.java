package Project;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
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
    @FXML
    private Label labelClose;

    @FXML
    private JFXButton ChangeCashier;

    private boolean response=false;
    private String Message="";
    private dbconn database;
    private Connection connect;
    private Map<String, Object> map;

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
    private static Connection getConnection() throws SQLException {
        Connection conn;
        dbconn.getInstance();
        conn = dbconn.connect();
        return conn;
    }

    public void printReport(String report) throws SQLException {
        database = new dbconn();
        connect = getConnection();
        map = new HashMap<String, Object>();

        Report.createReport(connect, map, database.getReport(report, "report_jasper"));
        Report.showReport();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LabelError.setText(getMessage());
        ChangeCashier.setOnAction(e->{
            try{
                printReport("InventoryReports");
            }catch (Exception ee){
                ee.printStackTrace();
            }
        });
    }
}
