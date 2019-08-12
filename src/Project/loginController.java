package Project;



import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;



import javax.swing.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.fxml.FXMLLoader;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.scene.control.Label;



import javafx.scene.paint.Color;


public class loginController implements Initializable {


    @FXML
    private JFXButton minimize;

    public Stage stage;
    @FXML
    private JFXButton btnClose;
    @FXML
    private JFXButton btnLogin;
    @FXML
    private JFXButton btnSignup;

    @FXML
    public TextField txtUsername;
    @FXML
    public PasswordField txtPassword;
    @FXML
    private Label lblerror;



    //Connection conn = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    private static Connection getConnection() throws SQLException{
        Connection conn;
        dbconn.getInstance();
        conn=dbconn.connect();
        return conn;
    }


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
    @FXML
    private void login() throws  Exception{
            String username = txtUsername.getText();
            String pass = txtPassword.getText();

            String sql="SELECT * from tbl_employee where  user_name=  ? and password= ?";
            try{

                preparedStatement=getConnection().prepareStatement(sql);
                preparedStatement.setString(1,username);
                preparedStatement.setString(2,pass);
                resultSet = preparedStatement.executeQuery();
                if(txtUsername.getText().contentEquals("") || txtPassword.getText().contentEquals("")){
                    lblerror.setText("Please fill in ");
                    lblerror.setVisible(true);
                } else if(!resultSet.next()){
                    lblerror.setText("Username or Password Incorrect");
                    lblerror.setVisible(true);
                    txtUsername.setText("");
                    txtPassword.setText("");
                }else{


                    //JOptionPane.showMessageDialog(null, String.format("Welcome %s", checkUser(resultSet.getString("role"))));
                    lblerror.setText("logging in . . . .");
                    lblerror.setVisible(true);
                    lblerror.setTextFill(Color.web("#4386F8"));
                    Stage stage = (Stage) btnClose.getScene().getWindow();
                    stage.close();
                    String Name=resultSet.getString("fullname");
                    int role=Integer.parseInt(resultSet.getString("role"));



                    FXMLLoader loader=new FXMLLoader(getClass().getResource("Dashboard.fxml"));
                    Parent root = (Parent) loader.load();
                    DashboardController dash=loader.getController();
                    dash.checkUser(Name,role);

                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();




                }
            }catch (Exception e){
                e.printStackTrace();
            }





    }
    @FXML
    private void Signup() throws Exception{
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("Signup.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public loginController(){
       /* try{
            if(resultSet.getString("role").contentEquals("1")){
                setName("Dummy Admin");
            }else
                setName("Dummy User1");

        }catch (SQLException e){
            e.printStackTrace();
        }*/


    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
