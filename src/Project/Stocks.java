package Project;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Stocks extends RecursiveTreeObject<Stocks> {
    StringProperty product_id;
    StringProperty product_name;
    StringProperty current_stocks;
    StringProperty Entry_type;
    StringProperty Last_Entry;
    StringProperty Date;
    StringProperty Time;
    public Stocks(String id ,String prodname, String quan, String type,String entry,String Date,String Time) {
        this.product_id=new SimpleStringProperty(id);
        this.product_name = new SimpleStringProperty(prodname);
        this.current_stocks = new SimpleStringProperty(quan);
        this.Entry_type = new SimpleStringProperty(type);
        this.Last_Entry = new SimpleStringProperty(entry);
        this.Date = new SimpleStringProperty(Date);
        this.Time= new SimpleStringProperty(Time);

    }
}
