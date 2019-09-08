package Project;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Receipts extends RecursiveTreeObject<Receipts> {
    StringProperty ReceiptId;
    StringProperty CustomerName;
    StringProperty EmployeeName;
    StringProperty CartID;
    StringProperty TransactionDate;
    StringProperty TotalPrice;
    StringProperty DiscountedValue;
    StringProperty DiscountedPrice;
    StringProperty TotalPayment;
    StringProperty TotalChange;

    public Receipts(String id,String cart,String cusName,String empName,String totalprice,String disValue,String disPrice,String cash,String change,String date){
        this.ReceiptId=new SimpleStringProperty(id);
        this.CartID=new SimpleStringProperty(cart);
        this.CustomerName=new SimpleStringProperty(cusName);
        this.EmployeeName=new SimpleStringProperty(empName);
        this.TotalPrice=new SimpleStringProperty(totalprice);
        this.DiscountedValue=new SimpleStringProperty(disValue);
        this.DiscountedPrice=new SimpleStringProperty(disPrice);
        this.TotalPayment=new SimpleStringProperty(cash);
        this.TotalChange=new SimpleStringProperty(change);
        this.TransactionDate=new SimpleStringProperty(date);
    }

}
