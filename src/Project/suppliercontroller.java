package Project;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.stage.Stage;
import javafx.util.Callback;

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
    private JFXTextField Company_name;

    @FXML
    private JFXButton btnCancel;

    @FXML
    private JFXButton btnAddsupplier;

    @FXML
    private JFXTextField Contact_Number;

    @FXML
    private JFXTextField txtEmail;

    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;




    ObservableList<Suppliers> supplierlist= FXCollections.observableArrayList();

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
