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

public class suppliercontroller implements Initializable {


    @FXML
    private JFXButton btnClose;
    @FXML
    private JFXButton btnAddnew;
    @FXML
    private JFXTextField Company_name;

    @FXML
    private JFXButton btnEdit;

    @FXML
    private JFXButton btnAddsupplier;

    @FXML
    private JFXTextField Contact_Number;

    @FXML
    private JFXTextField txtEmail;
    @FXML
    private Label LabelError;


    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;




    ObservableList<Suppliers> supplierlist= FXCollections.observableArrayList();
    int SelectedId;
    @FXML
    private JFXTreeTableView<Suppliers> Supplier;

    public suppliercontroller(){

    }
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
    @FXML
     void GetRowtable(MouseEvent e) throws  Exception{
        if (e.getClickCount() == 2 && !e.isConsumed()) {
            e.consume();
            String id=Supplier.getSelectionModel().getSelectedItems().get(0).getValue().supplier_id.getValue();
            String companyname=Supplier.getSelectionModel().getSelectedItems().get(0).getValue().company_name.getValue();
            String contact=Supplier.getSelectionModel().getSelectedItems().get(0).getValue().contact_number.getValue();
            String email=Supplier.getSelectionModel().getSelectedItems().get(0).getValue().email.getValue();
            Company_name.setText(companyname);
            Contact_Number.setText(contact);
            txtEmail.setText(email);
            setSelectedId(id);
            btnEdit.setDisable(false);
            btnAddsupplier.setDisable(true);
            btnAddsupplier.setVisible(false);
            btnAddnew.setVisible(true);
            btnAddnew.setDisable(false);

        }
    }
    @FXML
    private void doEdit(){
        if(Company_name.getText().contentEquals("") || Contact_Number.getText().contentEquals("") || txtEmail.getText().contentEquals("")){
            LabelError.setVisible(true);
            Company_name.setText("");
            Contact_Number.setText("");
            txtEmail.setText("");
        }else {
            Edit(Company_name.getText(), Contact_Number.getText(), txtEmail.getText(), getSelectedId());
            btnEdit.setDisable(true);
            btnAddnew.setDisable(true);
            btnAddnew.setVisible(false);
            btnAddsupplier.setVisible(true);
            btnAddsupplier.setDisable(false);
            LabelError.setVisible(false);
        }
    }
    @FXML
    private void AddNew(){
        Company_name.setText("");
        Contact_Number.setText("");
        txtEmail.setText("");
        btnAddnew.setVisible(false);
        btnAddsupplier.setVisible(true);
        btnAddsupplier.setDisable(false);
        btnEdit.setDisable(true);
    }
    private String getSelectedId(){
        return this.SelectedId+"";
    }
    private void setSelectedId(String id){
        this.SelectedId=Integer.parseInt(id);
    }
    @FXML
    private void doAdd(){
        if(Company_name.getText().contentEquals("") || Contact_Number.getText().contentEquals("") || txtEmail.getText().contentEquals("")){
            LabelError.setVisible(true);
            Company_name.setText("");
            Contact_Number.setText("");
            txtEmail.setText("");
        }else{
            Add(Company_name.getText(),Contact_Number.getText(),txtEmail.getText());

        }


    }
    private void Add(String company,String contact,String email){
        try{
            String sql="Insert into tbl_supplier values(null,?,?,?)";
            preparedStatement=getConnection().prepareStatement(sql);
            preparedStatement.setString(1,company);
            preparedStatement.setString(2,contact);
            preparedStatement.setString(3,email);
            preparedStatement.executeUpdate();
            refreshtable();
            Company_name.setText("");
            Contact_Number.setText("");
            txtEmail.setText("");


        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    private void Edit(String company,String contact,String email,String id){
        try{
            String sql="Update tbl_supplier SET company_name=?,contact_number=?,email=? WHERE supplier_id=?";
            preparedStatement=getConnection().prepareStatement(sql);
            preparedStatement.setString(1,company);
            preparedStatement.setString(2,contact);
            preparedStatement.setString(3,email);
            preparedStatement.setString(4,id);
            preparedStatement.executeUpdate();

            refreshtable();
            Company_name.setText("");
            Contact_Number.setText("");
            txtEmail.setText("");



        }catch(Exception ex){
            ex.printStackTrace();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int width=100;

        JFXTreeTableColumn<Suppliers, String> SupplierId = new JFXTreeTableColumn<>("Id");
        SupplierId .setPrefWidth(width);
        SupplierId .setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Suppliers, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Suppliers, String> productsStringCellDataFeatures) {
                return productsStringCellDataFeatures.getValue().getValue().supplier_id;
            }
        });
        JFXTreeTableColumn<Suppliers, String> SupplierName = new JFXTreeTableColumn<>("Company Name");
        SupplierName.setPrefWidth(width);
        SupplierName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Suppliers, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Suppliers, String> productsStringCellDataFeatures) {
                return productsStringCellDataFeatures.getValue().getValue().company_name;
            }
        });
        JFXTreeTableColumn<Suppliers, String> contactnumber = new JFXTreeTableColumn<>("Contact Number");
        contactnumber.setPrefWidth(width);
        contactnumber.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Suppliers, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Suppliers, String> productsStringCellDataFeatures) {
                return productsStringCellDataFeatures.getValue().getValue().contact_number;
            }
        });
        JFXTreeTableColumn<Suppliers, String> suppliermail = new JFXTreeTableColumn<>("Email");
        suppliermail.setPrefWidth(width);
        suppliermail.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Suppliers, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Suppliers, String> productsStringCellDataFeatures) {
                return productsStringCellDataFeatures.getValue().getValue().email;
            }
        });
        refreshtable();
        final TreeItem<Suppliers> root = new RecursiveTreeItem<Suppliers>(supplierlist, RecursiveTreeObject::getChildren);
        Supplier.getColumns().setAll(SupplierId,SupplierName, contactnumber, suppliermail);
        Supplier.setRoot(root);
        Supplier.setShowRoot(false);
    }
    private void refreshtable(){
        try{
            supplierlist.clear();
            String sql="Select *from tbl_supplier";
            preparedStatement = getConnection().prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                supplierlist.add(new Suppliers(resultSet.getString("supplier_id"),resultSet.getString("company_name"),resultSet.getString("contact_number"),resultSet.getString("email")));
            }

            }catch (Exception e){
            e.printStackTrace();
        }
    }


}
