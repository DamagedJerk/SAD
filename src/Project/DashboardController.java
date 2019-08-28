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
import javafx.event.Event;
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
    private Label ItemCount;
    @FXML
    private Label Time;


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
    private JFXTreeTableView<Inventory> StockInventoryTable;

    @FXML
    private JFXTreeTableView<Stocks> StockInTable;

    @FXML
    private JFXButton btnVoid;
    @FXML
    private JFXButton btnCashOut;

    @FXML
    private Tab tabSales;

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
    private JFXTextField SalesPayment;
    @FXML
    private JFXTextField SalesChange;
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
    private int CurrentID=0;


    //InventoryTab

    @FXML
    private JFXTextField InventoryID;

    @FXML
    private JFXComboBox<String> InventorySupp;

    @FXML
    private JFXComboBox<String> InventoryCateg;
    @FXML
    private JFXComboBox<String> Inventory_Status;

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
    private JFXButton new_entry;
    @FXML
    private JFXTreeTableView<Inventory> tbl_inventory;

    //lists
    ObservableList<Inventory> InventoryList = FXCollections.observableArrayList();
    ObservableList<products> productList = FXCollections.observableArrayList();
    private ObservableList<products> CartList = FXCollections.observableArrayList();
    ObservableList<Inventory> StockinList = FXCollections.observableArrayList();
    ObservableList<Stocks> StocksTableList = FXCollections.observableArrayList();


    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private Image userpic = new Image("/resources/user.png");

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
        Inventory_Status.getItems().add("INACTIVE");
        Inventory_Status.getItems().add("ACTIVE");
        Inventory_Status.setValue("ACTIVE");



        //tbl_product
        int width = 140;
        JFXTreeTableColumn<products, String> ProductId = new JFXTreeTableColumn<>("Id");
        ProductId.setPrefWidth(width);
        ProductId.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<products, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<products, String> productsStringCellDataFeatures) {
                return productsStringCellDataFeatures.getValue().getValue().product_id;
            }
        });

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

        refreshProductMenu();
        final TreeItem<products> root = new RecursiveTreeItem<products>(productList, RecursiveTreeObject::getChildren);
        tableProduct.getColumns().setAll(ProductId,ProductName, ProductQuantity, ProductPrice);
        tableProduct.setRoot(root);
        tableProduct.setShowRoot(false);

        //tblCart
        JFXTreeTableColumn<products, String> CartItemId = new JFXTreeTableColumn<>("Id");
        CartItemId.setPrefWidth(width);
        CartItemId.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<products, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<products, String> productsStringCellDataFeatures) {
                return productsStringCellDataFeatures.getValue().getValue().product_id;
            }
        });
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
        tableCart.getColumns().setAll(CartItemId,CartName, CartQuantity, CartPrice);
        // tableCart.setRoot(cart);
        //tableCart.setShowRoot(false);

        //tabInventory
        int widthInventory=130;
        tabSales.setOnSelectionChanged(event -> {
            if(tabSales.isSelected()){
                refreshProductMenu();
            }
        });//

        //InventoryTable
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
        JFXTreeTableColumn<Inventory, String> InventoryStatus= new JFXTreeTableColumn<>("Status");
        InventoryStatus.setPrefWidth(widthInventory);
        InventoryStatus.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Inventory, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Inventory, String> productsStringCellDataFeatures) {
                return productsStringCellDataFeatures.getValue().getValue().status;
            }
        });

        refreshInventoryTable(InventoryList);
        final TreeItem<Inventory> inventoryList = new RecursiveTreeItem<Inventory>(InventoryList, RecursiveTreeObject::getChildren);

        tbl_inventory.getColumns().setAll(InventoryId, InventoryName, InventoryQuantity, InventoryPrice, InventoryCategory, InventoryStatus,InventorySupplier);
        tbl_inventory.setRoot(inventoryList);
        tbl_inventory.setShowRoot(false);

        tabInventory.setOnSelectionChanged(event -> {
            if (tabInventory.isSelected() && tbl_inventory.getCurrentItemsCount()==0 ) {

            }
            });
        CurrentID=Integer.parseInt(setID());
        InventoryID.setText(CurrentID+"");

        SalesPayment.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {

                if(keyEvent.getCharacter().contains("") && SalesPayment.getLength()>1) {
                    //JOptionPane.showMessageDialog(null, "KeyTyped 1");
                    SalesChange.setText("");
                    if(keyEvent.getCharacter().contains("1") || keyEvent.getCharacter().contains("2") || keyEvent.getCharacter().contains("3") || keyEvent.getCharacter().contains("4") || keyEvent.getCharacter().contains("5") || keyEvent.getCharacter().contains("6") || keyEvent.getCharacter().contains("7") || keyEvent.getCharacter().contains("8") || keyEvent.getCharacter().contains("9") || keyEvent.getCharacter().contains("0")){
                        //JOptionPane.showMessageDialog(null, "KeyTyped 2");
                        Double Sales=Double.parseDouble(SalesPrice.getText());
                        Double payment=Double.parseDouble(SalesPayment.getText());
                        if(payment>=Sales){
                            Double Change=payment-Sales;
                            SalesChange.setText(""+Change);
                        }else{
                            SalesChange.setText("");
                        }
                    }
                }else{
                    //JOptionPane.showMessageDialog(null, "KeyTyped 3");
                    keyEvent.consume();
                    //Wa pa sya pa nahuman . . . . Error Trapping in case Backspace or space ang ipindut dapat ma consume;
                }
            }
        });
        //TabStockIn
        int stockincolumnwidth=80;
        JFXTreeTableColumn<Inventory, String> StockinId = new JFXTreeTableColumn<>("ID");
        StockinId.setPrefWidth(stockincolumnwidth);

        StockinId.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Inventory, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Inventory, String> productsStringCellDataFeatures) {
                return productsStringCellDataFeatures.getValue().getValue().product_id;
            }
        });
        JFXTreeTableColumn<Inventory, String> StockInName = new JFXTreeTableColumn<>("Name");
        StockInName .setPrefWidth(stockincolumnwidth);
        StockInName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Inventory, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Inventory, String> productsStringCellDataFeatures) {
                return productsStringCellDataFeatures.getValue().getValue().product_name;
            }
        });
        JFXTreeTableColumn<Inventory, String> StockInQuantity= new JFXTreeTableColumn<>("Quantity");
        StockInQuantity.setPrefWidth(stockincolumnwidth);
        StockInQuantity.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Inventory, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Inventory, String> productsStringCellDataFeatures) {
                return productsStringCellDataFeatures.getValue().getValue().product_quan;
            }
        });
        JFXTreeTableColumn<Inventory, String> StockInPrice= new JFXTreeTableColumn<>("Price");
        StockInPrice.setPrefWidth(stockincolumnwidth);
        StockInPrice.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Inventory, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Inventory, String> productsStringCellDataFeatures) {
                return productsStringCellDataFeatures.getValue().getValue().product_price;
            }
        });
        JFXTreeTableColumn<Inventory, String> StockInCat= new JFXTreeTableColumn<>("Category");
        StockInCat.setPrefWidth(stockincolumnwidth);
        StockInCat.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Inventory, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Inventory, String> productsStringCellDataFeatures) {
                return productsStringCellDataFeatures.getValue().getValue().product_category;
            }
        });
        JFXTreeTableColumn<Inventory, String> StockInSupp= new JFXTreeTableColumn<>("Supplier");
        StockInSupp.setPrefWidth(stockincolumnwidth);
        StockInSupp.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Inventory, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Inventory, String> productsStringCellDataFeatures) {
                return productsStringCellDataFeatures.getValue().getValue().product_supplier;
            }
        });
        JFXTreeTableColumn<Inventory, String> StockInStatus= new JFXTreeTableColumn<>("Status");
        StockInStatus.setPrefWidth(stockincolumnwidth);
        StockInStatus.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Inventory, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Inventory, String> productsStringCellDataFeatures) {
                return productsStringCellDataFeatures.getValue().getValue().status;
            }
        });
        refreshInventoryTable(StockinList);
        final TreeItem<Inventory> stockinList = new RecursiveTreeItem<Inventory>(StockinList, RecursiveTreeObject::getChildren);

        StockInventoryTable.getColumns().setAll(StockinId, StockInName, StockInQuantity, StockInPrice, StockInCat, StockInStatus,StockInSupp);
        StockInventoryTable.setRoot(stockinList);
        StockInventoryTable.setShowRoot(false);

        //StockInTable

        int stocktablewidth=80;
        JFXTreeTableColumn<Stocks, String> StockId = new JFXTreeTableColumn<>("ID");
        StockId.setPrefWidth(stocktablewidth);

        StockId.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Stocks, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Stocks, String> productsStringCellDataFeatures) {
                return productsStringCellDataFeatures.getValue().getValue().product_id;
            }
        });
        JFXTreeTableColumn<Stocks, String> StockName = new JFXTreeTableColumn<>("Name");
        StockName .setPrefWidth(stocktablewidth);
        StockName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Stocks, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Stocks, String> productsStringCellDataFeatures) {
                return productsStringCellDataFeatures.getValue().getValue().product_name;
            }
        });
        JFXTreeTableColumn<Stocks, String> Stocks= new JFXTreeTableColumn<>("Current Stocks");
        Stocks.setPrefWidth(stocktablewidth);
        Stocks.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Stocks, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Stocks, String> productsStringCellDataFeatures) {
                return productsStringCellDataFeatures.getValue().getValue().current_stocks;
            }
        });
        JFXTreeTableColumn<Stocks, String> Entry_type= new JFXTreeTableColumn<>("Entry Type");
        Entry_type.setPrefWidth(stocktablewidth);
        Entry_type.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Stocks, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Stocks, String> productsStringCellDataFeatures) {
                return productsStringCellDataFeatures.getValue().getValue().Entry_type;
            }
        });
        JFXTreeTableColumn<Stocks, String> Last_entry= new JFXTreeTableColumn<>("Last Entry");
        Last_entry.setPrefWidth(stocktablewidth);
        Last_entry.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Stocks, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Stocks, String> productsStringCellDataFeatures) {
                return productsStringCellDataFeatures.getValue().getValue().Last_Entry;
            }
        });
        JFXTreeTableColumn<Stocks, String> StockInDate= new JFXTreeTableColumn<>("Date of Entry");
        StockInDate.setPrefWidth(stocktablewidth);
        StockInDate.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Stocks, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Stocks, String> productsStringCellDataFeatures) {
                return productsStringCellDataFeatures.getValue().getValue().Date;
            }
        });
        JFXTreeTableColumn<Stocks, String> StockInTime= new JFXTreeTableColumn<>("Time of Entry");
        StockInTime.setPrefWidth(stocktablewidth);
        StockInTime.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Stocks, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Stocks, String> productsStringCellDataFeatures) {
                return productsStringCellDataFeatures.getValue().getValue().Time;
            }
        });

        final TreeItem<Stocks> StocksTablesList = new RecursiveTreeItem<Stocks>(StocksTableList, RecursiveTreeObject::getChildren);

        StockInTable.getColumns().setAll(StockId, StockName,Stocks, Entry_type, Last_entry, StockInDate,StockInTime);//balik diri
        StockInTable.setRoot(StocksTablesList );
        StockInTable.setShowRoot(false);

    }

    private void setComboBOx(JFXComboBox comboBOx,String query,String columnname){
        try {

            preparedStatement = getConnection().prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
               comboBOx.getItems().add(resultSet.getString(columnname));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    class products extends RecursiveTreeObject<products> {
        StringProperty product_id;
        StringProperty product_name;
        StringProperty product_quan;
        StringProperty product_price;

        public products(String id ,String prodname, String quan, String price) {
            this.product_id=new SimpleStringProperty(id);
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
        StringProperty status;

        public Inventory(String id, String prodname, String quan, String price, String cat , String supp,String status) {
            this.product_id = new SimpleStringProperty(id);
            this.product_name = new SimpleStringProperty(prodname);
            this.product_quan = new SimpleStringProperty(quan);
            this.product_price = new SimpleStringProperty(price);
            this.product_category = new SimpleStringProperty(cat);
            this.product_supplier = new SimpleStringProperty(supp);
            this.status= new SimpleStringProperty(status);

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
        @FXML
        void GetRow(MouseEvent e) throws Exception {


            if (e.getClickCount() == 2 && !e.isConsumed()) {
                e.consume();

                //TABLE PRODUCT
                if(e.getSource()==tableProduct){
                    int Quantity=0;
                    String CartItemId=tableProduct.getSelectionModel().getSelectedItems().get(0).getValue().product_id.getValue();
                    String CartName = tableProduct.getSelectionModel().getSelectedItems().get(0).getValue().product_name.getValue();
                    //String CartQuan = tableProduct.getSelectionModel().getSelectedItems().get(0).getValue().product_quan.getValue();
                    String CartPrice = tableProduct.getSelectionModel().getSelectedItems().get(0).getValue().product_price.getValue();

                    //AddController addmodal = new AddController("Add.fxml");
                    //addmodal.getModal((JFXTreeTableView) e.getSource());

                    FXMLLoader loader= new FXMLLoader(getClass().getResource("Add.fxml"));
                    AddController controller=new AddController();
                    loader.setController(controller);
                    Parent root =loader.load();

                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.initStyle(StageStyle.TRANSPARENT);
                    stage.initOwner(tableProduct.getScene().getWindow());
                    stage.setScene(new Scene(root));
                    stage.showAndWait();
                    stage.setOnShowing(windowEvent -> {
                            PromptAdd.setOnAction(actionEvent -> {
                                try{
                                    controller.AddQuantity();
                                }catch (Exception ex){
                                    ex.printStackTrace();
                                }

                            });

                    });
                    //Quantity=Integer.parseInt(controller.getQuan());
                    if(!controller.getQuan().contentEquals("")){

                        SelectToCart(CartItemId,CartName, controller.getQuan(), CartPrice, CartList);
                        final TreeItem<products> cart = new RecursiveTreeItem<products>(CartList, RecursiveTreeObject::getChildren);
                        tableCart.setRoot(cart);
                        tableCart.setShowRoot(false);
                        total_items();
                        btnCashOut.setDisable(false);
                    }
                }
                //InventoryTab Table
                if(e.getSource()==tbl_inventory){
                    //adding to FIELDS
                    String id=tbl_inventory.getSelectionModel().getSelectedItems().get(0).getValue().product_id.getValue();
                    String name=tbl_inventory.getSelectionModel().getSelectedItems().get(0).getValue().product_name.getValue();
                    String quan=tbl_inventory.getSelectionModel().getSelectedItems().get(0).getValue().product_quan.getValue();
                    String itemprice=tbl_inventory.getSelectionModel().getSelectedItems().get(0).getValue().product_price.getValue();
                    String category=tbl_inventory.getSelectionModel().getSelectedItems().get(0).getValue().product_category.getValue();
                    String supplier=tbl_inventory.getSelectionModel().getSelectedItems().get(0).getValue().product_supplier.getValue();
                    String status=tbl_inventory.getSelectionModel().getSelectedItems().get(0).getValue().status.getValue();

                    InventorySupp.setValue(supplier);
                    InventoryCateg.setValue(category);
                    Inventory_Status.setValue(status);
                    InventoryID.setText(id);
                    InventoryName.setText(name);
                    InventoryQuantity.setText(quan);
                    InventoryPrice.setText(itemprice);

                    update_inventory.setDisable(false);
                    add_inventory.setDisable(true);
                    add_inventory.setVisible(false);
                    new_entry.setVisible(true);
                    new_entry.setDisable(false);
                }


            }
            //TABLE CART
            if(e.getSource()==tableCart){
                btnVoid.setDisable(false);

            }
        }
        @FXML
        private void CashOut(){
            System.out.print("here");
            if(SalesPrice.getText().contentEquals("") || SalesPrice.getText().contentEquals("") || SalesChange.getText().contentEquals("")){
                JOptionPane.showMessageDialog(null,"Please input all fields","Warning",JOptionPane.WARNING_MESSAGE);
            }else{
                //JOptionPane.showMessageDialog(null,"hello");
                //Cashing Out . . .. . Partial dapat pani
                String totalPrice=SalesPrice.getText();
                String totalPayment=SalesPayment.getText();
                String totalChange=SalesChange.getText();
                int totalitem=Integer.parseInt(ItemCount.getText());
                //int id=Integer.parseInt(tableCart.getSelectionModel().getModelItem(0).getValue().product_id.getValue());
                try{
                    //

                    String sql="Insert into tbl_Cart (Cart_id) values(null)";
                    preparedStatement=getConnection().prepareStatement(sql);
                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                    resultSet.close();


                    preparedStatement=getConnection().prepareStatement("Select max(Cart_id) from tbl_cart");
                    resultSet=preparedStatement.executeQuery();
                    String cartid="";
                    if(resultSet.next()){
                            cartid=resultSet.getString("max(Cart_id)");
                            //JOptionPane.showMessageDialog(null,cartid+" CARTID");
                        }

                    //inserting to tbl_order or order line
                    sql="Insert into tbl_order values(null,?,?,?,?)";
                    for(int i=0;i<tableCart.getCurrentItemsCount();i++){
                        int id=Integer.parseInt(tableCart.getSelectionModel().getModelItem(i).getValue().product_id.getValue());
                        int quan=Integer.parseInt(tableCart.getSelectionModel().getModelItem(i).getValue().product_quan.getValue());

                        preparedStatement=getConnection().prepareStatement(sql);
                        preparedStatement.setInt(1,id);
                        preparedStatement.setInt(2,quan);
                        preparedStatement.setString(3,tableCart.getSelectionModel().getModelItem(i).getValue().product_price.getValue());
                        preparedStatement.setString(4,cartid);
                        preparedStatement.executeUpdate();


                    }
                    preparedStatement.close();
                    resultSet.close();
                    JOptionPane.showMessageDialog(null,"Succeess");
                    // to report things

                    sql="Update tbl_cart SET total_payment=?,total_price=?,total_change=?,total_item=? where Cart_id=?";
                    preparedStatement=getConnection().prepareStatement(sql);
                    preparedStatement.setString(1,totalPayment);
                    preparedStatement.setString(2,totalPrice);
                    preparedStatement.setString(3,totalChange);
                    preparedStatement.setInt(4,totalitem);
                    preparedStatement.setString(5,cartid);
                    preparedStatement.executeUpdate();
                    preparedStatement.close();

                    //get employee_id
                    String emp_id="";
                    preparedStatement=getConnection().prepareStatement("Select user_id from tbl_employee");
                    resultSet=preparedStatement.executeQuery();
                    if(resultSet.next()){
                        emp_id=resultSet.getString("user_id");

                    }

                    //insert int tbl_report
                    sql="Insert into tbl_report values(null,?,NULL,?,?)";
                    preparedStatement=getConnection().prepareStatement(sql);
                    preparedStatement.setString(1,cartid);
                    preparedStatement.setString(2,emp_id);//employeeid
                    preparedStatement.setString(3,dateTime.getText());//date
                    preparedStatement.executeUpdate();

                    SalesPrice.setText("");
                    SalesChange.setText("");
                    SalesPayment.setText("");
                    btnCashOut.setDisable(true);
                    CartList.clear();
                    ItemCount.setText("0");
                    refreshProductMenu();

                    //kulang nalang mag print ug resibo

                }catch(Exception ex){
                    ex.printStackTrace();
                }


                }
            }


        @FXML
        private void UpdateInventory(){
            if(InventoryName.getText().contentEquals("") || InventoryQuantity.getText().contentEquals("") || InventoryPrice.getText().contentEquals("") ){
                JOptionPane.showMessageDialog(null,"Please input all fields","Warning",JOptionPane.WARNING_MESSAGE);

            }else {
                int id =Integer.parseInt(InventoryID.getText()) ;
                String name = InventoryName.getText();
                String quan = InventoryQuantity.getText();
                String itemprice = InventoryPrice.getText();
                int category = InventoryCateg.getSelectionModel().selectedIndexProperty().getValue();
                int supplier = InventorySupp.getSelectionModel().selectedIndexProperty().getValue();
                int status = Inventory_Status.getSelectionModel().selectedIndexProperty().getValue();
                //String Date =dateTime.getText();
                String sql ="UPDATE tbl_products SET prod_name =?,prod_quantity =?,prod_price=?,category_id=?,supplier_id=?,prod_status=? WHERE prod_id = ?";
                try{

                    category++;
                    supplier++;
                    preparedStatement=getConnection().prepareStatement(sql);
                    preparedStatement.setString(1,name);
                    preparedStatement.setString(2,quan);
                    preparedStatement.setString(3,itemprice);
                    preparedStatement.setInt(4,category);
                    preparedStatement.setInt(5,supplier);
                    preparedStatement.setInt(6,status);
                    preparedStatement.setInt(7,id);


                    preparedStatement.executeUpdate();
                    refreshInventoryTable(InventoryList);
                    NewField();
                }catch (Exception e){
                    e.printStackTrace();
                }


            }

        }
        @FXML
        private void AddtoInventory(){

            if(InventoryName.getText().contentEquals("") || InventoryQuantity.getText().contentEquals("") || InventoryPrice.getText().contentEquals("") ){
                JOptionPane.showMessageDialog(null,"Please input all fields","Warning",JOptionPane.WARNING_MESSAGE);
                
            }else {
                int id =Integer.parseInt(InventoryID.getText()) ;
                String name = InventoryName.getText();
                String quan = InventoryQuantity.getText();
                String itemprice = InventoryPrice.getText();
                int category = InventoryCateg.getSelectionModel().selectedIndexProperty().getValue();
                int supplier = InventorySupp.getSelectionModel().selectedIndexProperty().getValue();
                int status = Inventory_Status.getSelectionModel().selectedIndexProperty().getValue();
                String Date =dateTime.getText();
                String sql ="Insert into tbl_products values(?,?,?,?,?,?,?,?)";
                //JOptionPane.showMessageDialog(null,sql+"");
                try{

                    category++;
                    supplier++;
                    preparedStatement=getConnection().prepareStatement(sql);
                    preparedStatement.setInt(1,id);
                    preparedStatement.setString(2,name);
                    preparedStatement.setInt(3,supplier);
                    preparedStatement.setString(4,quan);
                    preparedStatement.setString(5,itemprice);
                    preparedStatement.setString(6,Date);
                    preparedStatement.setInt(7,status);
                    preparedStatement.setInt(8,category);

                    preparedStatement.executeUpdate();
                    refreshInventoryTable(InventoryList);
                    CurrentID++;
                    NewField();
                }catch (Exception e){
                    e.printStackTrace();
                }


            }


        }
        @FXML
        private void NewEntry(){
            NewField();
            new_entry.setDisable(true);
            new_entry.setVisible(false);
            add_inventory.setVisible(true);
            add_inventory.setDisable(false);
            update_inventory.setDisable(true);
        }

        private void NewField(){
            InventoryID.setText(CurrentID+"");
            InventoryName.setText("");
            InventoryQuantity.setText("");
            InventoryPrice.setText("");
            InventoryCateg.setValue("");
            InventorySupp.setValue("");
            Inventory_Status.setValue("ACTIVE");

        }


        private String setID(){

            int Temp=1;

            for(int i=1;i<tbl_inventory.getCurrentItemsCount()+1;i++){
                Temp++;

                }
            return ""+Temp;

        }
        private void total_items(){
                ItemCount.setText(tableCart.getCurrentItemsCount()+"");
        }

        private void refreshInventoryTable(ObservableList list){
            try {
                String status;
                list.clear();
                String sql = "select p.prod_id,p.prod_name,p.prod_quantity,p.prod_price,c.cat_description,s.company_name,p.prod_status FROM tbl_products p JOIN tbl_supplier s ON p.supplier_id = s.supplier_id JOIN tbl_category c ON p.category_id=c.category_id Group by p.prod_id";
                preparedStatement = getConnection().prepareStatement(sql);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int temp=Integer.parseInt(resultSet.getString("prod_status"));
                    if(temp==0){
                        status="INACTIVE";
                    }else{
                        status="ACTIVE";
                    }
                    list.add(new Inventory(resultSet.getString("prod_id"), resultSet.getString("prod_name"), resultSet.getString("prod_quantity"),resultSet.getString("prod_price"), resultSet.getString("cat_description"), resultSet.getString("company_name"),status));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        private void refreshProductMenu(){
            try {
                productList.clear();
                String sql = "Select * from tbl_products where prod_status=1";
                preparedStatement = getConnection().prepareStatement(sql);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    //JOptionPane.showMessageDialog(null,resultSet.getString("prod_name"));
                    productList.add(new products(resultSet.getString("prod_id"),resultSet.getString("prod_name"), resultSet.getString("prod_quantity"), resultSet.getString("prod_price")));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        private void initClock() {

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd ");
            DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm:ss");
            dateTime.setText(LocalDateTime.now().format(formatter));
            Time.setText(LocalDateTime.now().format(time));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

        private void SelectToCart(String id,String name, String Quan, String Price, ObservableList list) {
            double price= Double.parseDouble(Price);
            double quantity=Double.parseDouble(Quan);
            totalprice += price*quantity;

            SalesPrice.setText(totalprice.toString());
            list.add(new products(id,name, Quan, Price));
        }


        public void setQuan(String quantity) {
            Quantity = quantity;
        }
        public String getQuantity(){
          return this.Quantity;
        }


    }


