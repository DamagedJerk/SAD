package Project;



import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;



public class loginController implements Initializable {


    public Stage stage;
    @FXML
    private JFXButton minmize,btnClose;
    @FXML
    private JFXButton btnLogin;
    @FXML
    private JFXButton btnSignup;

    @FXML
    public TextField txtUsername;
    @FXML
    public PasswordField txtPassword;


    Connection conn = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    private static Connection getConnection() throws SQLException{
        Connection conn;
        dbconn.getInstance();
        conn=dbconn.connect();
        return conn;
    }


    @FXML
    private void close(){
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();     //System.exit(1);
    }
    @FXML
    private void minimize(){
        Stage stage = (Stage) minmize.getScene().getWindow();
        stage.setIconified(true);
    }
    @FXML
    private void login(){
            String username = txtUsername.getText();
            String pass = txtPassword.getText();

            String sql="SELECT * from tbl_employee where  user_name= ? and password= ?";
            //JOptionPane.showMessageDialog(null,"SQL "+sql);
            try{

                preparedStatement=getConnection().prepareStatement(sql);
                preparedStatement.setString(1,username);
                preparedStatement.setString(2,pass);
                resultSet = preparedStatement.executeQuery();


                if(!resultSet.next()){

                    JOptionPane.showMessageDialog(null,"mali","Error",JOptionPane.ERROR_MESSAGE);
                    txtUsername.setText("");
                    txtPassword.setText("");
                }else{
                    String role="";
                    if(resultSet.getString("role").contentEquals("1")){
                        role="admin";
                    }else
                        role="staff";
                    JOptionPane.showMessageDialog(null,"welcome "+role);

                }
            }catch (Exception e){
                e.printStackTrace();
            }





    }
    @FXML
    private void Signup(){
        JOptionPane.showMessageDialog(null,"SignUp now BITCH");
    }


    public loginController(){

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
