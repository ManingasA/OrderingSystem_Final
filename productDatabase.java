/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package orderingsystem;
import java.sql.*;
import javax.swing.*;

//this class is responsible for the database for the products
//this calls on the MainPage since it uses the database
public class productDatabase {
    MainPage main;
    ResultSet rs;
   
    public productDatabase(MainPage mainPage) {
        this.main = mainPage;
        
        //if the user inputs an ID, it will perform the following codes
        if (!main.prodIDField.getText().isEmpty()) {
        //sql syntax for selecting a row in the product database
        String id = "SELECT IDproduct, ProductName, Price FROM prddatabase WHERE IDproduct = ?;";
        //connects database to java
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/products", "root", "PassWord");
                PreparedStatement pst = conn.prepareStatement(id)) {
               //sets the text from the IDfield to column 1
                pst.setString(1, main.prodIDField.getText());
                rs = pst.executeQuery();
                //checks every row in the database to see if anything matched with the users input
                if (rs.next()) {
                    //if there is a match, it will get the corresponding row and store into a string to be called later on
                    String retrievedProductName = rs.getString("ProductName");
                    String retrievedPrice = rs.getString("Price");
                    //sets the retrieved data from the database to the corresponding textfield
                    main.prodNameField.setText(retrievedProductName);
                    main.priceField.setText(retrievedPrice);
                    main.qtyField.setText("1");
                    //turns the String text of the Price Textfield into a double data type
                    double price = Double.parseDouble(retrievedPrice);
                    //turns the String text of the Quantity Textfield into an integer 
                    Integer quantity = Integer.valueOf(main.qtyField.getText());
                    
                    //multiplies the price and quantity
                    double subT = price * quantity;
                    //displays the result into the subtotal textfield
                    main.subtotalpriceField.setText(String.valueOf(subT));
                    
                }   
                else {
                    JOptionPane.showMessageDialog(null, "No matching ID found");
                }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        //this is code is similar to the code above, 
        //except this is when the user inputs the product name instead
        }
        else if (!main.prodNameField.getText().isEmpty()) {
            String name = "SELECT IDproduct, ProductName, Price FROM prddatabase WHERE ProductName = ?;";
        //connect
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/products", "root", "PassWord");
                PreparedStatement pst = conn.prepareStatement(name)) {
               
                pst.setString(1, main.prodNameField.getText());
                rs = pst.executeQuery();
                
                if (rs.next()) {
                    
                    String retrievedProductID = rs.getString("IDproduct");
                    String retrievedPrice = rs.getString("Price");
                    
                    main.prodIDField.setText(retrievedProductID);
                    main.priceField.setText(retrievedPrice);
                    main.qtyField.setText("1");
                    
                    double price = Double.parseDouble(retrievedPrice);
                    Integer quantity = Integer.parseInt(main.qtyField.getText());
                    
                    double subT = price * quantity;
                    
                    main.subtotalpriceField.setText(String.valueOf(subT));
                    
                }   
                else {
                    JOptionPane.showMessageDialog(null, "No matching ID found");
                }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        }
    }
}
          
   