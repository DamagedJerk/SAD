package Project;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CategoryPanelController implements Initializable {


    @FXML
    private JFXTextField txtCategory;
    @FXML
    private Label LabelError;
    @FXML
    private JFXButton CategoryNew;
    @FXML
    private JFXButton CategoryEdit;

    @FXML
    private JFXButton CategoryAdd;

    @FXML
    private JFXTreeTableView<Category> CategoryTable;
    @FXML
    private JFXButton btnClose;

    ObservableList<Category> categories= FXCollections.observableArrayList();
    private static Connection getConnection() throws SQLException {
        Connection conn;
        dbconn.getInstance();
        conn = dbconn.connect();
        return conn;
    }
    @FXML
    void close() {

        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();

    }

    public CategoryPanelController(){}
    PreparedStatement preparedStatement=null;
    ResultSet resultSet=null;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int width=110;
        JFXTreeTableColumn<Category, String> CategoryId = new JFXTreeTableColumn<>("Id");
        CategoryId.setPrefWidth(width);
        CategoryId .setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Category, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Category, String> productsStringCellDataFeatures) {
                return productsStringCellDataFeatures.getValue().getValue().category_id;
            }
        });
        JFXTreeTableColumn<Category, String> CategoryDesc = new JFXTreeTableColumn<>("Description");
        CategoryDesc.setPrefWidth(width);
        CategoryDesc.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Category, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Category, String> productsStringCellDataFeatures) {
                return productsStringCellDataFeatures.getValue().getValue().category_description;
            }
        });
        refreshtable();
        final TreeItem<Category> root = new RecursiveTreeItem<Category>(categories, RecursiveTreeObject::getChildren);
        CategoryTable.getColumns().setAll(CategoryId,CategoryDesc);
        CategoryTable.setRoot(root);
        CategoryTable.setShowRoot(false);


    }
    private void refreshtable(){
        try{
            categories.clear();
            String sql="Select * from tbl_category";
            preparedStatement = getConnection().prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                categories.add(new Category(resultSet.getString("category_id"),resultSet.getString("cat_description")));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    void GetRow(MouseEvent e){
        if (e.getClickCount() == 2 && !e.isConsumed()) {
            e.consume();

            String descriptionValue=CategoryTable.getSelectionModel().getSelectedItems().get(0).getValue().category_description.getValue();

            txtCategory.setText(descriptionValue);
            CategoryEdit.setDisable(false);

            CategoryNew.setVisible(true);
            CategoryAdd.setVisible(false);
            CategoryAdd.setDisable(true);

        }
    }
    @FXML
    private  void doAdd(){
        if(txtCategory.getText().contentEquals("")){
            LabelError.setVisible(true);
        }else{
            Add(txtCategory.getText());
            refreshtable();
            LabelError.setVisible(false);

        }
    }
    private void Add(String Desc){
        try{
            String sql="Insert into tbl_category values(null,?)";
            preparedStatement=getConnection().prepareStatement(sql);
            preparedStatement.setString(1,Desc);
            preparedStatement.executeUpdate();
            txtCategory.setText("");
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    @FXML
    private void doEdit(){
        if(txtCategory.getText().contentEquals("")){
            LabelError.setVisible(true);
        }else{
            Edit(txtCategory.getText());
            LabelError.setVisible(false);
            CategoryEdit.setDisable(true);
            CategoryNew.setDisable(true);
            CategoryNew.setVisible(false);
            CategoryAdd.setVisible(true);
            CategoryAdd.setDisable(false);
            refreshtable();
        }
    }
    @FXML
    private void AddNew(){
        txtCategory.setText("");

        CategoryNew.setVisible(false);
        CategoryAdd.setVisible(true);
        CategoryAdd.setDisable(false);
        CategoryEdit.setDisable(true);
    }

    private void Edit(String Desc){
        try{
            String sql="Update tbl_category SET cat_description=? WHERE cat_description=?";
            preparedStatement=getConnection().prepareStatement(sql);
            preparedStatement.setString(1,Desc);
            preparedStatement.setString(2,Desc);
            preparedStatement.executeUpdate();
            txtCategory.setText("");
        }catch(Exception ex){
            ex.printStackTrace();
        }

    }

}
