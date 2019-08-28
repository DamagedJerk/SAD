package Project;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Suppliers extends RecursiveTreeObject<Suppliers> {

    StringProperty supplier_id;
    StringProperty company_name;
    StringProperty contact_number;
    StringProperty email;

    public Suppliers(String supplier_id,String company_name,String contact_number,String email){
        this.supplier_id=new SimpleStringProperty(supplier_id);
        this.company_name=new SimpleStringProperty(company_name);
        this.contact_number=new SimpleStringProperty(contact_number);
        this.email=new SimpleStringProperty(email);
    }
}
