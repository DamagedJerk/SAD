package Project;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.sun.source.tree.Tree;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class confirm implements Initializable {

    ObservableList<products> orderlist= FXCollections.observableArrayList();
    PreparedStatement preparedStatement=null;
    ResultSet resultSet=null;
    Double totalprice,totalpayment,totalchange,discountedprice=0.0;

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

    public void setTotalprice(Double totalprice) {
        this.totalprice = totalprice;
    }

    public void setTotalpayment(Double totalpayment) {
        this.totalpayment = totalpayment;
    }

    public void setTotalchange(Double totalchange) {
        this.totalchange = totalchange;
    }

    public Double getTotalprice() {
        return totalprice;
    }

    public Double getTotalpayment() {
        return totalpayment;
    }

    public Double getTotalchange() {
        return totalchange;
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
    private void applydiscount(){

        //dapat naay 
        Double dbl=Double.parseDouble(discountrate.getText());
        Double totalprice =Double.parseDouble(total_price.getText());
        Double totalPayment = Double.parseDouble(total_payment.getText());
        Double totalChange;

        dbl=dbl/100;
        discountedprice=totalprice*dbl;
        totalprice=totalprice-discountedprice;
        totalChange=totalPayment-totalprice;
        total_change.setText(totalChange+"");
        total_price.setText(totalprice+"");

        btnDiscount.setDisable(true);
        discountrate.setText("");
    }


}
