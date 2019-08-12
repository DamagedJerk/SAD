package Project;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeItem;

import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;


import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {


    @FXML
    private Label lblName;

    @FXML
    private JFXButton minimize;

    @FXML
    private JFXButton btnClose;

    @FXML
    private JFXTreeTableView<products> tableProduct;

    @FXML
    private JFXTreeTableView<products> tableCart;
    @FXML
    private JFXButton btnVoid;

    @FXML
    private Tab tabInventory;

    @FXML
    private Tab tabStock;

    @FXML
    private Tab tabReport;

    @FXML
    private Tab tabLog;

    @FXML
    private ImageView img;

    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    private static Connection getConnection() throws SQLException {
        Connection conn;
        dbconn.getInstance();
        conn=dbconn.connect();
        return conn;
    }

    @FXML
    void close(ActionEvent event) {

        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
        System.exit(1);
    }

    @FXML
    void minimize(ActionEvent event) {
        Stage stage = (Stage) minimize.getScene().getWindow();
        stage.setIconified(true);

    }




    public DashboardController(){

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //JOptionPane.showMessageDialog(null, String.format(" %s", img.getImage().getUrl()));
        Image imahe=new Image("@../resources/user.png");
        img.setImage(imahe);




        int width=250;
        JFXTreeTableColumn<products,String> ProductName = new JFXTreeTableColumn<>("Name");
        ProductName.setPrefWidth(width);
        ProductName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<products, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<products, String> productsStringCellDataFeatures) {
                return productsStringCellDataFeatures.getValue().getValue().product_name;
            }
        });
        JFXTreeTableColumn<products,String> ProductQuantity = new JFXTreeTableColumn<>("Quantity");
        ProductQuantity.setPrefWidth(width);
        ProductQuantity.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<products, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<products, String> productsStringCellDataFeatures) {
                return productsStringCellDataFeatures.getValue().getValue().product_quan;
            }
        });
        JFXTreeTableColumn<products,String> ProductPrice= new JFXTreeTableColumn<>("Price");
        ProductPrice.setPrefWidth(width);
        ProductPrice.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<products, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<products, String> productsStringCellDataFeatures) {
                return productsStringCellDataFeatures.getValue().getValue().product_price;
            }
        });
        ObservableList<products> products = FXCollections.observableArrayList();
        products.add(new products("Spider-man Case","12","100.0"));
        products.add(new products("Iron-man Case","12","100.0"));
        products.add(new products("Phone Accessory","12","50.0"));
        products.add(new products("Captain America Case","12","100.0"));
        products.add(new products("Thor Case","12","100.0"));

        final TreeItem<products> root = new RecursiveTreeItem<products>(products,RecursiveTreeObject::getChildren);
        tableProduct.getColumns().setAll(ProductName,ProductQuantity,ProductPrice);
        tableProduct.setRoot(root);
        tableProduct.setShowRoot(false);

    }

    class products extends RecursiveTreeObject<products>{
        StringProperty product_name;
        StringProperty  product_quan;
        StringProperty  product_price;

        public products(String prodname,String quan,String price){
            this.product_name=new SimpleStringProperty(prodname);
            this.product_quan= new SimpleStringProperty(quan);
            this.product_price=new SimpleStringProperty(price);

        }

    }

    public void checkUser(String Name,int Role){
        lblName.setText(String.format("Welcome %s",Name));
        if(Role==1){
            tabInventory.setDisable(false);
            tabStock.setDisable(false);
            tabReport.setDisable(false);
            tabLog.setDisable(false);

        }

    }
}
