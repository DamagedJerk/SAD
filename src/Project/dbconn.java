package Project;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;


public class dbconn {

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
}
