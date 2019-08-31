package Project;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

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
