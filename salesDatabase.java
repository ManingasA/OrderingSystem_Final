/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Methods;

import java.sql.*;

public class salesDatabase {
       
     private String url = "dada";
     private String user = "root";
     private String pass = "PassWord";
     
     
    public salesDatabase () {
     
       try (Connection conn = DriverManager.getConnection(url, user, pass);
               PreparedStatement pst = conn.prepareStatement("SELECT * FROM sales.daily")) {
           
       }
       catch (SQLException sqle) {
           sqle.printStackTrace();
       }
        
    }
}
