package orderingsystem;

import java.sql.*;
import java.time.LocalDate;
import javax.swing.*;
import javax.swing.table.*;

public class methods {
   
    String url = "jdbc:mysql://localhost:3306/products";    
    String user = "root";
    String pass = "PassWord";

    public methods() { 
        
    }
    
    public void updateSupplyTable (DefaultTableModel model) {
        
        String instruction = "SELECT IDproduct, ProductName, Price, Stocks FROM prddatabase";
        
        model.setRowCount(0);

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement pst = conn.prepareStatement(instruction)) {
            
            ResultSet rst = pst.executeQuery();
            
            while (rst.next()) {
                String prodID = rst.getString("IDproduct");
                String prodName = rst.getString("ProductName");
                String pri = rst.getString("Price");
                String stk = rst.getString("Stocks");
                Object[] rows = {prodID, prodName, pri, stk};
                
                model.addRow(rows);
            }
        } catch (SQLException sql) {
            System.out.println("Error: " + sql.getMessage());
        }
    }
    
    public void updateStock(int quantityToUpdate, String IDproduct) {
        
        String retrieve = "SELECT Stocks FROM prddatabase WHERE IDproduct = ?";
        String update = "UPDATE prddatabase SET Stocks = ? WHERE IDproduct = ?";
        
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement retrievePst = conn.prepareStatement(retrieve);
             PreparedStatement updatePst = conn.prepareStatement(update)) {
            retrievePst.setString(1, IDproduct);
            ResultSet rs = retrievePst.executeQuery();
            
            if (rs.next()) {
                int stock = rs.getInt("Stocks");
                
                int newValue = stock - quantityToUpdate;

                updatePst.setInt(1, newValue);
                updatePst.setString(2, IDproduct);

                updatePst.executeUpdate();
            }
        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle.getMessage());
        }
    }
    
     public void updateStockFromTable(JTable table) {
        int rowCount = table.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            String IDproduct = (String) table.getValueAt(i, 0);
            String quantityToUpdateStr = (String) table.getValueAt(i, 2);
            int quantityToUpdate = Integer.parseInt(quantityToUpdateStr);
            updateStock(quantityToUpdate, IDproduct);
        }
    }
     
     public void salesTableUpdate (LocalDate date, long orderNumber, double profit) {
         String store = "INSERT INTO sales.daily (Date, OrderNumber, Profit) VALUES (?, ?, ?)";

         try (Connection conn = DriverManager.getConnection(url, user, pass);
                 PreparedStatement pst = conn.prepareStatement(store)) {

                 pst.setDate(1, java.sql.Date.valueOf(date));
                 pst.setLong(2, orderNumber);
                 pst.setDouble(3, profit);
                 
                 pst.executeUpdate();
   
         }
         catch (SQLException sqle) {
             
         }
     }
     public void deleteRow(int rowIndex, DefaultTableModel model) {
        if (rowIndex >= 0 && rowIndex < model.getRowCount()) {
            String id = model.getValueAt(rowIndex, 0).toString();
            model.removeRow(rowIndex);
            deleteFromDatabase(id);
        }
    }

    public void deleteFromDatabase(String id) {
        String deleteQuery = "DELETE FROM prddatabase WHERE IDproduct = ?";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement pstmt = conn.prepareStatement(deleteQuery)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
     public void collectedProfitsFromEachMonth() {
        // Connect to the source database and aggregate monthly profits
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement pst = conn.prepareStatement(
                "SELECT DATE_FORMAT(Date, '%M %Y') AS Months, SUM(Profit) AS Profit " +
                "FROM sales.daily " +
                "GROUP BY Months")) {

            ResultSet rs = pst.executeQuery();

            // Connect to the target database and insert aggregated data
            String insertUrl = "jdbc:mysql://localhost:3306/sales"; // Update with your target database details
            String insertUser = "root";
            String insertPass = "PassWord";
            
            try (Connection insertConn = DriverManager.getConnection(insertUrl, insertUser, insertPass);
                 PreparedStatement insertPst = insertConn.prepareStatement(
                    "INSERT INTO sales.monthly (Months, Profit) VALUES (?, ?)"
                  + " ON DUPLICATE KEY UPDATE Profit = VALUES(Profit);")) {

                while (rs.next()) {
                    Object month = rs.getString("Months");
                    double totalProfit = rs.getDouble("Profit");

                    insertPst.setObject(1, month);
                    insertPst.setDouble(2, totalProfit);
                    insertPst.executeUpdate();
                }
            }
        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle.getMessage());
        }
    }
     
     public void collectedProfitsFromEachYear() {
        // Connect to the source database and aggregate monthly profits
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement pst = conn.prepareStatement(
                "SELECT DATE_FORMAT(Date, '%Y') AS Years, SUM(Profit) AS Profit " +
                "FROM sales.daily " +
                "GROUP BY Years")) {

            ResultSet rs = pst.executeQuery();

            // Connect to the target database and insert aggregated data
            String insertUrl = "jdbc:mysql://localhost:3306/sales"; // Update with your target database details
            String insertUser = "root";
            String insertPass = "PassWord";
            
            try (Connection insertConn = DriverManager.getConnection(insertUrl, insertUser, insertPass);
                 PreparedStatement insertPst = insertConn.prepareStatement(
                    "INSERT INTO sales.yearly (Years, Profit) VALUES (?, ?) ON DUPLICATE KEY UPDATE Profit = VALUES(Profit);")) {

                while (rs.next()) {
                    Object year = rs.getString("Years");
                    double totalProfit = rs.getDouble("Profit");

                    insertPst.setObject(1, year);
                    insertPst.setDouble(2, totalProfit);
                    insertPst.executeUpdate();
                }
            }
        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle.getMessage());
        }
    } 
   
     public void addToCart (DefaultTableModel model, JTextField idField, JTextField nameField, JTextField qField,
            JTextField pField, JTextField stpField, JTextField tField, JTextArea resibo, StringBuilder t) {
         
        String item = nameField.getText();
        String qty = qField.getText();
        String pr = pField.getText();
         
        Object[]rows = {idField.getText(), nameField.getText(), qField.getText(), pField.getText(), stpField.getText()}; 
           model.addRow(rows);
           JOptionPane.showMessageDialog(null, "Data Added Successfully!");


           int prodNameWidth = 18;
           int qtyWidth = 10;
           int priceWidth = 10;

           // Get the text from the fields
    
           for (int i = 0; i < item.length(); i += prodNameWidth) {
           String part = item.substring(i, Math.min(i + prodNameWidth, item.length()));
           if (i == 0) {
               // First line with qty and price
               t.append(String.format("     %-"+prodNameWidth+"s\t%-"+qtyWidth+"s\t%-"+priceWidth+"s\n", part, qty, pr));
           } else {
               // Subsequent lines only with the product name
               t.append(String.format("     %-"+prodNameWidth+"s\n", part));
           }
       }
           resibo.setText(t.toString());               

   //this code will erase all the text in the textfield above whenever the addbutton is pressed
           idField.setText("");
           nameField.setText("");
           qField.setText("");
           pField.setText("");

           //totalTextField.setText(subtotalpriceField.getText());

           //checks if the textfields are empty, if they are then 0.0 is set to sTF and tTF
           //if the textfields aren't empty, it will convert the text into Double and put it inside sTF and tTF
           double sTF = stpField.getText().isEmpty() ? 0.0 : Double.parseDouble(stpField.getText());
           double tTF = tField.getText().isEmpty() ? 0.0 : Double.parseDouble(tField.getText());

           //adds the two values
           double result = sTF + tTF;

           //turns the value onto a text and display it inside the textfield
           tField.setText(String.valueOf(result));
           stpField.setText("");

        }
     
     public int CheckStock(JTable table, Object id, int idIndex, int stockIndex) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        for (int row = 0; row < model.getRowCount(); row++) {
            if (model.getValueAt(row, idIndex).equals(id)) {
                String stockVal = (String) model.getValueAt(row, stockIndex);
                return Integer.parseInt(stockVal);
            }
        }
        return -1; 
    }
     
}
