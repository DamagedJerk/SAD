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
    private JFXButton btnVoid;

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
        Inventory_Status.getItems().add("INACTIVE");
        Inventory_Status.getItems().add("ACTIVE");
        Inventory_Status.setValue("ACTIVE");



        //tbl_product
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

        refreshProductMenu();
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
        tabSales.setOnSelectionChanged(event -> {
            if(tabSales.isSelected()){
                refreshProductMenu();
            }
        });

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

        refreshInventoryTable();
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
                    String CartName = tableProduct.getSelectionModel().getSelectedItems().get(0).getValue().product_name.getValue();
                    String CartQuan = tableProduct.getSelectionModel().getSelectedItems().get(0).getValue().product_quan.getValue();
                    String CartPrice = tableProduct.getSelectionModel().getSelectedItems().get(0).getValue().product_price.getValue();

                    AddController addmodal = new AddController("Add.fxml");
                    addmodal.getModal((JFXTreeTableView) e.getSource());
                    JOptionPane.showMessageDialog(null,addmodal+"");

                    SelectToCart(CartName, CartQuan, CartPrice, CartList);
                    final TreeItem<products> cart = new RecursiveTreeItem<products>(CartList, RecursiveTreeObject::getChildren);
                    tableCart.setRoot(cart);
                    tableCart.setShowRoot(false);
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
                    refreshInventoryTable();
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
                    refreshInventoryTable();
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
        private void refreshInventoryTable(){
            try {
                String status;
                InventoryList.clear();
                String sql = "select p.prod_id,p.prod_name,p.prod_quantity,p.prod_price,c.cat_description,s.company_name,p.prod_status FROM tbl_products p JOIN tbl_supplier s ON p.supplier_id = s.supplier_id JOIN tbl_category c ON p.category_id=c.category_id";
                preparedStatement = getConnection().prepareStatement(sql);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int temp=Integer.parseInt(resultSet.getString("prod_status"));
                    if(temp==0){
                        status="INACTIVE";
                    }else{
                        status="ACTIVE";
                    }
                    InventoryList.add(new Inventory(resultSet.getString("prod_id"), resultSet.getString("prod_name"), resultSet.getString("prod_quantity"),resultSet.getString("prod_price"), resultSet.getString("cat_description"), resultSet.getString("company_name"),status));
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
                    productList.add(new products(resultSet.getString("prod_name"), resultSet.getString("prod_quantity"), resultSet.getString("prod_price")));
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

        private void SelectToCart(String name, String Quan, String Price, ObservableList list) {
            double temp = Double.parseDouble(Price);

            totalprice += temp;

            SalesPrice.setText(totalprice.toString());
            list.add(new products(name, Quan, Price));
        }


        public void setQuan(String quantity) {
            Quantity = quantity;
        }
        public String getQuantity(){
          return this.Quantity;
        }


    }


