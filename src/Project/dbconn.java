package Project;

import java.sql.Connection;
import java.sql.DriverManager;


public class dbconn {

    public  static Connection connect;
    public static Connection conn(){

         String dbname = "cellections";
         String user = "root";
         String pass = "";

         try{
             Class.forName("com.mysql.jdbc.Driver");

             connect= DriverManager.getConnection("jdbc:mysql://localhost/"+dbname,pass,user);
         }catch(Exception e){
             e.printStackTrace();
         }

         return connect;
    }
}
