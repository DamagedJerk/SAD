package Project;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;

public class AddController {

    private String fxmlURL;

    @FXML
    private Label alertlabel;

    @FXML
    private JFXButton PromptAdd;

    @FXML
    private JFXButton PromptCancel;

    @FXML
    private JFXTextField PromptTextQuantity;
    @FXML
    private Label PrompError;

    @FXML
    private void close() throws  Exception{
        Stage stage = (Stage) PromptCancel.getScene().getWindow();
        stage.close();

    }
    @FXML
    void AddQuantity(ActionEvent event) throws  Exception  {
        //JOptionPane.showMessageDialog(null,"addquantity ni agi");
        if(PromptTextQuantity.getText().contentEquals("")){
            PrompError.setVisible(true);

            PromptTextQuantity.setText("");
        }else{
            /*FXMLLoader loader=FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
            DashboardController dash = loader.getController();
            dash.getQuantity(PromptTextQuantity.getText());

             */
            setQuan(PromptTextQuantity.getText());
            close();
            //DashboardController dash=FXMLLoader.load(PromptAdd.get);
        }

    }
    public String Quan="";
    public void setQuan(String str){
        this.Quan=str;
    }
    public String getQUan(){
        return this.Quan;
    }

    public AddController(){
    }
    public AddController(String fxmlURL){
        this.fxmlURL=fxmlURL;
    }

    public <T>  T getModal(JFXTreeTableView view) throws  Exception{
        FXMLLoader loader= new FXMLLoader(getClass().getResource(fxmlURL));
        Parent root =loader.load();
        T controller=loader.getController();


        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initOwner(view.getScene().getWindow());
        stage.setScene(new Scene(root));
        stage.showAndWait();
        //Platform .runLater(()->stage.showAndWait());
        return controller;
    }

}
