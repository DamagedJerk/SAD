package Project;



import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import javax.swing.*;
import java.sql.Connection;


public class loginController {


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

        try{
            dbconn connection = new dbconn();
            Connection sqlconn=connection.conn();

        }catch(Exception e){

        }




    }
    @FXML
    private void Signup(){
        JOptionPane.showMessageDialog(null,"SignUp now BITCH");
    }


    public loginController(){

    }


}
