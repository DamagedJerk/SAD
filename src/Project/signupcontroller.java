package Project;

import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class signupcontroller implements Initializable {

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
    private JFXComboBox<String> cboRole;


    @FXML
    private void close() throws  Exception{
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
        FXMLLoader loader=new FXMLLoader(getClass().getResource("Log-InForm.fxml"));
        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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

        if(txtUsername.getText().contentEquals("") || txtFirstName.getText().contentEquals("") || txtLastName.getText().contentEquals("") || txtEmail.getText().contentEquals("") || txtBirthday.getValue().toString().contentEquals("") || txtCellNumber.getText().contentEquals("") || txtPassword.getText().contentEquals("") || txtConfirm.getText().contentEquals("")){
            JOptionPane.showMessageDialog(null,"Please input all fields","Warning",JOptionPane.WARNING_MESSAGE);
            clearfields();
        }else if(!txtPassword.getText().contentEquals(txtConfirm.getText())) {
            JOptionPane.showMessageDialog(null,"Password Do not Match","Error",JOptionPane.ERROR_MESSAGE);
            txtPassword.setText("");
            txtConfirm.setText("");
        }else{
                String txtfirstname = txtFirstName.getText();
                String txtUser = txtUsername.getText();
                String txtlastname = txtLastName.getText();
                String txtemail = txtEmail.getText();
                String txtbirth = txtBirthday.getValue().toString();
                String txtCellnum = txtCellNumber.getText();
                String txtPass = txtPassword.getText();
                String txtconfirmpass = txtConfirm.getText();
                int role =cboRole.getSelectionModel().selectedIndexProperty().getValue();
                String sql = "Insert into tbl_employee (user_name,firstname,lastname,password,email,cell_number,role) values(?,?,?,?,?,?,?)";

                try {
                    preparedStatement = getConnection().prepareStatement(sql);
                    preparedStatement.setString(1, txtUser);
                    preparedStatement.setString(2, txtfirstname);
                    preparedStatement.setString(3, txtlastname);
                    preparedStatement.setString(4, txtPass);
                    preparedStatement.setString(5, txtemail);
                    preparedStatement.setString(6, txtCellnum);
                    preparedStatement.setInt(7, role);
                    preparedStatement.executeUpdate();

                    clearfields();

                    JOptionPane.showMessageDialog(null, "Successfully Added");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
             private void clearfields(){
                    txtFirstName.setText("");
                    txtLastName.setText("");
                    txtEmail.setText("");
                    txtUsername.setText("");
                    txtBirthday.setValue(null);
                    txtCellNumber.setText("");
                    txtPassword.setText("");
                    txtConfirm.setText("");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cboRole.getItems().add("User");
        cboRole.getItems().add("Admin");

    }
}
