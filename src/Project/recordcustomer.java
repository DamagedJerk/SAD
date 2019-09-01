package Project;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class recordcustomer {

    PreparedStatement preparedStatement=null;
    ResultSet resultSet=null;
    private boolean response=false;
    private int customer_id=0;
    @FXML
    private JFXButton btnClose;

    @FXML
    private JFXTextField First_name;

    @FXML
    private JFXTextField Mid_name;

    @FXML
    private JFXTextField Last_name;

    @FXML
    private JFXButton btnConfirm;
    @FXML
    void close() {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();

    }
    private static Connection getConnection() throws SQLException {
        Connection conn;
        dbconn.getInstance();
        conn = dbconn.connect();
        return conn;
    }
    @FXML
    private void InsertCustomer(){
        if(isCustomerExist()==false){
            try{
                preparedStatement=getConnection().prepareStatement("Insert into tbl_customer values(null,?,?,?)");
                preparedStatement.setString(1,First_name.getText());
                preparedStatement.setString(2,Mid_name.getText());
                preparedStatement.setString(3,Last_name.getText());
                preparedStatement.executeUpdate();
                setResponse(true);
                getId();
                close();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }

    }
    private boolean isCustomerExist(){
        try{
            preparedStatement=getConnection().prepareStatement("Select *from tbl_customer where cus_firstname=? and cus_lastname=?");
            preparedStatement.setString(1,First_name.getText());
            preparedStatement.setString(2,Last_name.getText());
            resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                int result=Integer.parseInt(resultSet.getString("cus_id"));
                setCustomer_id(result);
                setResponse(true);
                close();
                return true;

            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }
    public void getId(){
        try{
            preparedStatement=getConnection().prepareStatement("Select max(cus_id) from tbl_customer");
            resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                int result=Integer.parseInt(resultSet.getString("max(cus_id)"));
                setCustomer_id(result);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    public recordcustomer(){

    }
    //setters

    public void setResponse(boolean response) {
        this.response = response;
    }

    public boolean isResponse() {
        return response;
    }

    private void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }
    //getters
}
