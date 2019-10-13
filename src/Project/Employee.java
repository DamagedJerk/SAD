package Project;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Employee extends RecursiveTreeObject<Employee> {
    StringProperty id;
    StringProperty firstname;
    StringProperty lastname;
    StringProperty username;
    StringProperty password;
    StringProperty cellnumber;
    StringProperty Role;

    public Employee(String id,String fname,String lname,String username,String password,String cell,String Role){
        this.id=new SimpleStringProperty(id);
        this.firstname=new SimpleStringProperty(fname);
        this.lastname=new SimpleStringProperty(lname);
        this.username=new SimpleStringProperty(username);
        this.password=new SimpleStringProperty(password);
        this.cellnumber=new SimpleStringProperty(cell);
        this.Role=new SimpleStringProperty(Role);
    }
}
