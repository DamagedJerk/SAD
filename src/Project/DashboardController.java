package Project;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeItem;

import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.stage.Stage;
import javafx.util.Callback;


import javax.swing.*;
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
    @FXML
    private Label lblCount;
    @FXML
    private JFXTextField SalesPrice;
    public Stage stage;



    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private Image userpic=new Image("/resources/user.png");
    private ObservableList<products> CartList = FXCollections.observableArrayList();
    public Double totalprice=0.0;

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
        int width=140;
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
        ObservableList<products> productList = FXCollections.observableArrayList();

        try{
            String sql = "Select * from tbl_products";
            preparedStatement=getConnection().prepareStatement(sql);
            resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                //JOptionPane.showMessageDialog(null,resultSet.getString("prod_name"));
                productList.add(new products(resultSet.getString("prod_name"),resultSet.getString("prod_quantity"),resultSet.getString("prod_price")));
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        final TreeItem<products> root = new RecursiveTreeItem<products>(productList,RecursiveTreeObject::getChildren);
        tableProduct.getColumns().setAll(ProductName,ProductQuantity,ProductPrice);
        tableProduct.setRoot(root);
        tableProduct.setShowRoot(false);

        tableProduct.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Dragboard db=tableProduct.startDragAndDrop(TransferMode.ANY);

                ClipboardContent content= new ClipboardContent();
                content.putString(tableProduct.getAccessibleText());
                db.setContent(content);
            }
        });
        //tblCart

        JFXTreeTableColumn<products,String> CartName = new JFXTreeTableColumn<>("Name");
        CartName.setPrefWidth(width);
        CartName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<products, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<products, String> productsStringCellDataFeatures) {
                return productsStringCellDataFeatures.getValue().getValue().product_name;
            }
        });
        JFXTreeTableColumn<products,String> CartQuantity = new JFXTreeTableColumn<>("Quantity");
        CartQuantity.setPrefWidth(width);
        CartQuantity.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<products, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<products, String> productsStringCellDataFeatures) {
                return productsStringCellDataFeatures.getValue().getValue().product_quan;
            }
        });
        JFXTreeTableColumn<products,String> CartPrice= new JFXTreeTableColumn<>("Price");
        CartPrice.setPrefWidth(width);
        CartPrice.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<products, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<products, String> productsStringCellDataFeatures) {
                return productsStringCellDataFeatures.getValue().getValue().product_price;
            }
        });



        //final TreeItem<products> cart = new RecursiveTreeItem<products>(CartList,RecursiveTreeObject::getChildren);
        tableCart.getColumns().setAll(CartName,CartQuantity,CartPrice);
       // tableCart.setRoot(cart);
        //tableCart.setShowRoot(false);
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


        }else{
            img.setImage(userpic);
        }

    }

    @FXML
    void GetRow(MouseEvent e) throws Exception {

        if (e.getClickCount() == 2 && !e.isConsumed()) {
            e.consume();

            String CartName=tableProduct.getSelectionModel().getSelectedItems().get(0).getValue().product_name.getValue();
            String CartQuan=tableProduct.getSelectionModel().getSelectedItems().get(0).getValue().product_quan.getValue();
            String CartPrice=tableProduct.getSelectionModel().getSelectedItems().get(0).getValue().product_price.getValue();

            FXMLLoader loader=new FXMLLoader(getClass().getResource("Dashboard.fxml"));
            Parent root = (Parent) loader.load();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();



            SelectToCart(CartName,CartQuan,CartPrice,CartList);
            final TreeItem<products> cart = new RecursiveTreeItem<products>(CartList,RecursiveTreeObject::getChildren);
            tableCart.setRoot(cart);
            tableCart.setShowRoot(false);


        }
     }

     private void SelectToCart(String name,String Quan,String Price,ObservableList list){
        double temp=Double.parseDouble(Price);

        totalprice+=temp;

        SalesPrice.setText(totalprice.toString());
         list.add(new products(name,Quan,Price));
     }





}
