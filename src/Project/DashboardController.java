package Project;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.control.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;


import javax.sound.midi.ControllerEventListener;
import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private Label dateTime;


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
    private AnchorPane rootPane;
    @FXML
    private Tab tabLog;

    @FXML
    private ImageView img;
    @FXML
    private Label lblCount;
    @FXML
    private JFXTextField SalesPrice;
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


    private String Quantity = "";


    //InventoryTab

    @FXML
    private JFXTextField InventoryID;

    @FXML
    private JFXComboBox<String> InventorySupp;

    @FXML
    private JFXComboBox<String> InventoryCateg;

    @FXML
    private JFXTextField InventoryQuantity;

    @FXML
    private JFXTextField InventoryPrice;

    @FXML
    private JFXTextField InventoryName;

    @FXML
    private JFXButton add_inventory;

    @FXML
    private JFXButton update_inventory;
    @FXML
    private JFXTreeTableView<Inventory> tbl_inventory;

    ObservableList<Inventory> InventoryList = FXCollections.observableArrayList();


    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private Image userpic = new Image("/resources/user.png");
    private ObservableList<products> CartList = FXCollections.observableArrayList();
    public Double totalprice = 0.0;


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
        System.exit(1);
    }

    @FXML
    void AddQuantity(ActionEvent event) throws Exception {
        JOptionPane.showMessageDialog(null, "addquantity ni agi");
        if (PromptTextQuantity.getText().contentEquals("")) {
            PrompError.setVisible(true);

            PromptTextQuantity.setText("");
        } else {
            /*FXMLLoader loader=FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
            DashboardController dash = loader.getController();
            dash.getQuantity(PromptTextQuantity.getText());

             */

            //DashboardController dash=FXMLLoader.load(PromptAdd.get);
        }

    }


    @FXML
    void minimize(ActionEvent event) {
        Stage stage = (Stage) minimize.getScene().getWindow();
        stage.setIconified(true);

    }


    public DashboardController() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initClock();

        //initialize
        setComboBOx(InventorySupp,"Select * from tbl_supplier","company_name");
        setComboBOx(InventoryCateg,"Select * from tbl_category","cat_description");

        int width = 140;
        JFXTreeTableColumn<products, String> ProductName = new JFXTreeTableColumn<>("Name");
        ProductName.setPrefWidth(width);

        ProductName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<products, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<products, String> productsStringCellDataFeatures) {
                return productsStringCellDataFeatures.getValue().getValue().product_name;
            }
        });
        JFXTreeTableColumn<products, String> ProductQuantity = new JFXTreeTableColumn<>("Quantity");
        ProductQuantity.setPrefWidth(width);
        ProductQuantity.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<products, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<products, String> productsStringCellDataFeatures) {
                return productsStringCellDataFeatures.getValue().getValue().product_quan;
            }
        });
        JFXTreeTableColumn<products, String> ProductPrice = new JFXTreeTableColumn<>("Price");
        ProductPrice.setPrefWidth(width);
        ProductPrice.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<products, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<products, String> productsStringCellDataFeatures) {
                return productsStringCellDataFeatures.getValue().getValue().product_price;
            }
        });
        ObservableList<products> productList = FXCollections.observableArrayList();

        try {
            String sql = "Select * from tbl_products";
            preparedStatement = getConnection().prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                //JOptionPane.showMessageDialog(null,resultSet.getString("prod_name"));
                productList.add(new products(resultSet.getString("prod_name"), resultSet.getString("prod_quantity"), resultSet.getString("prod_price")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        final TreeItem<products> root = new RecursiveTreeItem<products>(productList, RecursiveTreeObject::getChildren);
        tableProduct.getColumns().setAll(ProductName, ProductQuantity, ProductPrice);
        tableProduct.setRoot(root);
        tableProduct.setShowRoot(false);

        //tblCart

        JFXTreeTableColumn<products, String> CartName = new JFXTreeTableColumn<>("Name");
        CartName.setPrefWidth(width);
        CartName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<products, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<products, String> productsStringCellDataFeatures) {
                return productsStringCellDataFeatures.getValue().getValue().product_name;
            }
        });
        JFXTreeTableColumn<products, String> CartQuantity = new JFXTreeTableColumn<>("Quantity");
        CartQuantity.setPrefWidth(width);
        CartQuantity.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<products, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<products, String> productsStringCellDataFeatures) {
                return productsStringCellDataFeatures.getValue().getValue().product_quan;
            }
        });
        JFXTreeTableColumn<products, String> CartPrice = new JFXTreeTableColumn<>("Price");
        CartPrice.setPrefWidth(width);
        CartPrice.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<products, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<products, String> productsStringCellDataFeatures) {
                return productsStringCellDataFeatures.getValue().getValue().product_price;
            }
        });


        //final TreeItem<products> cart = new RecursiveTreeItem<products>(CartList,RecursiveTreeObject::getChildren);
        tableCart.getColumns().setAll(CartName, CartQuantity, CartPrice);
        // tableCart.setRoot(cart);
        //tableCart.setShowRoot(false);

        //tabInventory
        int widthInventory=130;
        tabInventory.setOnSelectionChanged(event -> {


            if (tabInventory.isSelected() && tbl_inventory.getCurrentItemsCount()==0 ) {
                JFXTreeTableColumn<Inventory, String> InventoryId = new JFXTreeTableColumn<>("ID");
                InventoryId.setPrefWidth(widthInventory);

                InventoryId.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Inventory, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Inventory, String> productsStringCellDataFeatures) {
                        return productsStringCellDataFeatures.getValue().getValue().product_id;
                    }
                });
                JFXTreeTableColumn<Inventory, String> InventoryName = new JFXTreeTableColumn<>("Name");
                InventoryName.setPrefWidth(widthInventory);
                InventoryName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Inventory, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Inventory, String> productsStringCellDataFeatures) {
                        return productsStringCellDataFeatures.getValue().getValue().product_name;
                    }
                });
                JFXTreeTableColumn<Inventory, String> InventoryQuantity = new JFXTreeTableColumn<>("Quantity");
                InventoryQuantity.setPrefWidth(widthInventory);
                InventoryQuantity.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Inventory, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Inventory, String> productsStringCellDataFeatures) {
                        return productsStringCellDataFeatures.getValue().getValue().product_quan;
                    }
                });
                JFXTreeTableColumn<Inventory, String> InventoryPrice = new JFXTreeTableColumn<>("Price");
                InventoryPrice.setPrefWidth(widthInventory);
                InventoryPrice.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Inventory, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Inventory, String> productsStringCellDataFeatures) {
                        return productsStringCellDataFeatures.getValue().getValue().product_price;
                    }
                });
                JFXTreeTableColumn<Inventory, String> InventoryCategory = new JFXTreeTableColumn<>("Category");
                InventoryCategory.setPrefWidth(widthInventory);
                InventoryCategory.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Inventory, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Inventory, String> productsStringCellDataFeatures) {
                        return productsStringCellDataFeatures.getValue().getValue().product_category;
                    }
                });
                JFXTreeTableColumn<Inventory, String> InventorySupplier = new JFXTreeTableColumn<>("Supplier");
                InventorySupplier.setPrefWidth(widthInventory);
                InventorySupplier.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Inventory, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Inventory, String> productsStringCellDataFeatures) {
                        return productsStringCellDataFeatures.getValue().getValue().product_supplier;
                    }
                });
                try {
                    String sql = "select p.prod_id,p.prod_name,p.prod_quantity,p.prod_price,c.cat_description,s.company_name FROM tbl_products p JOIN tbl_supplier s ON p.supplier_id = s.supplier_id JOIN tbl_category c ON p.category_id=c.category_id";
                    preparedStatement = getConnection().prepareStatement(sql);
                    resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        //JOptionPane.showMessageDialog(null,resultSet.getString("prod_name"));
                        InventoryList.add(new Inventory(resultSet.getString("prod_id"), resultSet.getString("prod_name"), resultSet.getString("prod_quantity"),resultSet.getString("prod_price"), resultSet.getString("cat_description"), resultSet.getString("company_name")));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                final TreeItem<Inventory> inventoryList = new RecursiveTreeItem<Inventory>(InventoryList, RecursiveTreeObject::getChildren);
                tbl_inventory.getColumns().setAll(InventoryId, InventoryName, InventoryQuantity, InventoryPrice, InventoryCategory, InventorySupplier);
                tbl_inventory.setRoot(inventoryList);
                tbl_inventory.setShowRoot(false);
            }


        });


    }
    private void setComboBOx(JFXComboBox comboBOx,String query,String columnname){
        try {

            preparedStatement = getConnection().prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                //JOptionPane.showMessageDialog(null,resultSet.getString("prod_name"));
                comboBOx.getItems().add(resultSet.getString(columnname));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    class products extends RecursiveTreeObject<products> {
        StringProperty product_name;
        StringProperty product_quan;
        StringProperty product_price;

        public products(String prodname, String quan, String price) {
            this.product_name = new SimpleStringProperty(prodname);
            this.product_quan = new SimpleStringProperty(quan);
            this.product_price = new SimpleStringProperty(price);

        }

    }

    class Inventory extends RecursiveTreeObject<Inventory> {

        StringProperty product_name;
        StringProperty product_quan;
        StringProperty product_price;
        StringProperty product_supplier;
        StringProperty product_category;
        StringProperty product_id;

        public Inventory(String id, String prodname, String quan, String price, String cat , String supp) {
            this.product_id = new SimpleStringProperty(id);
            this.product_name = new SimpleStringProperty(prodname);
            this.product_quan = new SimpleStringProperty(quan);
            this.product_price = new SimpleStringProperty(price);
            this.product_category = new SimpleStringProperty(cat);
            this.product_supplier = new SimpleStringProperty(supp);

        }
    }
        public void checkUser(String Name, int Role) {
            lblName.setText(String.format("Welcome %s", Name));
            if (Role == 1) {
                tabInventory.setDisable(false);
                tabStock.setDisable(false);
                tabReport.setDisable(false);
                tabLog.setDisable(false);


            } else {
                img.setImage(userpic);
            }

        }

        public String getQuantity(String str) {
            return Quantity = str;
        }

        @FXML
        void GetRow(MouseEvent e) throws Exception {


            if (e.getClickCount() == 2 && !e.isConsumed()) {
                e.consume();

                //TABLE PRODUCT
                if(e.getSource()==tableProduct){
                    String CartName = tableProduct.getSelectionModel().getSelectedItems().get(0).getValue().product_name.getValue();
                    String CartQuan = tableProduct.getSelectionModel().getSelectedItems().get(0).getValue().product_quan.getValue();
                    String CartPrice = tableProduct.getSelectionModel().getSelectedItems().get(0).getValue().product_price.getValue();

                    AddController addmodal = new AddController("Add.fxml");
                    addmodal.getModal((JFXTreeTableView) e.getSource());

                    SelectToCart(CartName, CartQuan, CartPrice, CartList);
                    final TreeItem<products> cart = new RecursiveTreeItem<products>(CartList, RecursiveTreeObject::getChildren);
                    tableCart.setRoot(cart);
                    tableCart.setShowRoot(false);
                }
                if(e.getSource()==tbl_inventory){
                    //adding to FIELDS
                    String id=tbl_inventory.getSelectionModel().getSelectedItems().get(0).getValue().product_id.getValue();
                    String name=tbl_inventory.getSelectionModel().getSelectedItems().get(0).getValue().product_name.getValue();
                    String quan=tbl_inventory.getSelectionModel().getSelectedItems().get(0).getValue().product_quan.getValue();
                    String itemprice=tbl_inventory.getSelectionModel().getSelectedItems().get(0).getValue().product_price.getValue();
                    String category=tbl_inventory.getSelectionModel().getSelectedItems().get(0).getValue().product_category.getValue();
                    String supplier=tbl_inventory.getSelectionModel().getSelectedItems().get(0).getValue().product_supplier.getValue();

                    InventorySupp.setValue(supplier);
                    InventoryCateg.setValue(category);
                    InventoryID.setText(id);
                    InventoryName.setText(name);
                    InventoryQuantity.setText(quan);
                    InventoryPrice.setText(itemprice);

                    update_inventory.setDisable(false);

                }


            }
            //TABLE CART
            if(e.getSource()==tableCart){
                btnVoid.setDisable(false);
            }


        }
        @FXML
        private void AddtoInventory(){

            if(InventoryName.getText().contentEquals("") || InventoryQuantity.getText().contentEquals("") || InventoryPrice.getText().contentEquals("") ){
                JOptionPane.showMessageDialog(null,"Please input all fields","Warning",JOptionPane.WARNING_MESSAGE);
                
            }else {
                String id = InventoryID.getText();
                String name = InventoryName.getText();
                String quan = InventoryQuantity.getText();
                String itemprice = InventoryPrice.getText();
                int category = InventoryCateg.getSelectionModel().selectedIndexProperty().getValue();
                int supplier = InventorySupp.getSelectionModel().selectedIndexProperty().getValue();
                int status = 1;
                String Date =dateTime.getText();
                String sql ="Insert into tbl_products (prod_name,prod_quantity,prod_price,supplier_id,category_id,prod_status,prod_date) values(?,?,?,?,?,?,?)";
                try{
                    preparedStatement=getConnection().prepareStatement(sql);
                    preparedStatement.setString(1,name);
                    preparedStatement.setString(2,quan);
                    preparedStatement.setString(3,itemprice);
                    preparedStatement.setInt(4,supplier);
                    preparedStatement.setInt(5,category);
                    preparedStatement.setInt(6,status);
                    preparedStatement.setString(7,Date);
                    preparedStatement.executeUpdate();

                    InventoryName.setText("");
                    InventoryQuantity.setText("");
                    InventoryPrice.setText("");
                    InventoryCateg.setValue("");
                    InventorySupp.setValue("");

                    JOptionPane.showMessageDialog(null,"Successfully Added");


                }catch (Exception e){
                    e.printStackTrace();
                }


            }


        }
        private void initClock() {

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            dateTime.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

        private void SelectToCart(String name, String Quan, String Price, ObservableList list) {
            double temp = Double.parseDouble(Price);

            totalprice += temp;

            SalesPrice.setText(totalprice.toString());
            list.add(new products(name, Quan, Price));
        }


        public void getQuan(String quantity) {
            Quantity = quantity;
        }


    }


