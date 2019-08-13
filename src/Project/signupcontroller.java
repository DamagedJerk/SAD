package Project;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class signupcontroller {

    @FXML
    private JFXButton minimize;

    public Stage stage;
    @FXML
    private JFXButton btnClose;
    @FXML
    private JFXTextField txtFirstName;

    @FXML
    private JFXTextField txtLastName;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtUsername;

    @FXML
    private JFXDatePicker txtBirthday;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private JFXPasswordField txtConfirm;

    @FXML
    private JFXButton btnConfirm;
    @FXML
    private JFXTextField txtCellNumber;


    @FXML
    private void close(){
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
        System.exit(1);
    }
    @FXML
    private void minimize(){
        Stage stage = (Stage) minimize.getScene().getWindow();
        stage.setIconified(true);
    }
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private static Connection getConnection() throws SQLException {
        Connection conn;
        dbconn.getInstance();
        conn=dbconn.connect();
        return conn;
    }

    public signupcontroller(){

    }
    public void signup(){


        String txtfirstname=txtFirstName.getText();
        String txtUser=txtUsername.getText();
        String txtlastname=txtLastName.getText();
        String txtemail=txtEmail.getText();
        String txtbirth=txtBirthday.getValue().toString();
        String txtCellnum=txtCellNumber.getText();
        String txtPass=txtPassword.getText();
        String txtconfirmpass=txtConfirm.getText();
        int role=0;
        String sql="Insert into tbl_employee (user_name,firstname,lastname,password,email,cell_number,role) values(?,?,?,?,?,?,?)";

        try{
            preparedStatement=getConnection().prepareStatement(sql);
            preparedStatement.setString(1,txtUser);
            preparedStatement.setString(2,txtfirstname);
            preparedStatement.setString(3,txtlastname);
            preparedStatement.setString(4,txtPass);
            preparedStatement.setString(5,txtemail);
            preparedStatement.setString(6,txtCellnum);
            preparedStatement.setInt(7,role);
            resultSet=preparedStatement.executeQuery();
            JOptionPane.showMessageDialog(null,"Successfully Added");

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
