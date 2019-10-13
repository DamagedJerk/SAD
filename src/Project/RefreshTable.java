package Project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RefreshTable {

    ObservableList<Employee> EmployeeList= FXCollections.observableArrayList();
    public RefreshTable(){

    }
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private static Connection getConnection() throws SQLException {
        Connection conn;
        dbconn.getInstance();
        conn=dbconn.connect();
        return conn;
    }

    public void RefreshEmployee(){
        try {
            EmployeeList.clear();
            String sql = "SELECT * FROM `tbl_employee` e JOIN tbl_role r ON e.role=r.role;";
            preparedStatement = getConnection().prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                EmployeeList.add(new Employee(resultSet.getString("user_id"),resultSet.getString("firstname"),resultSet.getString("lastname"),resultSet.getString("user_name"),resultSet.getString("password"),resultSet.getString("email"),resultSet.getString("cell_number"),resultSet.getString("description")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
