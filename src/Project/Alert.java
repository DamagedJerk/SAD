package Project;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Alert {

    private boolean response = false;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    //setters


    public void setResponse(boolean response) {
        this.response = response;
    }

    public boolean isResponse() {
        return response;
    }

    //getters
    @FXML
    private Label LabelError;
    @FXML
    private JFXPasswordField txtPassword;
    @FXML
    public JFXButton btnConfirm;
    @FXML
    private JFXButton btncancel;
    public Alert(){}

    private static Connection getConnection() throws SQLException {
        Connection conn;
        dbconn.getInstance();
        conn = dbconn.connect();
        return conn;
    }
    @FXML
    void close() {
        Stage stage = (Stage) btncancel.getScene().getWindow();
        stage.close();

    }

     @FXML
     void doConfirm(){

        try{
            preparedStatement=getConnection().prepareStatement("Select *from tbl_employee where role=1");
            resultSet=preparedStatement.executeQuery();
            int i=1;
            while(resultSet.next()){
                if (resultSet.getString("password").contentEquals(txtPassword.getText())) {
                    //JOptionPane.showMessageDialog(null,"response should be true now");
                    setResponse(true);
                    break;
                }else{
                    LabelError.setVisible(true);
                    txtPassword.setText("");
                }

            }
            if(isResponse()==true){
                close();
            }



        }catch(Exception e){
            e.printStackTrace();
        }

    }

}
