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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    private String adminID="";

    public void setAdminID(String adminID) {
        this.adminID = adminID;
    }

    public String getAdminID() {
        return adminID;
    }

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
                    setAdminID(resultSet.getString("user_id"));
                    break;
                }

            }
            if(isResponse()==true) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd ");
                DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm:ss");
                preparedStatement = getConnection().prepareStatement("Insert into tbl_activitylog values(null,?,?,?,?)");

                preparedStatement.setString(1, getAdminID());
                preparedStatement.setString(2, "Admin Confirmation");
                preparedStatement.setString(3, LocalDateTime.now().format(formatter));
                preparedStatement.setString(4, LocalDateTime.now().format(time));
                preparedStatement.executeUpdate();
            }


            if(isResponse()==true){
                close();
            }else{
                LabelError.setVisible(true);
                txtPassword.setText("");
            }



        }catch(Exception e){
            e.printStackTrace();
        }

    }

}
