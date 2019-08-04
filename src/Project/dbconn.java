package Project;

import java.sql.Connection;
import java.sql.DriverManager;


public class dbconn {

    public Connection connect;
    public Connection conn(){

         String dbname = "cellections";
         String user = "root";
         String pass = "";

         try{
             Class.forName("com.mysql.jdbc.Driver");

             connect= DriverManager.getConnection("jdbc:mysql://localhost/"+dbname,user,pass);
         }catch(Exception e){
             e.printStackTrace();
         }

         return connect;
    }
}
