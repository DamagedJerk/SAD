package Project;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.sun.source.tree.Tree;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class confirm implements Initializable {

    ObservableList<products> orderlist= FXCollections.observableArrayList();
    PreparedStatement preparedStatement=null;
    ResultSet resultSet=null;
    double totalprice,totalpayment,change,discountedprice=0.00;
    double discountvalue=0.00;
    private boolean response=false;
    private int customer_id=0;
    private String adminid="";

    //setters and getters

    public String getCartID() throws SQLException {
        String str="";
        preparedStatement=getConnection().prepareStatement("Select max(Cart_id) from tbl_cart");
        resultSet=preparedStatement.executeQuery();
        if(resultSet.next()){
            str=resultSet.getString("max(Cart_id)");
        }
        return str;
    }
    private void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }



    public void setResponse(boolean response) {
        this.response = response;
    }

    public boolean isResponse() {
        return response;
    }


    public void setDiscountedprice(Double discountedprice) {
        this.discountedprice = discountedprice;
    }

    public Double getDiscountedprice() {
        return discountedprice;
    }

    public void setDiscountvalue(Double discountvalue) {
        this.discountvalue = discountvalue;
    }

    public Double getDiscountvalue() {
        return discountvalue;
    }

    public Double getChange() {
        return change;
    }

    public void setChange(Double change) {
        this.change = change;
    }

    //end
    @FXML
    private JFXButton btnDiscount;
    @FXML
    private JFXButton btnConfirm;
    @FXML
    private JFXTextField total_price;
    @FXML
    private JFXTextField total_payment;

    @FXML
    private JFXTextField discountrate;

    @FXML
    private JFXTextField total_change;
    @FXML
    private JFXTextField FirstName;
    @FXML
    private JFXTextField Address;
    @FXML
    private JFXTextField Contact;
    @FXML
    private JFXTextField LastName;


    @FXML
    private JFXButton btnCashOut;

    @FXML
    private JFXButton btnClose;
    @FXML
    private JFXTreeTableView<products> OrderLIstTable;
    @FXML
    private Label LabelId;
    @FXML
    private Label ItemCount;
    @FXML
    void close() {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();

    }
    public void setFields(String totalprice,String totalpayment,String totalchange){
        setTotalprice(Double.parseDouble(totalprice));
        setTotalpayment(Double.parseDouble(totalpayment));
        setTotalchange(Double.parseDouble(totalchange));
        total_price.setText(getTotalprice()+"");
        total_payment.setText(getTotalpayment()+"");
        total_change.setText(getTotalchange()+"");

    }

    public void setAdminid(String adminid) {
        this.adminid = adminid;
    }

    public String getAdminid() {
        return adminid;
    }

    public void setTotalprice(Double totalprice) {
        this.totalprice = totalprice;
    }

    public void setTotalpayment(Double totalpayment) {
        this.totalpayment = totalpayment;
    }

    public void setTotalchange(Double totalchange) {
        this.change = totalchange;
    }

    public Double getTotalprice() {
        return totalprice;
    }

    public Double getTotalpayment() {
        return totalpayment;
    }

    public Double getTotalchange() {
        return change;
    }

    public confirm(){

    }
    private static Connection getConnection() throws SQLException {
        Connection conn;
        dbconn.getInstance();
        conn = dbconn.connect();
        return conn;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        int width=80;
        JFXTreeTableColumn<products, String> productid = new JFXTreeTableColumn<>("Id");
        productid.setPrefWidth(width);
        productid.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<products, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<products, String> productsStringCellDataFeatures) {
                return productsStringCellDataFeatures.getValue().getValue().product_id;
            }
        });
        JFXTreeTableColumn<products, String> productname = new JFXTreeTableColumn<>("Name");
        productname.setPrefWidth(width);
        productname.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<products, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<products, String> productsStringCellDataFeatures) {
                return productsStringCellDataFeatures.getValue().getValue().product_name;
            }
        });
        JFXTreeTableColumn<products, String> productquan = new JFXTreeTableColumn<>("Quantity");
        productquan.setPrefWidth(width);
        productquan.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<products, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<products, String> productsStringCellDataFeatures) {
                return productsStringCellDataFeatures.getValue().getValue().product_quan;
            }
        });
        JFXTreeTableColumn<products, String> prodprice = new JFXTreeTableColumn<>("Price");
        prodprice.setPrefWidth(width);
        prodprice.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<products, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<products, String> productsStringCellDataFeatures) {
                return productsStringCellDataFeatures.getValue().getValue().product_price;
            }
        });
        refreshtable();
        final TreeItem<products> root = new RecursiveTreeItem<products>(orderlist, RecursiveTreeObject::getChildren);
        OrderLIstTable.getColumns().setAll(productid,productname,productquan,prodprice);
        OrderLIstTable.setRoot(root);
        OrderLIstTable.setShowRoot(false);
    }
    private void refreshtable(){
        try{
            int i=0;
            //JOptionPane.showMessageDialog(null,getCartID());
            orderlist.clear();
            String sql="Select o.product_id,p.prod_name,o.order_quantity,o.total_price from tbl_order o JOIN tbl_products p ON o.product_id = p.prod_id WHERE o.Cart_id=(Select max(Cart_id) from tbl_cart)";
            preparedStatement = getConnection().prepareStatement(sql);
            //preparedStatement.setString(1,getCartID());
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                i++;
                orderlist.add(new products(resultSet.getString("product_id"),resultSet.getString("prod_name"),resultSet.getString("order_quantity"),resultSet.getString("total_price")));
            }
            LabelId.setText(getCartID());
            ItemCount.setText(i+"");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    private void applydiscount() throws Exception{

        FXMLLoader loader= new FXMLLoader(getClass().getResource("Alert.fxml"));
        Alert controller=new Alert();
        loader.setController(controller);
        Parent root =loader.load();

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initOwner(btnDiscount.getScene().getWindow());
        stage.setScene(new Scene(root));
        stage.showAndWait();

        if(controller.isResponse()==true) {
            double rate = Double.parseDouble(discountrate.getText());
            double totalprice = Double.parseDouble(total_price.getText());
            double totalPayment = Double.parseDouble(total_payment.getText());
            double totalChange;

            rate = rate / 100;
            discountvalue = totalprice * rate;
            totalprice = totalprice - discountvalue;
            change = totalPayment - totalprice;
            change=Math.round(change*100);
            change=change/100;
            totalprice=Math.round(totalprice*100);
            totalprice=totalprice/100;
            total_change.setText(change+ "");
            total_price.setText(totalprice + "");
            setDiscountedprice(totalprice);
            //logs activity
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd ");
            DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm:ss");
            preparedStatement=getConnection().prepareStatement("Insert into tbl_activitylog values(null,?,?,?,?)");
            preparedStatement.setString(1,controller.getAdminID());
            preparedStatement.setString(2,"Granted Discount");
            preparedStatement.setString(3, LocalDateTime.now().format(formatter));
            preparedStatement.setString(4,LocalDateTime.now().format(time));
            preparedStatement.executeUpdate();
            //
            btnDiscount.setDisable(true);
            discountrate.setText("");
        }
    }
    @FXML
    private void doCOnfirm(){
        //get employee_id
        try {
            if(isCustomerExist()==false){
                try{
                    preparedStatement=getConnection().prepareStatement("Insert into tbl_customer values(null,?,?,?,?)");
                    preparedStatement.setString(1,FirstName.getText());
                    //preparedStatement.setString(2,Mid_name.getText());
                    preparedStatement.setString(2,LastName.getText());
                    preparedStatement.setString(3,Address.getText());
                    preparedStatement.setString(4,Contact.getText());
                    preparedStatement.executeUpdate();
                    setResponse(true);
                    getId();
                    close();
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
 }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    private boolean isCustomerExist() {
        try {
            preparedStatement = getConnection().prepareStatement("Select *from tbl_customer where cus_firstname=? and cus_lastname=?");
            preparedStatement.setString(1, FirstName.getText());
            preparedStatement.setString(2, LastName.getText());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int result = Integer.parseInt(resultSet.getString("cus_id"));
                setCustomer_id(result);
                setResponse(true);
                close();
                return true;

            }

        }catch (Exception ex){
        ex.printStackTrace();
    }
        return false;
    }
    public void getId(){
        try{
            preparedStatement=getConnection().prepareStatement("Select max(cus_id) from tbl_customer");
            resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                int result=Integer.parseInt(resultSet.getString("max(cus_id)"));
                setCustomer_id(result);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
    public int getCustomer_id() {
        return customer_id;
    }

}