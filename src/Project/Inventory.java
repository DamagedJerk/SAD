package Project;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

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