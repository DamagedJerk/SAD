package Project;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Stocks extends RecursiveTreeObject<Stocks> {
    StringProperty product_id;
    StringProperty product_name;
    StringProperty before_update_stocks;
    StringProperty current_stocks;
    StringProperty Last_Entry;
    StringProperty Date;
    StringProperty Time;
    StringProperty TotalCost;
    public Stocks(String id ,String prodname,String formerStocks,String entry,String quan,String Date,String Time,String cost) {
        this.product_id=new SimpleStringProperty(id);
        this.product_name = new SimpleStringProperty(prodname);
        this.current_stocks = new SimpleStringProperty(quan);
        //this.Entry_type = new SimpleStringProperty(type);
        this.Last_Entry = new SimpleStringProperty(entry);
        this.Date = new SimpleStringProperty(Date);
        this.Time= new SimpleStringProperty(Time);
        this.TotalCost= new SimpleStringProperty(cost);
        this.before_update_stocks=new SimpleStringProperty(formerStocks);
        //this.updated_quantity=new SimpleStringProperty()

    }
}
