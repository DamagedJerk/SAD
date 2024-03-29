package Project;



import animatefx.animation.*;
import com.jfoenix.controls.JFXButton;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;



import javax.swing.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.fxml.FXMLLoader;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.scene.control.Label;



import javafx.scene.paint.Color;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;


public class loginController implements Initializable {


    @FXML
    private JFXButton minimize;

    public Stage stage;
    @FXML
    private JFXButton btnClose;
    @FXML
    private JFXButton btnLogin;


    @FXML
    public JFXTextField txtUsername;
    @FXML
    public JFXPasswordField txtPassword;
    @FXML
    private Label lblerror;



    //Connection conn = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd ");
    DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm:ss");

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

                    String Name=resultSet.getString("firstname");
                    int role=Integer.parseInt(resultSet.getString("role"));
                    String userid=resultSet.getString("user_id");
                    //JOptionPane.showMessageDialog(null,resultSet.getString("user_id")+
                    preparedStatement=getConnection().prepareStatement("Insert into tbl_activitylog values(null,?,?,?,?)");
                    preparedStatement.setString(1,userid);
                    preparedStatement.setString(2,"Log-in");
                    preparedStatement.setString(3,LocalDateTime.now().format(formatter));
                    preparedStatement.setString(4,LocalDateTime.now().format(time));
                    preparedStatement.executeUpdate();

                    FXMLLoader loader=new FXMLLoader(getClass().getResource("Dashboard.fxml"));
                    Parent root = loader.load();
                    DashboardController dash=loader.getController();

                    Stage stage = (Stage) btnClose.getScene().getWindow();
                    stage.close();


                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setOnShown(new EventHandler<WindowEvent>() {
                        @Override
                        public void handle(WindowEvent windowEvent) {
                            dash.checkUser(Name,role,userid);
                            dash.CheckInventory();

                        }
                    });
                    stage.show();
                    FlipInX animate=new FlipInX(root);
                    animate.play();
                    animate.playOnFinished(new Swing(dash.img));


                }
            }catch (Exception e){
                lblerror.setVisible(true);
                lblerror.setText("Cannot connect to Database");
                e.printStackTrace();
            }





    }
    /*@FXML
    private void Signup() throws Exception{

        FXMLLoader loader= new FXMLLoader(getClass().getResource("Alert.fxml"));
        Alert controller=new Alert();
        loader.setController(controller);
        Parent root =loader.load();

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initOwner(btnSignup.getScene().getWindow());
        stage.setScene(new Scene(root));
        new FlipInX(root).play();
        stage.showAndWait();
        if(controller.isResponse()==true) {
            stage = (Stage) btnSignup.getScene().getWindow();
            stage.close();

            root = FXMLLoader.load(getClass().getResource("Signup.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }*/


    public loginController(){ }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtUsername.setText("admin");
        txtPassword.setText("admin");


    }
}
