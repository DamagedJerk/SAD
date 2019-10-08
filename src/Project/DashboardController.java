package Project;

import animatefx.animation.*;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.event.Event;
import javafx.event.EventHandler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;

import javafx.scene.control.SingleSelectionModel;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;



import javax.swing.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


public class DashboardController implements Initializable {


    @FXML
    private Label dateTime;
    @FXML
    private Label InventoryCount;
    @FXML
    private Label ItemCount;
    @FXML
    private Label Time;
    @FXML
    private Label stockinerror;
    @FXML
    private JFXButton btn_supplier;
    @FXML
    private JFXButton btnLogOut;
    @FXML
    private JFXButton btn_addcategory;
    @FXML
    private Label lblName;
    @FXML
    private Label LabelId;

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
    private JFXTreeTableView<Receipts> ReportTable;

    @FXML
    private JFXTreeTableView<Stocks> StockInTable;
    @FXML
    private JFXTreeTableView<Stocks> StockOutTable;
    @FXML
    private JFXTextField StockIntotalprice;
    @FXML
    private JFXTextField StockinAmount;
    @FXML
    private JFXTextField SalesSearch;
    @FXML
    private JFXTextField InventorySearch;
    @FXML
    private JFXTextField StockInSearch;
    @FXML
    private JFXTextField StockListSearch;

    @FXML
    private JFXButton btnVoid;
    @FXML
    private JFXButton btnCashOut;

    @FXML
    public Tab tabSales;

    @FXML
    private Tab tabInventory;

    @FXML
    public Tab tabStock;

    @FXML
    public Tab tabReport;

    @FXML
    private AnchorPane rootPane;
    @FXML
    private Tab tabLog;

    @FXML
    public ImageView img;
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



    private int CurrentID=0;
    private int totalitems=0;
    private int dateid=0;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm:ss");
    private dbconn database;
    private Connection connect;
    private Map<String, Object> map;
    private double balance=0;



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
    private JFXComboBox<String> enddate;

    @FXML
    private JFXComboBox<String> startingdate;
    @FXML
    private JFXComboBox<String> cbologdate;
    @FXML
    private JFXComboBox<String> cboReasons;

    @FXML
    private JFXComboBox<String> cboActivity;
    @FXML
    private JFXTextField InventoryQuantity;
    @FXML
    private JFXTextField InventoryPrice;
    @FXML
    private JFXTextField InventoryName;
    @FXML
    private JFXTextField InventoryCost;
    @FXML
    private JFXButton add_inventory;
    @FXML
    private JFXButton update_inventory;
    @FXML
    private JFXButton new_entry;
    @FXML
    private JFXTreeTableView<Inventory> tbl_inventory;

    @FXML
    private JFXButton InventoryReports;
    @FXML
    private JFXButton StockInReports;


    //stock-in tab fields
    @FXML
    private JFXTextField StockinID;
    @FXML
    private JFXComboBox<String> StockinCat;
    @FXML
    private JFXTextField StockInITEMID;
    @FXML
    private JFXTextField StockInName;
    @FXML
    private JFXTextField StockinCost;
    @FXML
    private JFXTextField StockInStock;
    @FXML
    private JFXButton btnStockIn;
    @FXML
    private JFXButton btnStockOut;

    @FXML
    private TextArea AreaLogs;
    @FXML
    private JFXComboBox<Integer> cbo_size;
    //Reports Tab
    @FXML
    private JFXRadioButton radioDaily;

    @FXML
    private JFXRadioButton radioMonthly;
    @FXML
    private JFXRadioButton radio_stockin;
    @FXML
    private JFXRadioButton radio_stockOut;

    @FXML
    private LineChart<String,Number> LineChart;
    @FXML
    private ScrollPane scrollpane;
    @FXML
    private LineChart<String,Number> MonthlyChart;
    @FXML
    private Label LineChart_Title;
    @FXML
    private Label lbl_balance;
    @FXML
    private Label lblTotalSales;

    @FXML
    private JFXButton btn_loadChart;

    @FXML
    private JFXTabPane tabpane;
    @FXML
    private LineChart<String,Number> YearlyChart;
    @FXML
    private javafx.scene.chart.PieChart PieChart;
    @FXML
    private TextArea AdminLogs;
    @FXML
    private JFXRadioButton radioYear;
    @FXML
    private JFXComboBox<String> cboYear;
    @FXML
    private JFXComboBox<String> cboMonthly;





    //

    //lists
    ObservableList<Inventory> InventoryList = FXCollections.observableArrayList();
    ObservableList<products> productList = FXCollections.observableArrayList();
    ObservableList<products> CartList = FXCollections.observableArrayList();
    ObservableList<Inventory> StockinList = FXCollections.observableArrayList();
    ObservableList<Stocks> StocksTableList = FXCollections.observableArrayList();
    ObservableList<Receipts> ReceiptList = FXCollections.observableArrayList();
    ObservableList<Stocks> StockOutList= FXCollections.observableArrayList();
    String month[]={"January","February","March","April","May","June","July","August","September","October","November","December"};
    String monthvalue="";
    private int Role=0;

    private void setRole(int role){
        this.Role=role;
    }
    private int getRole(){
        return this.Role;
    }


    private boolean requested = false;
    private int purchasedquan=0;
    private double purchasedprice=0.00;

    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private Image userpic = new Image("/resources/user.png");
    private Image confirm = new Image("/resources/confirm.png");
    private String userId="";
    private String receiptId="";
    final ToggleGroup group = new ToggleGroup();
    final ToggleGroup Stockgroup = new ToggleGroup();
    public Double totalprice = 0.0;



    private static Connection getConnection() throws SQLException {
        Connection conn;
        dbconn.getInstance();
        conn = dbconn.connect();
        return conn;
    }

    @FXML
    private void doLogOut() throws Exception{
        FXMLLoader loader= new FXMLLoader(getClass().getResource("Exit.fxml"));
        Message controller=new Message();
        controller.setMessage("Please Confirm Log-Out");
        loader.setController(controller);
        Parent root =loader.load();

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initOwner(tableProduct.getScene().getWindow());
        stage.setScene(new Scene(root));
        new FlipInX(root).play();
        stage.showAndWait();

        if(controller.isResponse()==true) {
            try {
                preparedStatement = getConnection().prepareStatement("Insert into tbl_activitylog values(null,?,?,?,?)");
                preparedStatement.setString(1, userId);
                preparedStatement.setString(2, "Log-out");
                preparedStatement.setString(3, LocalDateTime.now().format(formatter));
                preparedStatement.setString(4, LocalDateTime.now().format(time));
                preparedStatement.executeUpdate();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            Stage thisstage = (Stage) btnLogOut.getScene().getWindow();
            thisstage.close();
            FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("Log-InForm.fxml"));
            Parent rootpane= fxmlLoader.load();
            Scene scene = new Scene(rootpane);
            thisstage.setScene(scene);
            thisstage.show();
            new FlipInX(thisstage.getScene().getRoot()).play();



        }
    }
    private String checkmonth()throws SQLException{
        String str="";
        int monthindex=0;
        preparedStatement=getConnection().prepareStatement("Select max(Month) from tbl_date");
        resultSet=preparedStatement.executeQuery();
        if(resultSet.next()) {
            monthindex=Integer.parseInt(resultSet.getString("max(Month)"));
            if(monthindex==1){
                str="January";
                setMonth("01");
            }
            if (monthindex == 2) {
                str = "February";
                setMonth("02");
            }
            if (monthindex == 3) {
                str = "March";
                setMonth("03");
            }
            if (monthindex == 4) {
                str = "April";
                setMonth("04");
            }
            if (monthindex == 5) {
                str = "May";
                setMonth("05");
            }
            if (monthindex == 6) {
                str = "June";
                setMonth("06");
            }
            if (monthindex == 7) {
                str = "July";
                setMonth("07");
            }
            if (monthindex == 8) {
                str = "August";
                setMonth("08");
            }
            if (monthindex == 9) {
                str = "September";
                setMonth("09");
            }
            if (monthindex == 10) {
                str = "October";
                setMonth("10");
            }
            if (monthindex == 11) {
                str = "November";
                setMonth("11");
            }
            if (monthindex == 12) {
                str = "December";
                setMonth("12");
            }
        }
        return str;
    }

    @FXML
    void close() throws  Exception{
        FXMLLoader loader= new FXMLLoader(getClass().getResource("Exit.fxml"));
        Message controller=new Message();
        controller.setMessage("Please Confirm Exit");
        loader.setController(controller);
        Parent root =loader.load();

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initOwner(tableProduct.getScene().getWindow());
        stage.setScene(new Scene(root));
        new FlipInX(root).play();
        stage.showAndWait();


        if(controller.isResponse()==true) {
            try {
                preparedStatement = getConnection().prepareStatement("Insert into tbl_activitylog values(null,?,?,?,?)");
                preparedStatement.setString(1, userId);
                preparedStatement.setString(2, "Log-out");
                preparedStatement.setString(3, LocalDateTime.now().format(formatter));
                preparedStatement.setString(4, LocalDateTime.now().format(time));
                preparedStatement.executeUpdate();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            Stage thisstage= (Stage) btnClose.getScene().getWindow();
            thisstage.close();
            System.exit(1);
        }
    }

    @FXML
    void minimize(ActionEvent event) {
        Stage stage = (Stage) minimize.getScene().getWindow();
        stage.setIconified(true);

    }


    public DashboardController() {

    }
    private void Log(String Filter,String Date){
        try{

            String logs="";
            String sql="Select e.user_name,a.activity_description,a.LogDate,a.Log_Time from tbl_activitylog a Join tbl_employee e ON a.user_id=e.user_id";
            switch (Filter){
                case "All": sql+=" Where a.LogDate='"+Date+"' and role=1 GROUP BY a.activity_log";
                            break;
                case "Log-in": sql+=" Where a.LogDate='"+Date+"' AND a.activity_description Like '%Log-%' and role=1 GROUP BY a.activity_log";
                            break;
                case "Registration" : sql+=" WHERE a.LogDate='"+Date+"'  AND a.activity_description Like '%Registered%' and role=1 GROUP BY a.activity_log";
                            break;
                case "Sale Transactions" :  sql+=" WHERE a.LogDate='"+Date+"' AND a.activity_description  Like '%Transaction%' and role=1 GROUP BY a.activity_log";
                            break;
                case "Adding Inventory" : sql+=" WHERE a.LogDate='"+Date+"' AND a.activity_description Like '%Inventory%' and role=1 Group BY a.activity_log";
                            break;
                case "Stock-in" : sql+=" WHERE a.LogDate='"+Date+"' And a.activity_description Like '%Stock%' and role=1 GROUP BY a.activity_log";
                            break;
                case "Admin Confirmations" : sql+=" WHERE a.LogDate='"+Date+"' And (a.activity_description Like '%Confirmation%' OR a.activity_description Like '%Discount%' OR a.activity_description like '%Removed%' OR a.activity_description Like '%Registered%' ) and role=1 GROUP BY a.activity_log";
                            break;
                case "Discount" : sql+=" WHERE  a.LogDate='"+Date+"' And a.activity_description Like '%Discount%' and role=1 GROUP BY a.activity_log";
                            break;
                case "Void Operations" : sql+=" WHERE a.LogDate='"+Date+"' And a.activity_description Like '%Removed%' and role=1 GROUP BY a.activity_log";
                            break;
                case "Added Category" : sql+=" WHERE a.LogDate='"+Date+"' And a.activity_description Like '%Category%' and role=1 GROUP BY a.activity_log";
                    break;
                case "Added Supplier" : sql+=" WHERE a.LogDate='"+Date+"' And a.activity_description Like '%Supplier%' and role=1 GROUP BY a.activity_log";
                    break;
            }
            //JOptionPane.showMessageDialog(null,sql);
            preparedStatement=getConnection().prepareStatement(sql);
            resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                logs+=resultSet.getString("user_name")+" "+resultSet.getString("activity_description")+" on "+resultSet.getString("LogDate")+" around "+resultSet.getString("Log_Time")+"\n";
            }

                AdminLogs.setText(logs);

                String userlogs="";
                String query="Select e.user_name,a.activity_description,a.LogDate,a.Log_Time from tbl_activitylog a Join tbl_employee e ON a.user_id=e.user_id";
                switch (Filter){
                    case "All": query+=" Where a.LogDate='"+Date+"' and role=0 GROUP BY a.activity_log";
                        break;
                    case "Log-in": query+=" Where a.LogDate='"+Date+"' AND a.activity_description Like '%Log-%' and role=0 GROUP BY a.activity_log";
                        break;
                    case "Registration" : query+=" WHERE a.LogDate='"+Date+"'  AND a.activity_description Like '%Registered%' and role=0 GROUP BY a.activity_log";
                        break;
                    case "Sale Transactions" :  query+=" WHERE a.LogDate='"+Date+"' AND a.activity_description  Like '%Transaction%' and role=0 GROUP BY a.activity_log";
                        break;
                    case "Adding Inventory" : query+=" WHERE a.LogDate='"+Date+"' AND a.activity_description Like '%Inventory%' and role=0 Group BY a.activity_log";
                        break;
                    case "Stock-in" : query+=" WHERE a.LogDate='"+Date+"' And a.activity_description Like '%Stock%' and role=0 GROUP BY a.activity_log";
                        break;
                    case "Admin Confirmations" : query+=" WHERE a.LogDate='"+Date+"' And (a.activity_description Like '%Confirmation%' OR a.activity_description Like '%Discount%' OR a.activity_description like '%Removed%' OR a.activity_description Like '%Registered%' ) and role=0 GROUP BY a.activity_log";
                        break;
                    case "Discount" : query+=" WHERE  a.LogDate='"+Date+"' And a.activity_description Like '%Discount%' and role=0 GROUP BY a.activity_log";
                        break;
                    case "Void Operations" : query+=" WHERE a.LogDate='"+Date+"' And a.activity_description Like '%Removed%' and role=0 GROUP BY a.activity_log";
                        break;
                    case "Added Category" : query+=" WHERE a.LogDate='"+Date+"' And a.activity_description Like '%Category%' and role=0 GROUP BY a.activity_log";
                        break;
                    case "Added Supplier" : query+=" WHERE a.LogDate='"+Date+"' And a.activity_description Like '%Supplier%' and role=0 GROUP BY a.activity_log";
                        break;
                }
                //JOptionPane.showMessageDialog(null,sql);
                preparedStatement=getConnection().prepareStatement(query);
                resultSet=preparedStatement.executeQuery();
                while(resultSet.next()){
                    userlogs+=resultSet.getString("user_name")+" "+resultSet.getString("activity_description")+" on "+resultSet.getString("LogDate")+" around "+resultSet.getString("Log_Time")+"\n";
                }
                AreaLogs.setText(userlogs);



        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    @FXML
    private void clickme(){
        new Swing(img).play();
    }
    @FXML
    private void clickradio(){
        try {

            if (group.getSelectedToggle() == radioDaily) { // wa pa nahuman
                String month = getMonth();

                preparedStatement = getConnection().prepareStatement("SELECT concat_ws('-',Year,Month,Date) as Date from tbl_date where Year=? AND Month=? and Date=(select min(Date) from tbl_date where Month=?)");
                preparedStatement.setString(1,LocalDateTime.now().getYear()+"");
                preparedStatement.setString(2,month);
                preparedStatement.setString(3,month);
                resultSet=preparedStatement.executeQuery();
                if(resultSet.next()){
                    startingdate.setValue(resultSet.getString("Date"));
                }
                preparedStatement = getConnection().prepareStatement("SELECT concat_ws('-',Year,Month,Date) as Date from tbl_date where Year=? AND Month=? and Date=(select max(Date) from tbl_date where Month=?)");
                preparedStatement.setString(1,LocalDateTime.now().getYear()+"");
                preparedStatement.setString(2,month);
                preparedStatement.setString(3,month);
                resultSet=preparedStatement.executeQuery();
                if(resultSet.next()){
                    enddate.setValue(resultSet.getString("Date"));
                }
                Report();
                refreshChart();
                scrollpane.setVvalue(1.15);
                new BounceInUp(scrollpane).play();
            } else if (group.getSelectedToggle() == radioMonthly ) {
                String month = getMonth();
                //JOptionPane.showMessageDialog(null,"Monthly");
                preparedStatement = getConnection().prepareStatement("SELECT concat_ws('-',Year,Month,Date) as Date from tbl_date where Year=? AND Month=? and Date=(select min(Date) from tbl_date where Month=?)");
                preparedStatement.setString(1,LocalDateTime.now().getYear()+"");
                preparedStatement.setString(2,month);
                preparedStatement.setString(3,month);
                resultSet=preparedStatement.executeQuery();
                if(resultSet.next()){
                    startingdate.setValue(resultSet.getString("Date"));
                }
                preparedStatement = getConnection().prepareStatement("SELECT concat_ws('-',Year,Month,Date) as Date from tbl_date where Year=? AND Month=? and Date=(select max(Date) from tbl_date where Month=?)");
                preparedStatement.setString(1,LocalDateTime.now().getYear()+"");
                preparedStatement.setString(2,month);
                preparedStatement.setString(3,month);
                resultSet=preparedStatement.executeQuery();
                if(resultSet.next()){
                    enddate.setValue(resultSet.getString("Date"));
                }
                Report();
                refreshChart();
                scrollpane.setVvalue(0);
                new BounceInDown(scrollpane).play();

            }else if(group.getSelectedToggle()==radioYear){
                    String Year = LocalDateTime.now().getYear()+"";
                preparedStatement = getConnection().prepareStatement("SELECT concat_ws('-',Year,Month,Date) as Date from tbl_date where Year=? and Date=(select min(Date) from tbl_date where Month=(SELECT min(Month) from tbl_date where Year=?)) and Month=(SELECT min(Month) from tbl_date WHERE Year=?) ");
                preparedStatement.setString(1,LocalDateTime.now().getYear()+"");
                preparedStatement.setString(2,LocalDateTime.now().getYear()+"");
                preparedStatement.setString(3,LocalDateTime.now().getYear()+"");
                resultSet=preparedStatement.executeQuery();
                if(resultSet.next()){
                    startingdate.setValue(resultSet.getString("Date"));
                }
                preparedStatement = getConnection().prepareStatement("SELECT concat_ws('-',Year,Month,Date) as Date from tbl_date where Year=? and Date=(select max(Date) from tbl_date where Month=(SELECT max(Month) from tbl_date where Year=?)) and Month=(SELECT max(Month) from tbl_date WHERE Year=?) ");
                preparedStatement.setString(1,LocalDateTime.now().getYear()+"");
                preparedStatement.setString(2,LocalDateTime.now().getYear()+"");
                preparedStatement.setString(3,LocalDateTime.now().getYear()+"");
                resultSet=preparedStatement.executeQuery();
                if(resultSet.next()){
                    enddate.setValue(resultSet.getString("Date"));
                }

                Report();
                refreshChart();
                scrollpane.setVvalue(2.28);
                new BounceInLeft(scrollpane).play();
            }
            if(Stockgroup.getSelectedToggle()==radio_stockin){
                StockInTable.setVisible(true);
                StockOutTable.setVisible(false);
                cboReasons.setDisable(true);
                if(StockInName.getText().length()!=0) {
                    btnStockIn.setDisable(false);
                    btnStockOut.setDisable(true);
                }
                //JOptionPane.showMessageDialog(nu);
            }if(Stockgroup.getSelectedToggle()==radio_stockOut){
                StockInTable.setVisible(false);
                StockOutTable.setVisible(true);
                cboReasons.setDisable(false);
                refreshStockOutList();
                if(StockInName.getText().length()!=0) {
                    btnStockIn.setDisable(true);
                    btnStockOut.setDisable(false);
                }
            }
        }catch (Exception a){
            a.printStackTrace();
        }

    }
    private void setMonth(String month){
        this.monthvalue=month;
    }
    private String getMonth(){
        return monthvalue;
    }

    private void refreshChart(){
            //balik

        try{
            XYChart.Series<String,Number> series=new XYChart.Series<String,Number>();
            XYChart.Series<String,Number> Monthly=new XYChart.Series<String,Number>();
            XYChart.Series<String,Number> Yearly=new XYChart.Series<String,Number>();

                String month=getMonth();
                LineChart.getData().clear();
                series.getData().clear();

                //LineChart.setTitle("Daily Sales Performance");
                preparedStatement = getConnection().prepareStatement("SELECT r.transaction_date,concat_ws(\"-\",d.Year,d.Month,d.Date) as Date ,sum(r.total_price)-sum(r.discount_value) as Sales from tbl_receipt r JOIN tbl_date d ON r.transaction_date=d.Date_id where d.Month = ? GROUP by d.Date");

                preparedStatement.setString(1,getMonth());
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Double Ycomponent = Double.parseDouble(resultSet.getString("Sales"));
                    series.getData().add(new XYChart.Data<String, Number>(resultSet.getString("Date"), Ycomponent));
                }
                LineChart.getData().add(series);


                MonthlyChart.getData().clear();
                //LineChart.setTitle("Monthly Sales Performance");
                preparedStatement = getConnection().prepareStatement("SELECT r.transaction_date,concat_ws(\"-\",d.Year,d.Month) as Date ,sum(r.total_price)-sum(r.discount_value) as Sales from tbl_receipt r JOIN tbl_date d ON r.transaction_date=d.Date_id where d.Year = ? GROUP by concat_ws(\"-\",d.Year,d.Month)");
                preparedStatement.setString(1, cboYear.getSelectionModel().getSelectedItem());
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Double Ycomponent = Double.parseDouble(resultSet.getString("Sales"));
                    Monthly.getData().add(new XYChart.Data<String, Number>(resultSet.getString("Date"), Ycomponent));
                }
                MonthlyChart.getData().add(Monthly);

                YearlyChart.getData().clear();
                preparedStatement = getConnection().prepareStatement("SELECT r.transaction_date,Year as Date ,sum(r.total_price)-sum(r.discount_value) as Sales from tbl_receipt r JOIN tbl_date d ON r.transaction_date=d.Date_id GROUP by Year");
                //preparedStatement.setString(1, LocalDateTime.now().getYear()+"");
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                Double Ycomponent = Double.parseDouble(resultSet.getString("Sales"));
                Yearly.getData().add(new XYChart.Data<String, Number>(resultSet.getString("Date"), Ycomponent));
            }
            YearlyChart.getData().add(Yearly);



            for(final XYChart.Data<String,Number> data: series.getData()){
                data.getNode().setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        Tooltip.install(data.getNode(),new Tooltip("Date : "+data.getXValue()+"\nTotal Sales : PHP "+data.getYValue()));
                    }

                });
            }
            for(final XYChart.Data<String,Number> data: series.getData()){
                data.getNode().setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        Tooltip.install(data.getNode(),new Tooltip("Date : "+data.getXValue()+"\nTotal Sales : PHP "+data.getYValue()));
                        startingdate.setValue(data.getXValue());
                        enddate.setValue(data.getXValue());
                        lblTotalSales.setText("Daily Sales : PHP "+data.getYValue());
                        lblTotalSales.setVisible(true);
                    }

                });
            }
            for(final XYChart.Data<String,Number> data: Monthly.getData()){
                data.getNode().setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        Tooltip tooltip= new Tooltip();
                        tooltip.install(data.getNode(),new Tooltip("Month : "+data.getXValue()+"\nTotal Sales : PHP "+data.getYValue()));
                    }
                });
            }
            for(final XYChart.Data<String,Number> data: Monthly.getData()){
                data.getNode().setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        lblTotalSales.setText("Monthly Sales : PHP "+data.getYValue());
                        lblTotalSales.setVisible(true);
                    }
                });
            }
            for(final XYChart.Data<String,Number> data: Yearly.getData()){
                data.getNode().setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        Tooltip tooltip= new Tooltip();
                        tooltip.install(data.getNode(),new Tooltip("Year : "+data.getYValue()+"\nTotal Sales : PHP "+data.getYValue()));
                    }
                });
            }
            for(final XYChart.Data<String,Number> data: Yearly.getData()){
                data.getNode().setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        lblTotalSales.setText("Yearly Sales : PHP "+data.getYValue());
                        lblTotalSales.setVisible(true);
                    }
                });
            }


        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    @FXML
    private void doLoadChart(){
        refreshChart();
    }
    public String runningbalance(){
        String dbl="0.00";
        try{
            preparedStatement=getConnection().prepareStatement("SELECT sum(total_price)-sum(discount_value) as Running_Balance FROM `tbl_receipt` where transaction_date=(Select max(Date_id) from tbl_date)");
            resultSet=preparedStatement.executeQuery();

            if (resultSet.next() && resultSet.getString("Running_Balance")!=null){
                    dbl=resultSet.getString("Running_Balance");
                }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return dbl;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        lblTotalSales.setVisible(false);
        //initialize chart
        LineChart.getXAxis().setAutoRanging(true);
        LineChart.getYAxis().setAutoRanging(true);
        scrollpane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollpane.addEventFilter(ScrollEvent.SCROLL,new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                if (event.getDeltaY() != 0) {
                    event.consume();
                }
            }
        });
        radioDaily.setToggleGroup(group);
        radioMonthly.setToggleGroup(group);
        radioYear.setToggleGroup(group);

        radio_stockin.setToggleGroup(Stockgroup);
        radio_stockOut.setToggleGroup(Stockgroup);
        Stockgroup.selectToggle(radio_stockin);
        group.selectToggle(radioDaily);
        refreshChart();
        int size=10;
        while(size<=30){
            cbo_size.getItems().add(size);
            size+=2;
        }


        initClock();

        LabelId.setText(getID());
        StockinID.setText(getStockInID());
        InventoryQuantity.setText("0");
        setComboBOx(InventorySupp, "Select * from tbl_supplier", "company_name");
        setComboBOx(InventoryCateg, "Select * from tbl_category", "cat_description");
        setComboBOx(StockinCat, "Select * from tbl_category", "cat_description");
        setComboBOx(cbologdate,"Select concat_ws(\"-\",Year,Month,Date) as Date from tbl_date","Date");
        setComboBOx(startingdate,"Select concat_ws(\"-\",Year,Month,Date) as Date from tbl_date","Date");
        setComboBOx(enddate,"Select concat_ws(\"-\",Year,Month,Date) as Date from tbl_date","Date");
        setComboBOx(cboYear,"Select Year from tbl_date group by Year","Year");
        for (int i = 0; i < month.length; i++) {
            cboMonthly.getItems().add(month[i]);
        }
        try{
            cboMonthly.setValue(checkmonth());
        }catch (Exception e){
            e.printStackTrace();
        }

        cboReasons.getItems().add("Damaged");
        cboReasons.getItems().add("Lost");
        cboReasons.getItems().add("Human Error");

        cboYear.setValue(LocalDateTime.now().getYear()+"");
        startingdate.setValue(LocalDateTime.now().format(formatter));
        enddate.setValue(LocalDateTime.now().format(formatter));
        cboActivity.getItems().add("All");
        cboActivity.getItems().add("Log-in");
        cboActivity.getItems().add("Registration");
        cboActivity.getItems().add("Sale Transactions");
        cboActivity.getItems().add("Adding Inventory");
        cboActivity.getItems().add("Stock-in");
        cboActivity.getItems().add("Admin Confirmations");
        cboActivity.getItems().add("Discount");
        cboActivity.getItems().add("Void Operations");
        cboActivity.getItems().add("Added Category");
        cboActivity.getItems().add("Added Supplier");
        cboActivity.setValue("All");
        cbologdate.setValue(LocalDateTime.now().format(formatter));
        cboActivity.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(t1!=null){
                    Log(cboActivity.getValue(),cbologdate.getValue());
                }
            }
        });
        cbologdate.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(t1!=null){
                    Log(cboActivity.getValue(),cbologdate.getValue());
                }
            }
        });

        startingdate.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(t1!=null){
                    Report();
                    if(enddate.getValue().contentEquals(startingdate.getValue())){
                        refreshChart();
                    }
                }
            }
        });
        enddate.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(t1!=null){
                    Report();
                    if(enddate.getValue().contentEquals(startingdate.getValue())){
                        refreshChart();
                    }
                }
            }
        });
        cbo_size.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                if(t1!=null){
                    AreaLogs.setFont(Font.font("Arial", FontWeight.BOLD,t1));
                    AdminLogs.setFont(Font.font("Arial", FontWeight.BOLD,t1));
                }
            }
        });
        cboMonthly.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(t1!=null){
                    String str="";
                    int monthlyindex=Integer.parseInt(cboMonthly.getSelectionModel().getSelectedIndex()+"");
                    if(monthlyindex<9){
                        monthlyindex++;
                        str="0"+monthlyindex;
                    }else{
                        monthlyindex++;
                        str=monthlyindex+"";
                    }
                    setMonth(str);
                    refreshChart();
                    clickradio();
                }
            }
        });
        cboYear.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(t1!=null){
                    refreshChart();
                }
            }
        });
        InventoryReports.setOnAction(e->{
            try{
                printReport("InventoryReport");
            }catch (Exception ee){
                ee.printStackTrace();
            }
        });
        StockInReports.setOnAction(a->{
            try{
                if(Stockgroup.getSelectedToggle()==radio_stockin){
                    printReport("StockinReport");
                }
                if(Stockgroup.getSelectedToggle()==radio_stockOut){
                    printReport("StockOutReport");
                }
                //
            }catch (Exception ee){
                ee.printStackTrace();
            }
        });

        Inventory_Status.getItems().add("INACTIVE");
        Inventory_Status.getItems().add("ACTIVE");
        Inventory_Status.setValue("ACTIVE");


        //tbl_product
        int width = 80;
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
        int cartwidth=120;
        JFXTreeTableColumn<products, String> CartItemId = new JFXTreeTableColumn<>("Id");
        CartItemId.setPrefWidth(cartwidth);
        CartItemId.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<products, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<products, String> productsStringCellDataFeatures) {
                return productsStringCellDataFeatures.getValue().getValue().product_id;
            }
        });
        JFXTreeTableColumn<products, String> CartName = new JFXTreeTableColumn<>("Name");
        CartName.setPrefWidth(cartwidth);
        CartName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<products, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<products, String> productsStringCellDataFeatures) {
                return productsStringCellDataFeatures.getValue().getValue().product_name;
            }
        });
        JFXTreeTableColumn<products, String> CartQuantity = new JFXTreeTableColumn<>("Quantity");
        CartQuantity.setPrefWidth(cartwidth);
        CartQuantity.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<products, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<products, String> productsStringCellDataFeatures) {
                return productsStringCellDataFeatures.getValue().getValue().product_quan;
            }
        });
        JFXTreeTableColumn<products, String> CartPrice = new JFXTreeTableColumn<>("Price");
        CartPrice.setPrefWidth(cartwidth);
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
        int widthInventory=80;
        tabSales.setOnSelectionChanged(event -> {
            if(tabSales.isSelected()){
                refreshProductMenu();
            }
        });//
        tabReport.setOnSelectionChanged(event -> {
            if(tabReport.isSelected()){
                Report();
                refreshChart();
                lbl_balance.setText("Running Balance : PHP "+runningbalance());
            }
        });
        tabInventory.setOnSelectionChanged(event -> {
            if(tabInventory.isSelected()){
                refreshInventoryTable(InventoryList);
                CheckInventory();

            }
        });
        tabStock.setOnSelectionChanged(event -> {
            if(tabStock.isSelected()){
                refreshStockList();
                refreshStockOutList();
                refreshInventoryTable(StockinList);

            }
        });
        tabLog.setOnSelectionChanged(event -> {
            if(tabLog.isSelected()){
                Log(cboActivity.getValue(),cbologdate.getValue());

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
        JFXTreeTableColumn<Inventory, String> InventoryCost = new JFXTreeTableColumn<>("Cost");
        InventoryCost.setPrefWidth(widthInventory);
        InventoryCost.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Inventory, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Inventory, String> productsStringCellDataFeatures) {
                return productsStringCellDataFeatures.getValue().getValue().product_cost;
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

        tbl_inventory.getColumns().setAll(InventoryId, InventoryName, InventoryQuantity, InventoryPrice,InventoryCost, InventoryCategory, InventoryStatus,InventorySupplier);
        tbl_inventory.setRoot(inventoryList);
        tbl_inventory.setShowRoot(false);

        /*tabInventory.setOnSelectionChanged(event -> {
            if (tabInventory.isSelected() && tbl_inventory.getCurrentItemsCount()==0 ) {

            }
            });*/
        CurrentID=Integer.parseInt(setID());
        InventoryID.setText(CurrentID+"");
        StockinAmount.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {

                if(keyEvent.getCharacter().contains("") && StockinAmount.getLength()>=1) {
                    //JOptionPane.showMessageDialog(null, "KeyTyped 1");
                    StockIntotalprice.setText("");
                    if(keyEvent.getCharacter().contains("1") || keyEvent.getCharacter().contains("2") || keyEvent.getCharacter().contains("3") || keyEvent.getCharacter().contains("4") || keyEvent.getCharacter().contains("5") || keyEvent.getCharacter().contains("6") || keyEvent.getCharacter().contains("7") || keyEvent.getCharacter().contains("8") || keyEvent.getCharacter().contains("9") || keyEvent.getCharacter().contains("0")){
                        //JOptionPane.showMessageDialog(null, "KeyTyped 2");
                        double itemprice=Double.parseDouble(StockinCost.getText());
                        double amount=Double.parseDouble(StockinAmount.getText());
                        double totalprice=0.00;
                        totalprice=amount*itemprice;
                        StockIntotalprice.setText(totalprice+"");
                    }
                }else{
                    //JOptionPane.showMessageDialog(null, "KeyTyped 3");
                    keyEvent.consume();
                    //Wa pa sya pa nahuman . . . . Error Trapping in case Backspace or space ang ipindut dapat ma consume;
                }
            }
        });
        SalesSearch.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                try{
                    productList.clear();
                    String search=SalesSearch.getText();

                    String sql = "Select * from tbl_products where prod_status=1 AND prod_name LIKE '%"+search+"%'";
                    //JOptionPane.showMessageDialog(null,sql);
                    preparedStatement = getConnection().prepareStatement(sql);
                    resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        //JOptionPane.showMessageDialog(null,resultSet.getString("prod_name"));
                        productList.add(new products(resultSet.getString("prod_id"),resultSet.getString("prod_name"), resultSet.getString("prod_quantity"), resultSet.getString("prod_price")));
                    }

                }catch (Exception ex){
                    ex.printStackTrace();
                }

            }
        });
        InventorySearch.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                try{
                    int i =0;
                    String status;
                    String search=InventorySearch.getText();
                    InventoryList.clear();
                    String sql = "select p.prod_id,p.prod_name,p.prod_quantity,p.prod_price,c.cat_description,s.company_name,p.prod_status,p.prod_cost FROM tbl_products p JOIN tbl_supplier s ON p.supplier_id = s.supplier_id JOIN tbl_category c ON p.category_id=c.category_id WHERE p.prod_name LIKE '%"+search+"%' Group by p.prod_id";
                    preparedStatement = getConnection().prepareStatement(sql);
                    resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        i++;
                        int temp=Integer.parseInt(resultSet.getString("prod_status"));
                        if(temp==0){
                            status="INACTIVE";
                        }else{
                            status="ACTIVE";
                        }
                        InventoryList.add(new Inventory(resultSet.getString("prod_id"), resultSet.getString("prod_name"), resultSet.getString("prod_quantity"),resultSet.getString("prod_price"), resultSet.getString("cat_description"), resultSet.getString("company_name"),status,resultSet.getString("prod_cost")));

                    }
                    InventoryCount.setText(i+"");

                }catch (Exception ex){
                    ex.printStackTrace();
                }

            }
        });
        StockInSearch.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                try{
                        String status;
                        String search = StockInSearch.getText();
                        StockinList.clear();
                        String sql = "select p.prod_id,p.prod_name,p.prod_quantity,p.prod_price,c.cat_description,s.company_name,p.prod_status,p.prod_cost FROM tbl_products p JOIN tbl_supplier s ON p.supplier_id = s.supplier_id JOIN tbl_category c ON p.category_id=c.category_id WHERE p.prod_name LIKE '%" + search + "%' Group by p.prod_id";
                        preparedStatement = getConnection().prepareStatement(sql);
                        resultSet = preparedStatement.executeQuery();
                        while (resultSet.next()) {

                            int temp = Integer.parseInt(resultSet.getString("prod_status"));
                            if (temp == 0) {
                                status = "INACTIVE";
                            } else {
                                status = "ACTIVE";
                            }
                            StockinList.add(new Inventory(resultSet.getString("prod_id"), resultSet.getString("prod_name"), resultSet.getString("prod_quantity"), resultSet.getString("prod_price"), resultSet.getString("cat_description"), resultSet.getString("company_name"), status, resultSet.getString("prod_cost")));

                        }



                }catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });
        StockListSearch.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                try{
                    if(Stockgroup.getSelectedToggle()==radio_stockin) {

                        StocksTableList.clear();
                        String search = StockListSearch.getText();
                        String sql = "Select s.Stockin_id,p.prod_name,s.Stock_BeforeUpdate,s.updatedquantity,s.LastEntry,d.Date,s.Entry_time,s.total_cost from tbl_stockin s JOIN tbl_products p ON s.prod_id=p.prod_id JOIN tbl_date d ON s.Entry_date=d.Date_id WHERE prod_name LIKE '%" + search + "%'Group by Stockin_id";
                        preparedStatement = getConnection().prepareStatement(sql);
                        resultSet = preparedStatement.executeQuery();
                        while (resultSet.next()) {
                            StocksTableList.add(new Stocks(resultSet.getString("Stockin_id"), resultSet.getString("prod_name"), resultSet.getString("Stock_BeforeUpdate"), resultSet.getString("LastEntry"), resultSet.getString("updatedquantity"), resultSet.getString("Date"), resultSet.getString("Entry_time"), resultSet.getString("total_cost")));
                        }
                    }else{

                        String search = StockListSearch.getText();
                        StockOutList.clear();
                        String sql="Select s.StockOut_id,p.prod_name,s.StockBefore_Update,s.updatedquantity,s.lastOut,concat_ws(\"-\",d.Year,d.Month,d.Date) as Date,s.departure_time,s.Status,s.TotalLoss from tbl_stockout s JOIN tbl_products p ON s.prod_id=p.prod_id JOIN tbl_date d ON s.Departure_date=d.Date_id WHERE p.prod_name LIKE '%"+search+"%' Group by s.StockOut_Id";
                        preparedStatement = getConnection().prepareStatement(sql);
                        resultSet = preparedStatement.executeQuery();
                        while(resultSet.next()){
                            StockOutList.add(new Stocks(resultSet.getString("StockOut_id"),resultSet.getString("prod_name"),resultSet.getString("StockBefore_Update"),resultSet.getString("lastOut"),resultSet.getString("updatedquantity"),resultSet.getString("Date"),resultSet.getString("departure_time"),resultSet.getString("TotalLoss"),resultSet.getString("status")));
                        }

                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

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
                            double Change=payment-Sales;
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
        StockInName.setPrefWidth(stockincolumnwidth);
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
        JFXTreeTableColumn<Inventory, String> StockInCost= new JFXTreeTableColumn<>("Cost");
        StockInCost.setPrefWidth(stockincolumnwidth);
        StockInCost.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Inventory, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Inventory, String> productsStringCellDataFeatures) {
                return productsStringCellDataFeatures.getValue().getValue().product_cost;
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

        StockInventoryTable.getColumns().setAll(StockinId, StockInName, StockInQuantity, StockInPrice,StockInCost, StockInCat, StockInStatus,StockInSupp);
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
        JFXTreeTableColumn<Stocks, String> formerStocks= new JFXTreeTableColumn<>("Former Quantity");
        formerStocks.setPrefWidth(stocktablewidth);
        formerStocks.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Stocks, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Stocks, String> productsStringCellDataFeatures) {
                return productsStringCellDataFeatures.getValue().getValue().before_update_stocks;
            }
        });

        JFXTreeTableColumn<Stocks, String> Stocks= new JFXTreeTableColumn<>("Updated Quantity");
        Stocks.setPrefWidth(stocktablewidth);
        Stocks.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Stocks, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Stocks, String> productsStringCellDataFeatures) {
                return productsStringCellDataFeatures.getValue().getValue().current_stocks;
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
        JFXTreeTableColumn<Stocks, String> StockInTotalCost= new JFXTreeTableColumn<>("Total Cost");
        StockInTotalCost.setPrefWidth(stocktablewidth);
        StockInTotalCost.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Stocks, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Stocks, String> productsStringCellDataFeatures) {
                return productsStringCellDataFeatures.getValue().getValue().TotalCost;
            }
        });
        refreshStockList();
        final TreeItem<Stocks> StocksTablesList = new RecursiveTreeItem<Stocks>(StocksTableList, RecursiveTreeObject::getChildren);

        StockInTable.getColumns().setAll(StockId, StockName,formerStocks,Last_entry,Stocks,StockInDate,StockInTime,StockInTotalCost);
        StockInTable.setRoot(StocksTablesList );
        StockInTable.setShowRoot(false);

        //stockout table
        JFXTreeTableColumn<Stocks, String> StockOutID= new JFXTreeTableColumn<>("ID");
        StockOutID.setPrefWidth(stocktablewidth);
        StockOutID.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Stocks, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Stocks, String> productsStringCellDataFeatures) {
                return productsStringCellDataFeatures.getValue().getValue().product_id;
            }
        });
        JFXTreeTableColumn<Stocks, String> StockOutName= new JFXTreeTableColumn<>("Name");
        StockOutName.setPrefWidth(stocktablewidth);
        StockOutName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Stocks, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Stocks, String> productsStringCellDataFeatures) {
                return productsStringCellDataFeatures.getValue().getValue().product_name;
            }
        });
        JFXTreeTableColumn<Stocks, String> unupdated_stocks= new JFXTreeTableColumn<>("Before Update");
        unupdated_stocks.setPrefWidth(stocktablewidth);
        unupdated_stocks.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Stocks, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Stocks, String> productsStringCellDataFeatures) {
                return productsStringCellDataFeatures.getValue().getValue().before_update_stocks;
            }
        });
        JFXTreeTableColumn<Stocks, String> LastOut= new JFXTreeTableColumn<>("Last Out");
        LastOut.setPrefWidth(stocktablewidth);
        LastOut.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Stocks, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Stocks, String> productsStringCellDataFeatures) {
                return productsStringCellDataFeatures.getValue().getValue().Last_Entry;
            }
        });
        JFXTreeTableColumn<Stocks, String> CurrentStocks= new JFXTreeTableColumn<>("Current Stocks");
        CurrentStocks.setPrefWidth(stocktablewidth);
        CurrentStocks.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Stocks, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Stocks, String> productsStringCellDataFeatures) {
                return productsStringCellDataFeatures.getValue().getValue().current_stocks;
            }
        });
        JFXTreeTableColumn<Stocks, String> StockOutDate = new JFXTreeTableColumn<>("Stock-Out Date");
        StockOutDate.setPrefWidth(stocktablewidth);
        StockOutDate.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Stocks, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Stocks, String> productsStringCellDataFeatures) {
                return productsStringCellDataFeatures.getValue().getValue().Date;
            }
        });
        JFXTreeTableColumn<Stocks, String> StockOutTime = new JFXTreeTableColumn<>("Stock-Out Time");
        StockOutTime.setPrefWidth(stocktablewidth);
        StockOutTime.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Stocks, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Stocks, String> productsStringCellDataFeatures) {
                return productsStringCellDataFeatures.getValue().getValue().Time;
            }
        });
        JFXTreeTableColumn<Stocks, String> StockOutStatus = new JFXTreeTableColumn<>("Status");
        StockOutStatus.setPrefWidth(stocktablewidth);
        StockOutStatus.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Stocks, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Stocks, String> productsStringCellDataFeatures) {
                return productsStringCellDataFeatures.getValue().getValue().status;
            }
        });
        JFXTreeTableColumn<Stocks, String> StockOutCost= new JFXTreeTableColumn<>("Total Cost");
        StockOutCost.setPrefWidth(stocktablewidth);
        StockOutCost.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Stocks, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Stocks, String> productsStringCellDataFeatures) {
                return productsStringCellDataFeatures.getValue().getValue().TotalCost;
            }
        });
        refreshStockOutList();
        final TreeItem<Stocks> StocksOutList = new RecursiveTreeItem<Stocks>(StockOutList, RecursiveTreeObject::getChildren);

        StockOutTable.getColumns().setAll(StockOutID,StockOutName,unupdated_stocks,LastOut,CurrentStocks,StockOutDate,StockOutTime,StockOutCost,StockOutStatus);
        StockOutTable.setRoot(StocksOutList);
        StockOutTable.setShowRoot(false);
        //
        //ReceiptTab

        JFXTreeTableColumn<Receipts, String> ReceiptId= new JFXTreeTableColumn<>("Receipt Id");
        ReceiptId.setPrefWidth(width);
        ReceiptId.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Receipts, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Receipts, String> receiptsStringCellDataFeatures) {
                return receiptsStringCellDataFeatures.getValue().getValue().ReceiptId;
            }
        });
        JFXTreeTableColumn<Receipts, String> CartId= new JFXTreeTableColumn<>("Cart Id");
        CartId.setPrefWidth(width);
        CartId.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Receipts, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Receipts, String> receiptsStringCellDataFeatures) {
                return receiptsStringCellDataFeatures.getValue().getValue().CartID;
            }
        });
        JFXTreeTableColumn<Receipts, String> CustomerName= new JFXTreeTableColumn<>("Customer Name");
        CustomerName.setPrefWidth(width);
        CustomerName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Receipts, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Receipts, String> receiptsStringCellDataFeatures) {
                return receiptsStringCellDataFeatures.getValue().getValue().CustomerName;
            }
        });
        JFXTreeTableColumn<Receipts, String> EmployeeName= new JFXTreeTableColumn<>("Employee Name");
        EmployeeName.setPrefWidth(width);
        EmployeeName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Receipts, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Receipts, String> receiptsStringCellDataFeatures) {
                return receiptsStringCellDataFeatures.getValue().getValue().EmployeeName;
            }
        });
        JFXTreeTableColumn<Receipts, String> TotalPricee= new JFXTreeTableColumn<>("Total Price");
        TotalPricee.setPrefWidth(width);
        TotalPricee.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Receipts, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Receipts, String> receiptsStringCellDataFeatures) {
                return receiptsStringCellDataFeatures.getValue().getValue().TotalPrice;
            }
        });
        JFXTreeTableColumn<Receipts, String> DiscountedValue= new JFXTreeTableColumn<>("Discounted Value");
        DiscountedValue.setPrefWidth(width);
        DiscountedValue.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Receipts, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Receipts, String> receiptsStringCellDataFeatures) {
                return receiptsStringCellDataFeatures.getValue().getValue().DiscountedValue;
            }
        });
        JFXTreeTableColumn<Receipts, String> DiscountedPrice= new JFXTreeTableColumn<>("Discounted Price");
        DiscountedPrice.setPrefWidth(width);
        DiscountedPrice.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Receipts, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Receipts, String> receiptsStringCellDataFeatures) {
                return receiptsStringCellDataFeatures.getValue().getValue().DiscountedPrice;
            }
        });
        JFXTreeTableColumn<Receipts, String> TotalAmount= new JFXTreeTableColumn<>("Total Payment");
        TotalAmount.setPrefWidth(width);
        TotalAmount.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Receipts, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Receipts, String> receiptsStringCellDataFeatures) {
                return receiptsStringCellDataFeatures.getValue().getValue().TotalPayment;
            }
        });
        JFXTreeTableColumn<Receipts, String> Change= new JFXTreeTableColumn<>("Change");
        Change.setPrefWidth(width);
        Change.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Receipts, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Receipts, String> receiptsStringCellDataFeatures) {
                return receiptsStringCellDataFeatures.getValue().getValue().TotalChange;
            }
        });
        JFXTreeTableColumn<Receipts, String> TransactionDate= new JFXTreeTableColumn<>("Transaction Date");
        TransactionDate.setPrefWidth(width);
        TransactionDate.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Receipts, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Receipts, String> receiptsStringCellDataFeatures) {
                return receiptsStringCellDataFeatures.getValue().getValue().TransactionDate;
            }
        });
        Report();
        final TreeItem<Receipts> receiptlist = new RecursiveTreeItem<Receipts>(ReceiptList, RecursiveTreeObject::getChildren);
        ReportTable.getColumns().setAll(ReceiptId,CartId,CustomerName,EmployeeName,TotalPricee,DiscountedValue,DiscountedPrice,TotalAmount,Change,TransactionDate);
        ReportTable.setRoot(receiptlist);
        ReportTable.setShowRoot(false);

        //lbl_balance.setText("Running Balance : PHP "+runningbalance());
    }
    public void CheckInventory(){
        try{

            preparedStatement=getConnection().prepareStatement("Select *from tbl_products where prod_quantity<=20");
            resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                    String product=resultSet.getString("prod_name");
                    int quan=Integer.parseInt(resultSet.getString("prod_quantity"));
                    if(quan==0){
                        Notifications notificationBuilder=Notifications.create().graphic(null).hideAfter(Duration.seconds(10)).position(Pos.BOTTOM_LEFT)
                                .title("Warning").text(product+" stocks are now empty");
                        notificationBuilder.showError();
                    }else{
                        Notifications notificationBuilder=Notifications.create().graphic(null).hideAfter(Duration.seconds(10)).position(Pos.BOTTOM_LEFT)
                                .title("Warning").text(product+" stocks are getting low");
                        notificationBuilder.showWarning();
                    }

            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void setComboBOx(JFXComboBox comboBOx,String query,String columnname){
        try {

            comboBOx.getItems().clear();
            preparedStatement = getConnection().prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
               comboBOx.getItems().add(resultSet.getString(columnname));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
        private String getStockInID(){
            int id = 0;
            try {

                preparedStatement = getConnection().prepareStatement("Select max(Stockin_id) from tbl_stockin");
                resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                    if (resultSet.getString("max(Stockin_id)") == null) {
                        id++;
                    } else {
                        id = Integer.parseInt(resultSet.getString("max(Stockin_id)"));
                        id++;
                    } }
                preparedStatement.close();
                resultSet.close();
            }catch(Exception ex){
                ex.printStackTrace();
            }
            return id+"";

        }
        private String getID(){
            int id = 0;
        try {

                preparedStatement = getConnection().prepareStatement("Select max(Cart_id) from tbl_cart");
                resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                if (resultSet.getString("max(Cart_id)") == null) {
                    id++;
                } else {
                    id = Integer.parseInt(resultSet.getString("max(Cart_id)"));
                    id++;
                } }
                preparedStatement.close();
                resultSet.close();
            }catch(Exception ex){
                ex.printStackTrace();
            }


            return id+"";
        }
        public void checkUser(String Name, int Role,String id) {
            lblName.setText(String.format("Welcome %s", Name));
            if (Role == 1) {
                tabInventory.setDisable(false);
                tabStock.setDisable(false);
                tabReport.setDisable(false);
                tabLog.setDisable(false);
                userId=id;
                tabpane.getSelectionModel().select(tabReport);
                setRole(Role);

            } else {
                img.setImage(userpic);
                userId=id;
                setRole(Role);
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
                    controller.setProd_id(CartItemId);
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.initStyle(StageStyle.TRANSPARENT);
                    stage.initOwner(tableProduct.getScene().getWindow());
                    stage.setScene(new Scene(root));
                    new FlipInX(root).play();
                    stage.showAndWait();


                    //Quantity=Integer.parseInt(controller.getQuan());
                    if(!controller.getQuan().contentEquals("")){

                        SelectToCart(CartItemId,CartName, controller.getQuan(), CartPrice, CartList);
                        final TreeItem<products> cart = new RecursiveTreeItem<products>(CartList, RecursiveTreeObject::getChildren);
                        tableCart.setRoot(cart);
                        tableCart.setShowRoot(false);
                        totalitems++;
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
                    String cost=tbl_inventory.getSelectionModel().getSelectedItems().get(0).getValue().product_cost.getValue();

                    InventorySupp.setValue(supplier);
                    InventoryCateg.setValue(category);
                    Inventory_Status.setValue(status);
                    InventoryID.setText(id);
                    InventoryName.setText(name);
                    InventoryQuantity.setText(quan);
                    InventoryQuantity.setDisable(true);
                    InventoryQuantity.setDisableAnimation(true);
                    InventoryPrice.setText(itemprice);
                    InventoryCost.setText(cost);

                    update_inventory.setDisable(false);
                    add_inventory.setDisable(true);
                    add_inventory.setVisible(false);
                    new_entry.setVisible(true);
                    new_entry.setDisable(false);
                }
                //table Stocks
                if(e.getSource()==StockInventoryTable){
                    String id=StockInventoryTable.getSelectionModel().getSelectedItems().get(0).getValue().product_id.getValue();
                    String name=StockInventoryTable.getSelectionModel().getSelectedItems().get(0).getValue().product_name.getValue();
                    String Category=StockInventoryTable.getSelectionModel().getSelectedItems().get(0).getValue().product_category.getValue();
                    String itemcost=StockInventoryTable.getSelectionModel().getSelectedItems().get(0).getValue().product_cost.getValue();
                    String currentstock=StockInventoryTable.getSelectionModel().getSelectedItems().get(0).getValue().product_quan.getValue();

                    StockinCat.setValue(Category);
                    StockInITEMID.setText(id);
                    StockInName.setText(name);
                    StockinCost.setText(itemcost);
                    StockInStock.setText(currentstock);


                    if(Stockgroup.getSelectedToggle()==radio_stockin){
                        btnStockIn.setDisable(false);
                    }else{
                        btnStockOut.setDisable(false);
                    }

                }

            }
            //TABLE CART
            if(e.getSource()==tableCart){
                btnVoid.setDisable(false);
            }

        }
        @FXML
        private void doStockOut(){
            try{
                if(StockinAmount.getText().contentEquals("") && StockIntotalprice.getText().contentEquals("") && cboReasons.getSelectionModel().getSelectedItem()==null){
                        stockinerror.setText("Please input appropriate fields");
                        stockinerror.setVisible(true);
                }
                else if(StockinAmount.getText().contentEquals("") && StockIntotalprice.getText().contentEquals("")){
                        stockinerror.setText("Please input an amount to pull-out. . . .");
                        stockinerror.setVisible(true);
                }else if(cboReasons.getSelectionModel().getSelectedItem()==null){
                        stockinerror.setText("Please select the reason why to pull out . . . . ");
                        stockinerror.setVisible(true);
                }else{
                    //do stock out
                    String stockinid=StockinID.getText();
                    String itemId=StockInITEMID.getText();
                    String outdatedquantity=StockInStock.getText();
                    String name=StockInName.getText();
                    int Amount=Integer.parseInt(StockinAmount.getText());
                    double stockincost=Double.parseDouble(StockIntotalprice.getText());
                    String dateofentry=getDateid()+"";
                    String timeofentry=Time.getText();
                    int outdatedquan=Integer.parseInt(outdatedquantity);
                    int updatedquan=outdatedquan-Amount;
                    String pullout=cboReasons.getSelectionModel().getSelectedItem();

                    //inserts to stock out table

                        preparedStatement = getConnection().prepareStatement("Insert into tbl_stockout values(null,?,?,?,?,?,?,?,?)");

                        preparedStatement.setString(1, itemId);
                        preparedStatement.setString(2, Amount + "");
                        preparedStatement.setString(3, outdatedquantity);
                        preparedStatement.setString(4, updatedquan + "");
                        preparedStatement.setString(5, dateofentry);
                        preparedStatement.setString(6, timeofentry);
                        preparedStatement.setString(7, pullout);
                        preparedStatement.setString(8, stockincost + "");
                        preparedStatement.executeUpdate();

                        //logs Activity
                    preparedStatement=getConnection().prepareStatement("Insert into tbl_activitylog values(null,?,?,?,?)");
                    preparedStatement.setString(1,userId);
                    preparedStatement.setString(2,"Pulls Out "+Amount+" stocks on product "+name);
                    preparedStatement.setString(3, LocalDateTime.now().format(formatter));
                    preparedStatement.setString(4,LocalDateTime.now().format(time));
                    preparedStatement.executeUpdate();

                    //updates inventory
                    preparedStatement=getConnection().prepareStatement("Update tbl_products SET prod_quantity=prod_quantity-? where prod_id=?");
                    preparedStatement.setString(1,Amount+"");
                    preparedStatement.setString(2,itemId);
                    preparedStatement.executeUpdate();

                    Notifications notificationBuilder=Notifications.create().graphic(new ImageView("/resources/confirm-smaller.png")).hideAfter(Duration.seconds(2)).position(Pos.CENTER)
                            .title("Success").text("Successfully pulls out "+Amount+" To Product "+name);
                    notificationBuilder.show();


                    refreshProductMenu();
                    refreshInventoryTable(InventoryList);
                    refreshInventoryTable(StockinList);

                    StockinID.setText(getStockInID());
                    StockinCat.setValue("");
                    StockInITEMID.setText("");
                    StockInName.setText("");
                    StockinCost.setText("");
                    StockInStock.setText("");
                    StockinAmount.setText("");
                    StockIntotalprice.setText("");
                    btnStockIn.setDisable(true);
                    btnStockOut.setDisable(true);
                    stockinerror.setVisible(false);
                    refreshStockOutList();


                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }

        @FXML
        private void doStockIn(){
            try{
                if(StockinAmount.getText().contentEquals("") || StockIntotalprice.getText().contentEquals("")){
                   stockinerror.setText("Please input an amount to add. . . .");
                   stockinerror.setVisible(true);
                }else{
                    String stockinid=StockinID.getText();
                    String itemId=StockInITEMID.getText();
                    String outdatedquantity=StockInStock.getText();
                    String name=StockInName.getText();
                    int Amount=Integer.parseInt(StockinAmount.getText());
                    double stockincost=Double.parseDouble(StockIntotalprice.getText());
                    String dateofentry=getDateid()+"";
                    String timeofentry=Time.getText();
                    int outdatedquan=Integer.parseInt(outdatedquantity);
                    int updatedquan=outdatedquan+Amount;

                    try{
                        preparedStatement=getConnection().prepareStatement("Insert into tbl_stockin values(?,?,?,?,?,?,?,?)");
                        preparedStatement.setString(1,stockinid);
                        preparedStatement.setString(2,itemId);
                        preparedStatement.setString(3,Amount+"");
                        preparedStatement.setString(4,outdatedquantity);
                        preparedStatement.setString(5,updatedquan+"");
                        preparedStatement.setString(6,dateofentry);
                        preparedStatement.setString(7,timeofentry);
                        preparedStatement.setString(8,stockincost+"");
                        preparedStatement.executeUpdate();

                        //logs activity
                        preparedStatement=getConnection().prepareStatement("Insert into tbl_activitylog values(null,?,?,?,?)");
                        preparedStatement.setString(1,userId);
                        preparedStatement.setString(2,"Added "+Amount+" stocks on product "+name);
                        preparedStatement.setString(3, LocalDateTime.now().format(formatter));
                        preparedStatement.setString(4,LocalDateTime.now().format(time));
                        preparedStatement.executeUpdate();
                        //


                        preparedStatement=getConnection().prepareStatement("Update tbl_products SET prod_quantity=prod_quantity+? where prod_id=?");
                        preparedStatement.setString(1,Amount+"");
                        preparedStatement.setString(2,itemId);
                        preparedStatement.executeUpdate();

                        Notifications notificationBuilder=Notifications.create().graphic(new ImageView("/resources/confirm-smaller.png")).hideAfter(Duration.seconds(2)).position(Pos.CENTER)
                                .title("Success").text("Successfully Added "+Amount+" To Product "+name);
                        notificationBuilder.show();

                        refreshStockList();
                        refreshProductMenu();
                        refreshInventoryTable(InventoryList);
                        refreshInventoryTable(StockinList);

                        StockinID.setText(getStockInID());
                        StockinCat.setValue("");
                        StockInITEMID.setText("");
                        StockInName.setText("");
                        StockinCost.setText("");
                        StockInStock.setText("");
                        StockinAmount.setText("");
                        StockIntotalprice.setText("");
                        btnStockIn.setDisable(true);
                        btnStockOut.setDisable(true);
                        stockinerror.setVisible(false);


                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }




            }catch(Exception ex){
                ex.printStackTrace();
            }
        }

        @FXML
        private void addcategory()throws  Exception{
            FXMLLoader loader= new FXMLLoader(getClass().getResource("addCategory.fxml"));
            CategoryPanelController controller= new CategoryPanelController();
            loader.setController(controller);
            Parent root =loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.initOwner(btn_supplier.getScene().getWindow());
            stage.setScene(new Scene(root));
            new FlipInX(root).play();
            stage.showAndWait();
            if(controller.isResponse()==true){
            try{
                //logs activity
                preparedStatement=getConnection().prepareStatement("Insert into tbl_activitylog values(null,?,?,?,?)");
                preparedStatement.setString(1,userId);
                preparedStatement.setString(2,"Added a Category");
                preparedStatement.setString(3, LocalDateTime.now().format(formatter));
                preparedStatement.setString(4,LocalDateTime.now().format(time));
                preparedStatement.executeUpdate();
                //
            }catch(Exception ex){
                ex.printStackTrace();
            }}
            setComboBOx(InventoryCateg,"Select * from tbl_category","cat_description");
            refreshInventoryTable(InventoryList);
            refreshInventoryTable(StockinList);
            refreshProductMenu();
        }

        @FXML
        private void doVoid() throws Exception{

            FXMLLoader loader= new FXMLLoader(getClass().getResource("Alert.fxml"));
            Alert controller=new Alert();
            loader.setController(controller);
            Parent root =loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.initOwner(tableProduct.getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.showAndWait();
            new FlipInX(root).play();

            if(controller.isResponse()==true){
                int quan=Integer.parseInt(CartList.get(tableCart.getSelectionModel().getFocusedIndex()).product_quan.getValue());
                double price=Double.parseDouble(CartList.get(tableCart.getSelectionModel().getFocusedIndex()).product_price.getValue());
                double toberemove=quan*price;
                totalprice=totalprice-toberemove;

                CartList.remove(tableCart.getSelectionModel().getFocusedIndex());
                preparedStatement=getConnection().prepareStatement("Insert into tbl_activitylog values(null,?,?,?,?)");
                preparedStatement.setString(1,controller.getAdminID());
                preparedStatement.setString(2," Removed an Order");
                preparedStatement.setString(3, LocalDateTime.now().format(formatter));
                preparedStatement.setString(4,LocalDateTime.now().format(time));
                preparedStatement.executeUpdate();

                SalesPrice.setText(totalprice+"");
                btnVoid.setDisable(true);
                totalitems--;
                total_items();
            }

        }
        @FXML
        private void addSupplier() throws  Exception{
            FXMLLoader loader= new FXMLLoader(getClass().getResource("addSupplier.fxml"));
            suppliercontroller controller=new suppliercontroller();
            loader.setController(controller);
            Parent root =loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.initOwner(btn_supplier.getScene().getWindow());
            stage.setScene(new Scene(root));
            new FlipInX(root).play();
            stage.showAndWait();

            if(controller.isResponse()==true){
            try{
                //logs activity
                preparedStatement=getConnection().prepareStatement("Insert into tbl_activitylog values(null,?,?,?,?)");
                preparedStatement.setString(1,userId);
                preparedStatement.setString(2,"Added a Supplier");
                preparedStatement.setString(3, LocalDateTime.now().format(formatter));
                preparedStatement.setString(4,LocalDateTime.now().format(time));
                preparedStatement.executeUpdate();
                //
            }catch(Exception ex){
                ex.printStackTrace();
            }}
            setComboBOx(InventorySupp,"Select * from tbl_supplier","company_name");
            refreshInventoryTable(InventoryList);
            refreshInventoryTable(StockinList);
            refreshProductMenu();

        }
        @FXML
        private void CashOut(){

            if(SalesPrice.getText().contentEquals("") || SalesPrice.getText().contentEquals("") || SalesChange.getText().contentEquals("")){
                 JOptionPane.showMessageDialog(null,"Please input all fields","Warning",JOptionPane.WARNING_MESSAGE);
                //alert puhon
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
                    String sql="";

                        sql="Insert into tbl_Cart values(?,?)";
                        preparedStatement=getConnection().prepareStatement(sql);
                        preparedStatement.setString(1,LabelId.getText());
                        preparedStatement.setString(2,totalitem+"");
                        preparedStatement.executeUpdate();
                        preparedStatement.close();
                    //inserting to tbl_order or order line
                    sql="Insert into tbl_order values(null,?,?,?,?)";
                    //JOptionPane.showMessageDialog(null,tableCart.getCurrentItemsCount()+"");
                    for(int i=0;i<CartList.size();i++){
                        int id=Integer.parseInt(tableCart.getSelectionModel().getModelItem(i).getValue().product_id.getValue());
                        int quan=Integer.parseInt(tableCart.getSelectionModel().getModelItem(i).getValue().product_quan.getValue());
                        double totalorderprice=Double.parseDouble(tableCart.getSelectionModel().getModelItem(i).getValue().product_price.getValue());
                        totalorderprice=totalorderprice*quan;
                        preparedStatement=getConnection().prepareStatement(sql);
                        preparedStatement.setInt(1,id);
                        preparedStatement.setInt(2,quan);
                        preparedStatement.setString(3,totalorderprice+"");
                        preparedStatement.setString(4,LabelId.getText());
                        preparedStatement.executeUpdate();


                    }
                    preparedStatement.close();
                    resultSet.close();
                    confirm controller=new confirm();
                    confirmationdiaglogue(controller);
                    requested=true;
                    //JOptionPane.showMessageDialog(null,userId);
                    if(controller.isResponse()==true){
                        // diri mag insert sa receipt
                        checkdate();
                        preparedStatement=getConnection().prepareStatement("Insert into tbl_receipt values(null,?,?,?,?,?,?,?,?,?)");
                        preparedStatement.setString(1,controller.getCartID());
                        preparedStatement.setString(2,controller.getCustomer_id()+"");
                        preparedStatement.setString(3,userId);
                        preparedStatement.setString(4,getDateid()+"");
                        preparedStatement.setString(5,SalesPayment.getText());
                        preparedStatement.setString(6,SalesPrice.getText());
                        preparedStatement.setString(7,controller.getDiscountvalue()+"");
                        preparedStatement.setString(8,controller.getDiscountedprice()+"");
                        preparedStatement.setString(9,controller.getChange()+"");
                        preparedStatement.executeUpdate();
                        String str=getReceiptID();
                        preparedStatement=getConnection().prepareStatement("Select p.prod_id,p.prod_name,o.order_quantity,o.total_price,o.order_quantity*o.total_price,r.receipt_id from tbl_order o JOIN tbl_products p ON p.prod_id=o.product_id JOIN tbl_cart c ON c.Cart_id=o.Cart_id JOIN tbl_receipt r ON r.Cart_id=c.Cart_id where r.receipt_id=?");
                        preparedStatement.setString(1,str);
                        resultSet=preparedStatement.executeQuery();
                        while(resultSet.next()){
                            receiptId=resultSet.getString("receipt_id");
                            String productid=resultSet.getString("prod_id");
                            int quan=Integer.parseInt(resultSet.getString("order_quantity"));
                            double totalprice=Double.parseDouble(resultSet.getString("o.order_quantity*o.total_price"));
                            if(checkDuplicate(productid,receiptId)==false){

                                preparedStatement=getConnection().prepareStatement("insert into tbl_purchased values(null,?,?,?,?)");
                                preparedStatement.setString(1,receiptId);
                                preparedStatement.setString(2,productid);
                                preparedStatement.setString(3,quan+"");
                                preparedStatement.setString(4,totalprice+"");
                                preparedStatement.executeUpdate();
                            }else{
                                purchasedquan=purchasedquan+quan;
                                purchasedprice=purchasedprice+totalprice;
                                preparedStatement=getConnection().prepareStatement("Update tbl_purchased SET item_sold=? , total_sales=? WHERE prod_id=? AND Reciept_no=?");
                                preparedStatement.setString(1,purchasedquan+"");
                                preparedStatement.setString(2,purchasedprice+"");
                                preparedStatement.setString(3,productid);
                                preparedStatement.setString(4,receiptId);
                                preparedStatement.executeUpdate();
                                purchasedquan=0;
                                purchasedprice=0.00;
                            }
                        }
                        preparedStatement.close();
                        resultSet.close();
                        //
                        preparedStatement=getConnection().prepareStatement("Select *From tbL_purchased WHERE Reciept_no=(Select max(Reciept_no) from tbl_purchased)");
                        resultSet=preparedStatement.executeQuery();
                        while(resultSet.next()){
                            int itemsold=Integer.parseInt(resultSet.getString("item_sold"));
                            String id=resultSet.getString("prod_id");
                            preparedStatement=getConnection().prepareStatement(   "Update tbl_products SET prod_quantity=prod_quantity-? where prod_id=?");
                            preparedStatement.setString(1,itemsold+"");
                            preparedStatement.setString(2,id);
                            preparedStatement.executeUpdate();
                        }
                        preparedStatement=getConnection().prepareStatement("Insert into tbl_activitylog values(null,?,?,?,?)");
                        preparedStatement.setString(1,userId);
                        preparedStatement.setString(2,"Finished a transaction receipt no:"+receiptId);
                        preparedStatement.setString(3, LocalDateTime.now().format(formatter));
                        preparedStatement.setString(4,LocalDateTime.now().format(time));
                        preparedStatement.executeUpdate();
                        preparedStatement.close();
                        resultSet.close();

                        preparedStatement=getConnection().prepareStatement("Update tbl_products SET prod_status=0  where prod_quantity=0");
                        preparedStatement.executeUpdate();
                        preparedStatement.close();
                        printReport("SalesReceipt");
                        CheckInventory();
                        SalesPrice.setText("");
                        SalesChange.setText("");
                        SalesPayment.setText("");
                        btnCashOut.setDisable(true);
                        CartList.clear();
                        ItemCount.setText("0");
                        refreshProductMenu();
                        refreshInventoryTable(InventoryList);
                        refreshInventoryTable(StockinList);
                        refreshChart();

                        LabelId.setText(getID());
                        totalitems=0;
                        totalprice=0.0;
                        //Last time
                        //kulang nalang mag print ug resibo
                    }else {
                        try {
                            sql = "Delete from tbl_cart WHERE Cart_id=(Select max(Cart_id) from tbl_order)";
                            preparedStatement = getConnection().prepareStatement(sql);
                            preparedStatement.executeUpdate();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    }
                }catch(Exception ex){
                    ex.printStackTrace();
                }
                }
            }
            private void confirmationdiaglogue(confirm controller) throws  Exception{
                FXMLLoader loader= new FXMLLoader(getClass().getResource("Discount.fxml"));
                loader.setController(controller);
                Parent root =loader.load();
                controller.setFields(SalesPrice.getText(),SalesPayment.getText(),SalesChange.getText());
                controller.setAdminid(userId);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.initOwner(btn_supplier.getScene().getWindow());
                stage.setScene(new Scene(root));
                new FlipInX(root).play();
                stage.showAndWait();


            }

    public void printReport(String report) throws  SQLException{
        database = new dbconn();
        connect = getConnection();
        map = new HashMap<String, Object>();

        Report.createReport(connect, map, database.getReport(report, "report_jasper"));
        Report.showReport();
    }




        @FXML
        private void UpdateInventory(){
            if(InventoryName.getText().contentEquals("") || InventoryQuantity.getText().contentEquals("") || InventoryPrice.getText().contentEquals("") ){
                JOptionPane.showMessageDialog(null,"Please input all fields","Warning",JOptionPane.WARNING_MESSAGE);
//patay na.
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
                    refreshInventoryTable(StockinList);
                    refreshProductMenu();
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
               //diri pud
            }else {
                int id =Integer.parseInt(InventoryID.getText()) ;
                String name = InventoryName.getText();
                String quan = InventoryQuantity.getText();
                String itemcost=InventoryCost.getText();
                String itemprice = InventoryPrice.getText();
                int category = InventoryCateg.getSelectionModel().selectedIndexProperty().getValue();
                int supplier = InventorySupp.getSelectionModel().selectedIndexProperty().getValue();
                int status = Inventory_Status.getSelectionModel().selectedIndexProperty().getValue();
                String Date =getDateid()+"";
                String sql ="Insert into tbl_products values(?,?,?,?,?,?,?,?,?)";
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
                    preparedStatement.setString(6,itemcost);
                    preparedStatement.setString(7,Date);
                    preparedStatement.setInt(8,status);
                    preparedStatement.setInt(9,category);

                    preparedStatement.executeUpdate();


                    //logs activity
                    preparedStatement=getConnection().prepareStatement("Insert into tbl_activitylog values(null,?,?,?,?)");
                    preparedStatement.setString(1,userId);
                    preparedStatement.setString(2,"Added a Product : "+name+" to Inventory");
                    preparedStatement.setString(3, LocalDateTime.now().format(formatter));
                    preparedStatement.setString(4,LocalDateTime.now().format(time));
                    preparedStatement.executeUpdate();
                    //
                    refreshInventoryTable(InventoryList);
                    refreshInventoryTable(StockinList);
                    refreshProductMenu();
                    CurrentID++;
                    NewEntry();
                    Notifications notificationBuilder=Notifications.create().graphic(null).hideAfter(Duration.seconds(2)).position(Pos.CENTER)
                            .title("Success").text("Added To Inventory");
                    notificationBuilder.show();
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
            InventoryQuantity.setDisable(false);
        }

        private void NewField(){
            InventoryID.setText(CurrentID+"");
            InventoryName.setText("");
            InventoryQuantity.setText("0");
            InventoryPrice.setText("");
            InventoryCateg.setValue("");
            InventorySupp.setValue("");
            Inventory_Status.setValue("ACTIVE");
            InventoryCost.setText("");

        }


        private String setID(){

            int Temp=0;
            try{
                preparedStatement=getConnection().prepareStatement("Select max(prod_id) from tbl_products ");
                resultSet=preparedStatement.executeQuery();
                if(resultSet.next() && !(resultSet.getString("max(prod_id)")==null)){
                    Temp=Integer.parseInt(resultSet.getString("max(prod_id)"));
                }
                Temp++;
            }catch(Exception ex){
                ex.printStackTrace();
            }

            return Temp+"";

        }
        private void total_items(){
                ItemCount.setText(totalitems+"");
        }

        private  void Report(){
            try{
                String start=startingdate.getValue();
                String end=enddate.getValue();
                ReceiptList.clear();
                String sql="SELECT r.receipt_id,r.Cart_id,e.firstname,u.cus_firstname,r.total_price,r.discount_value,r.discounted_price,r.total_payment,r.total_change,concat_ws(\"-\",d.Year,d.Month,d.Date) as Date from tbl_receipt r Join tbl_customer u ON r.cus_id=u.cus_id JOIN tbl_employee e ON r.employee_id=e.user_id JOIN tbl_date d ON r.transaction_date=d.Date_id where concat_ws(\"-\",d.Year,d.Month,d.Date) BETWEEN ? AND ? GROUP BY r.receipt_id";//done
                preparedStatement=getConnection().prepareStatement(sql);
                preparedStatement.setString(1,start);
                preparedStatement.setString(2,end);
                resultSet=preparedStatement.executeQuery();
                while(resultSet.next()){
                    ReceiptList.add(new Receipts(resultSet.getString("receipt_id"),resultSet.getString("Cart_id"),resultSet.getString("cus_firstname"),resultSet.getString("firstname"),resultSet.getString("total_price"),resultSet.getString("discount_value"),resultSet.getString("discounted_price"),resultSet.getString("total_payment"),resultSet.getString("total_change"),resultSet.getString("Date")));
                }
            }catch (Exception a){
                a.printStackTrace();
            }
        }

        private void refreshInventoryTable(ObservableList list){
            try {
                int i =0;
                String status;
                list.clear();
                String sql = "select p.prod_id,p.prod_name,p.prod_quantity,p.prod_price,c.cat_description,s.company_name,p.prod_status,p.prod_cost FROM tbl_products p JOIN tbl_supplier s ON p.supplier_id = s.supplier_id JOIN tbl_category c ON p.category_id=c.category_id Group by p.prod_id";
                preparedStatement = getConnection().prepareStatement(sql);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    i++;
                    int temp=Integer.parseInt(resultSet.getString("prod_status"));
                    if(temp==0){
                        status="INACTIVE";
                    }else{
                        status="ACTIVE";
                    }
                    list.add(new Inventory(resultSet.getString("prod_id"), resultSet.getString("prod_name"), resultSet.getString("prod_quantity"),resultSet.getString("prod_price"), resultSet.getString("cat_description"), resultSet.getString("company_name"),status,resultSet.getString("prod_cost")));

                }
                InventoryCount.setText(i+"");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        private void refreshStockOutList(){

        try{
            StockOutList.clear();
            String sql="Select s.StockOut_id,p.prod_name,s.StockBefore_Update,s.updatedquantity,s.lastOut,concat_ws(\"-\",d.Year,d.Month,d.Date) as Date,s.departure_time,s.Status,s.TotalLoss from tbl_stockout s JOIN tbl_products p ON s.prod_id=p.prod_id JOIN tbl_date d ON s.Departure_date=d.Date_id Group by s.StockOut_Id";
            preparedStatement=getConnection().prepareStatement(sql);//done
            resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                StockOutList.add(new Stocks(resultSet.getString("StockOut_id"),resultSet.getString("prod_name"),resultSet.getString("StockBefore_Update"),resultSet.getString("lastOut"),resultSet.getString("updatedquantity"),resultSet.getString("Date"),resultSet.getString("departure_time"),resultSet.getString("TotalLoss"),resultSet.getString("status")));
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
        private void refreshStockList(){

            try{
                StocksTableList.clear();
                String sql="Select s.Stockin_id,p.prod_name,s.Stock_BeforeUpdate,s.updatedquantity,s.LastEntry,concat_ws(\"-\",d.Year,d.Month,d.Date) as Date,s.Entry_time,s.total_cost from tbl_stockin s JOIN tbl_products p ON s.prod_id=p.prod_id JOIN tbl_date d ON s.Entry_date=d.Date_id Group by Stockin_id";
                preparedStatement=getConnection().prepareStatement(sql);//done
                resultSet=preparedStatement.executeQuery();
                while(resultSet.next()){
                    StocksTableList.add(new Stocks(resultSet.getString("Stockin_id"),resultSet.getString("prod_name"),resultSet.getString("Stock_BeforeUpdate"),resultSet.getString("LastEntry"),resultSet.getString("updatedquantity"),resultSet.getString("Date"),resultSet.getString("Entry_time"),resultSet.getString("total_cost")));
                }

            }catch(Exception e){
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

            dateTime.setText(LocalDateTime.now().format(formatter));
            Time.setText(LocalDateTime.now().format(time));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();

            try {
                    //JOptionPane.showMessageDialog(null,LocalDateTime.now().getDayOfMonth());
                if (checkdate()==false) {
                    String month="";
                    String dayofyear="";
                    if(LocalDateTime.now().getMonthValue()<10){
                        month="0"+LocalDateTime.now().getMonthValue();
                    }else{
                        month=LocalDateTime.now().getMonthValue()+"";
                    }
                    if(LocalDateTime.now().getDayOfMonth()<10){
                        dayofyear="0"+LocalDateTime.now().getDayOfMonth();
                    }else{
                        dayofyear=LocalDateTime.now().getDayOfMonth()+"";
                    }
                    //JOptionPane.showMessageDialog(null,month+dayofyear);
                    //JOptionPane.showMessageDialog(null,LocalDateTime.now().getYear() +" year "+LocalDateTime.now().getMonthValue()+" Month "+LocalDateTime.now().getDayOfMonth());
                    preparedStatement = getConnection().prepareStatement("Insert into tbl_date values(null,?,?,?)");// i edit ang date
                    preparedStatement.setString(1, LocalDateTime.now().getYear()+"");
                    preparedStatement.setString(2,month);
                    preparedStatement.setString(3,dayofyear);
                    preparedStatement.executeUpdate();
                }
                preparedStatement.close();
                resultSet.close();
            }catch (Exception ex){
                ex.printStackTrace();
            }
    }
        private boolean checkdate(){
        boolean bool=false;
        try {
                preparedStatement = getConnection().prepareStatement("Select *From tbl_date where concat_ws(\"-\",Year,Month,Date)=?");//edit ang date
                //fJOptionPane.showMessageDialog(null,LocalDateTime.now().format(formatter));
                preparedStatement.setString(1, LocalDateTime.now().format(formatter));
                resultSet = preparedStatement.executeQuery();
                if(resultSet.next()==true){
                    bool=true;
                    setDateid(Integer.parseInt(resultSet.getString("Date_id")));
                    //JOptionPane.showMessageDialog(null,"Pass Here Please");
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
            return bool;
        }

        private void SelectToCart(String id,String name, String Quan, String Price, ObservableList list) {
            double price= Double.parseDouble(Price);
            double quantity=Double.parseDouble(Quan);
            totalprice += price*quantity;

            SalesPrice.setText(totalprice.toString());
            list.add(new products(id,name, Quan, Price));
        }
        private String getReceiptID(){
        String str="";
        try{
            preparedStatement=getConnection().prepareStatement("Select max(receipt_id) from tbl_receipt");
            resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                str=resultSet.getString("max(receipt_id)");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
            return str;
    }
    private boolean checkDuplicate(String str,String receipt){
        try{
            PreparedStatement statement=getConnection().prepareStatement("Select *from tbl_purchased where prod_id=? AND Reciept_no=?");
            statement.setString(1,str);
            statement.setString(2,receipt);
            ResultSet res=statement.executeQuery();
            if(res.next()){
                purchasedquan=Integer.parseInt(res.getString("item_sold"));
                purchasedprice=Double.parseDouble(res.getString("total_sales"));
                res.close();
                statement.close();
                return true;
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
       return false;
    }

    public void setDateid(int dateid) {
        this.dateid = dateid;
    }

    public int getDateid() {
        return dateid;
    }
}


