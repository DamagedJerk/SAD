package Project;

import javax.swing.*;
import java.io.InputStream;
import java.sql.*;


public class dbconn {

    private PreparedStatement preparedStatement=null;
    private ResultSet resultSet=null;
    public  static Connection connect;
    private static dbconn connectme=null;
    public static Connection connect(){

         String dbname = "cellections";
         String user = "root";
         String pass = "";

         try{
             Class.forName("com.mysql.jdbc.Driver");

             connect= DriverManager.getConnection("jdbc:mysql://localhost/"+dbname,user,pass);
             //JOptionPane.showMessageDialog(null,"Successfully Connected");
         }catch(Exception e){
             //JOptionPane.showMessageDialog(null,"Cannot Connect to Database","Warning",JOptionPane.ERROR_MESSAGE);
             e.printStackTrace();
         }

         return connect;
    }
    public static dbconn getInstance()
    {
        if (connectme == null) { connectme = new dbconn(); }
        return connectme;
    }
    private static Connection getConnection() throws SQLException {
        Connection conn;
        dbconn.getInstance();
        conn = dbconn.connect();
        return conn;
    }


    public InputStream getReport(String report_name, String column_name) {
        InputStream input = null;
        String query = "SELECT "+column_name+" FROM tbl_report WHERE report_name='"+report_name+"'";
        try {

            connect = getConnection();
            preparedStatement = connect.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                input = resultSet.getBinaryStream(1);
            }

        }catch(Exception e) {
            e.printStackTrace();
        }

        return input;
    }
}
