package Project;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Category extends RecursiveTreeObject<Category> {
    StringProperty category_id;
    StringProperty category_description;

    public Category(String id,String desc){
        this.category_id=new SimpleStringProperty(id);
        this.category_description=new SimpleStringProperty(desc);
    }
}
