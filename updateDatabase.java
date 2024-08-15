package Methods;

import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class updateDatabase {

public void updateDatabase(String id, String stocks, String newValue) {

        String url = "jdbc:mysql://localhost:3306/products";
        String user = "root";
        String pass = "PassWord";

        String updateQuery = "UPDATE prddatabase SET Stocks = ? WHERE IDproduct = ?";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
                pstmt.setString(1, newValue);
                pstmt.setString(2, id);
                pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}