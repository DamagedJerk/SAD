package Project;



import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;



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
        if(txtUsername.getText().contentEquals("admin") && txtPassword.getText().contentEquals("admin")){
            JOptionPane.showMessageDialog(null,"Success Welcome "+txtUsername.getText());
        }else{
            JOptionPane.showMessageDialog(null,"Wrong Credentials","Error",JOptionPane.ERROR_MESSAGE);
                txtUsername.setText("");
                txtPassword.setText("");
        }



    }
    @FXML
    private void Signup(){
        JOptionPane.showMessageDialog(null,"SignUp now BITCH");
    }

    
    public loginController(){

    }


}
