package Project;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import javax.swing.*;


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
    @FXML
    void AddQuantity() throws  Exception  {
        //JOptionPane.showMessageDialog(null,"addquantity ni agi");
        if(PromptTextQuantity.getText().contentEquals("")){
            PrompError.setVisible(true);
            PromptTextQuantity.setText("");
        }else{
            setQuan(PromptTextQuantity.getText());
            close();


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
