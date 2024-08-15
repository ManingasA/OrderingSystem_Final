/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package orderingsystem;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.Date;
import java.awt.*;
import java.awt.event.*;
import java.text.*;
import com.toedter.calendar.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class SalesCatalog implements ActionListener{
    
    String url = "jdbc:mysql://localhost:3306/products";
    String uName = "root";
    String pass = "PassWord";
    
    JFrame frame = new JFrame();
    JComboBox<String> combo;
    JLabel date, time;
    Date CD = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("LLLL dd, yyyy");
    SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
    JPanel salesPanel, spanel;
 
    JTable salesTable;
    Object []column1 = {"Date", "OrderNumber", "Profit"};
    Object []column2 = {"Months", "Profit"};
    Object []column3 = {"Years", "Profit"};
    
    DefaultTableModel model = new DefaultTableModel();   
    JScrollPane saleScroll;
    
    CardLayout card = new CardLayout();
    JPanel datePicker = new JPanel(card);
    JTextField search;
    JDateChooser dateChooser;
    JMonthChooser monthChooser;
    JYearChooser yearChooser;
 
    @SuppressWarnings({"LeakingThisInConstructor", "Convert2Lambda"})
    public SalesCatalog() {
        
        salesPanel = new JPanel();
        salesPanel.setBounds(0, 0, 1365, 720);
        salesPanel.setLayout(null);
        
        spanel = new JPanel();
        spanel.setBounds(270, 150, 1000, 495);
        spanel.setBackground(Color.GRAY);
        spanel.setLayout(null);

        date = new JLabel("Date: " + dateFormat.format(new Date()));
        date.setBounds(1115, 15, 250, 50);
        date.setFont(new Font("Arial", Font.PLAIN, 20));
        
        time = new JLabel("Time: " + timeFormat.format(new Date()));
        time.setBounds(1115, 45, 250, 50);
        time.setFont(new Font("Arial", Font.PLAIN, 20));
 
        combo = new JComboBox<>();
        combo.addActionListener(this);
        combo.addItem("Daily");
        combo.addItem("Monthly");
        combo.addItem("Yearly");
        combo.setBounds(1115, 95, 95, 20);
        combo.setOpaque(false);
       
        datePicker.setBounds(1115, 120, 107, 20);
        
        search = new JTextField();
        search.setBounds(270, 50, 110, 20);
        
        dateChooser = new JDateChooser();
        dateChooser.setBounds(1220, 95, 22, 20);
        
        monthChooser = new JMonthChooser();
        monthChooser.setBounds(270, 80, 107, 20);
        
        yearChooser = new JYearChooser();
        yearChooser.setBounds(270, 110, 107, 20);
        
        salesTable = new JTable();
        model.setColumnIdentifiers(column1);
        
        //salesTable.setBounds(30, 80, 100, 100);
        salesTable.setModel(model);
        salesTable.setFont(new Font("Arial", Font.PLAIN, 13));
        salesTable.setRowHeight(30);
        
        
        saleScroll = new JScrollPane(salesTable);
        saleScroll.setBounds(0, 0, 1000, 485);

        spanel.add(saleScroll);

        datePicker.add(dateChooser, "Day");
        //datePicker.add(search, "Search");
        datePicker.add(monthChooser, "Month");
        datePicker.add(yearChooser, "Year");
        
        salesPanel.add(datePicker);
        salesPanel.add(combo);
        salesPanel.add(date);
        salesPanel.add(time);
        salesPanel.add(spanel);
        
    
        int delay = 1000;
        @SuppressWarnings("Convert2Lambda")
        ActionListener update = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
           date.setText("Date: " + dateFormat.format(new Date()));
           time.setText("Time: " + timeFormat.format(new Date()));
           
        }
        };
        new Timer (delay, update).start();
        
          dateChooser.addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                filterForDaily();
            }
        });
          
          monthChooser.addPropertyChangeListener("month", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                filterByMonth();
            }
        });
          
          yearChooser.addPropertyChangeListener("year", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                filterByYear();
            }
        });
    }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            methods m = new methods();
            if (e.getSource() == combo) {
                String choose = (String) combo.getSelectedItem();
                model.setRowCount(0);
                switch (choose) {
                    case "Daily":
                        card.show(datePicker, "Day");
                        //dateChooser.setBounds(1115, 125, 107, 20);
                        model.setColumnIdentifiers(column1);
                        try (Connection conn = DriverManager.getConnection(url, uName, pass);
                                PreparedStatement pst = conn.prepareStatement(
                                        "SELECT DATE_FORMAT(Date, '%M %d, %Y') AS Date, OrderNumber, Profit FROM sales.daily")) {
                                ResultSet rs = pst.executeQuery();

                                while (rs.next()) {
                                    String col1 = rs.getString("Date");
                                    String col2 = rs.getString("OrderNumber");
                                    String col3 = rs.getString("Profit");

                                    Object rows[] = {col1, col2, col3};
                                    model.addRow(rows);
                                }
                        }
                        catch (SQLException sqle) {
                            System.out.println("Error: " + sqle.getMessage());
                        }
                            if (e.getSource() == dateChooser) {
                            
                        }
                      break;  
                    case "Monthly":
                        card.show(datePicker, "Month");
                        model.setColumnIdentifiers(column2);
                        m.collectedProfitsFromEachMonth();
                                    
                        try (Connection conn = DriverManager.getConnection(url, uName, pass);
                                PreparedStatement pst = conn.prepareStatement("SELECT Months, Profit FROM sales.monthly")) {
                                ResultSet rs = pst.executeQuery();

                                while (rs.next()) {
                                    String col1 = rs.getString("Months");
                                    String col2 = rs.getString("Profit");

                                    Object rows[] = {col1, col2};
                                    model.addRow(rows);
                                }
                                salesTable.setRowHeight(39);
                                
                        }
                        catch (SQLException sqle) {
                            System.out.println("Error: " + sqle.getMessage());
                        }
                      break;     
                    case "Yearly":
                        card.show(datePicker, "Year");
                        m.collectedProfitsFromEachYear();
                        model.setColumnIdentifiers(column3);
                        try (Connection conn = DriverManager.getConnection(url, uName, pass);
                                PreparedStatement pst = conn.prepareStatement("SELECT Years, Profit FROM sales.yearly")) {
                                ResultSet rs = pst.executeQuery();

                                while (rs.next()) {
                                    String col1 = rs.getString("Years");
                                    String col2 = rs.getString("Profit");

                                    Object rows[] = {col1, col2};
                                    model.addRow(rows);
                                }
                        }
                        catch (SQLException sqle) {
                            System.out.println("Error: " + sqle.getMessage());                       
                        }
                      break;  
                }
            }
               
        }

        private void filterForDaily() {  
            
            Date selectedDate = dateChooser.getDate();
                if (selectedDate == null) {
                    try (Connection conn = DriverManager.getConnection(url, uName, pass);
                    PreparedStatement pst = conn.prepareStatement(
                            "SELECT DATE_FORMAT(Date, '%M %d, %Y') AS Date, OrderNumber, Profit FROM sales.daily")) {
                   ResultSet rs = pst.executeQuery();

                   while (rs.next()) {
                       String col1 = rs.getString("Date");
                       String col2 = rs.getString("OrderNumber");
                       String col3 = rs.getString("Profit");

                       Object rows[] = {col1, col2, col3};
                       model.addRow(rows);
                   }
               } 
                    catch (SQLException sqle) {
                   System.out.println("Error: " + sqle.getMessage());
               }
                    return;
                }
                SimpleDateFormat dFormat = new SimpleDateFormat("yyy-MM-dd");
                String selectedDateString = dFormat.format(selectedDate);
                
                String sql = "SELECT DATE_FORMAT(Date, '%M %d, %Y') AS Date, OrderNumber, Profit FROM sales.daily WHERE Date = ?";
                    try (Connection conn = DriverManager.getConnection(url, uName, pass);
                         PreparedStatement pst = conn.prepareStatement(sql)) {
                        pst.setString(1, selectedDateString);
                        ResultSet rs = pst.executeQuery();

                        DefaultTableModel tblModel = (DefaultTableModel) salesTable.getModel();
                        tblModel.setRowCount(0);

                        while (rs.next()) {
                            String col1 = rs.getString("Date");
                            String col2 = rs.getString("OrderNumber");
                            String col3 = rs.getString("Profit");

                            Object rows[] = {col1, col2, col3};
                            tblModel.addRow(rows);
                        }
                    } catch (SQLException sqle) {
                        System.out.println("Error: " + sqle.getMessage());
                    }
            }
        
        private void filterByMonth () {
            int selectedMonth = monthChooser.getMonth();
            

            String sql = "SELECT DATE_FORMAT(Date, '%M %Y') AS Months, SUM(Profit) AS Profit " +
                         "FROM sales.daily WHERE MONTH(Date) = ? " +
                         "GROUP BY Months";

            try (Connection conn = DriverManager.getConnection(url, uName, pass);
                 PreparedStatement pst = conn.prepareStatement(sql)) {

                pst.setInt(1, selectedMonth + 1);  // Month index starts from 0

                ResultSet rs = pst.executeQuery();

                DefaultTableModel tblModel = (DefaultTableModel) salesTable.getModel();
                tblModel.setRowCount(0);

                while (rs.next()) {
                    String col1 = rs.getString("Months");
                    String col2 = rs.getString("Profit");

                    Object[] rows = {col1, col2};
                    tblModel.addRow(rows);
                }
            } catch (SQLException sqle) {
                System.out.println("Error: " + sqle.getMessage());
            }
        }
        
        private void filterByYear () {
            int selectedYear = yearChooser.getYear();
            
            boolean yearSelected = selectedYear > 0;
            String sql;
            
            if (yearSelected) {
                sql = "SELECT DATE_FORMAT(Date, '%Y') AS Years, SUM(Profit) AS Profit " +
                         "FROM sales.daily WHERE YEAR(Date) = ? " +
                         "GROUP BY Years";
            }
            else {
                sql = "SELECT DATE_FORMAT(Date, '%Y') AS Years, SUM(Profit) AS Profit " +
                         "FROM sales.daily " +
                         "GROUP BY Years";
            }

            try (Connection conn = DriverManager.getConnection(url, uName, pass);
                 PreparedStatement pst = conn.prepareStatement(sql)) {

                pst.setInt(1, selectedYear);
                ResultSet rs = pst.executeQuery();

                DefaultTableModel tblModel = (DefaultTableModel) salesTable.getModel();
                tblModel.setRowCount(0);

                while (rs.next()) {
                    String col1 = rs.getString("Years");
                    String col2 = rs.getString("Profit");

                    Object[] rows = {col1, col2};
                    tblModel.addRow(rows);
                }
            } catch (SQLException sqle) {
                System.out.println("Error: " + sqle.getMessage());
            }
        }
    }