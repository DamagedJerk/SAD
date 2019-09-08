package Project;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class AddController {

    //private String fxmlURL;

    @FXML
    private Label alertlabel;

    @FXML
    public JFXButton PromptAdd;

    @FXML
    public JFXButton PromptCancel;

    @FXML
    public JFXTextField PromptTextQuantity;
    @FXML
    public Label PrompError;

    private String Quantity="";
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private String prod_id="";

    public void setProd_id(String prod_id) {
        this.prod_id = prod_id;
    }

    public String getProd_id() {
        return prod_id;
    }

    @FXML
    private void close() throws  Exception{
        Stage stage = (Stage) PromptCancel.getScene().getWindow();
        stage.close();

    }
    public void setQuan(String  quantity){
        this.Quantity=quantity;
    }
    public String getQuan(){
        return this.Quantity;
    }
    private static Connection getConnection() throws SQLException {
        Connection conn;
        dbconn.getInstance();
        conn = dbconn.connect();
        return conn;
    }
    private int checkquantity(){
        int quan=0;
        try{
            preparedStatement=getConnection().prepareStatement("Select prod_quantity from tbl_products where prod_id=?");
            preparedStatement.setString(1,getProd_id());
            resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                quan=Integer.parseInt(resultSet.getString("prod_quantity"));
            }

        }catch (Exception a){
            a.printStackTrace();
        }
        return quan;
    }
    @FXML
    void AddQuantity() throws  Exception  {
        if(PromptTextQuantity.getText().contentEquals("")){
            PrompError.setVisible(true);
            PrompError.setText("Please fill in");
            PromptTextQuantity.setText("");
        }else{
            int quan=Integer.parseInt(PromptTextQuantity.getText());
            if(quan>checkquantity()){
                PrompError.setVisible(true);
                PrompError.setText("There are only "+checkquantity()+" left");
                PromptTextQuantity.setText("");
            }else{
                setQuan(PromptTextQuantity.getText());
                close();
            }
        }

    }


    public AddController(){
    }
    //public AddController(String fxmlURL){
        //this.fxmlURL=fxmlURL;
    //}

    /*public <T> T getModal(JFXTreeTableView view) throws  Exception{
        FXMLLoader loader= new FXMLLoader(getClass().getResource(fxmlURL));
        Parent root =loader.load();
        T controller=loader.getController();
        JOptionPane.showMessageDialog(null,controller+"");

        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initOwner(view.getScene().getWindow());
        stage.setScene(new Scene(root));
        stage.showAndWait();
        //Platform .runLater(()->stage.showAndWait());
        return controller;
    }*/

}
