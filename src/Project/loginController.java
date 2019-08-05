package Project;



import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import javax.swing.*;
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


    Connection con= null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;




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
            String username = txtUsername.getText();
            String pass = txtPassword.getText();

            String sql="SELECT * from tbl_employee where user=? and pass=?";

            try{
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1,username);
                preparedStatement.setString(2,pass);
                resultSet = preparedStatement.executeQuery();

                if(!resultSet.next()){
                    JOptionPane.showMessageDialog(null,"mali","Error",JOptionPane.ERROR_MESSAGE);
                }
            }catch (Exception e){
                Logger.getLogger(loginController.class.getName()).log(Level.SEVERE,null,e);
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
