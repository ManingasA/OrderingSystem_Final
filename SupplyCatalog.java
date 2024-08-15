package orderingsystem;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;


public class SupplyCatalog extends JFrame implements ActionListener, MouseListener{
  
   //Table
    JTable supplyTable;
    Object [] columns = {"Product ID", "Product Name", "Price", "Stock"};
    DefaultTableModel model = new DefaultTableModel(columns, 0);
    
    //JFrame supplyFrame = new JFrame();
    JScrollPane scroll;
    JButton aButton, dButton;
    JPanel tablePanel, addingPanel, supplyPanel/*, buttonPanel2*/;
    JLabel id, name, prc, stk;
    JLabel image;
    JTextField ID, Name, Prc, Stk;

    CellEditor cell;
    Timer timer;
    
    @SuppressWarnings({"LeakingThisInConstructor", "Convert2Lambda"})
    public SupplyCatalog(CellEditor celleditor) {
        this.cell = celleditor;
        
        supplyPanel = new JPanel();
        supplyPanel.setBounds(0, 0, 1365, 720);
        supplyPanel.setLayout(null);
        
        ImageIcon icon = new ImageIcon("Logo.png");
        image = new JLabel(icon);
        image.setBounds(140, 10, 100, 70);
       
      /*supplyFrame.setSize(1365, 720);
        supplyFrame.setResizable(false);
        supplyFrame.setLocationRelativeTo(null);
        supplyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        supplyFrame.setLayout(null);*/
       
        //SQL database to Table
         timer = new Timer (1000, new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 methods m = new methods();
                 m.updateSupplyTable(model);
             }
         });
         timer.start();

        //Table Panel
        tablePanel = new JPanel();
        tablePanel.setBounds(300, 10, 1040, 625);
        tablePanel.setBackground(Color.GRAY);
        tablePanel.setLayout(null);
        
        //Table
        supplyTable = new JTable();
        supplyTable.setRowHeight(20);
        supplyTable.setModel(model);
        supplyTable.setBackground(Color.white);
        supplyTable.setForeground(Color.black);
        supplyTable.setAutoCreateRowSorter(false);
        
        CellEditor cd = new CellEditor(); 
        //supplyTable.getColumnModel().getColumn(1).setCellEditor(cd);
        supplyTable.getColumnModel().getColumn(2).setCellEditor(cd); 
        supplyTable.getColumnModel().getColumn(3).setCellEditor(cd);
        
        supplyTable.addMouseListener(this);
        model.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    int row = e.getFirstRow();
                    int column = e.getColumn();
                    Object columnName = model.getColumnName(column);
                    Object data = model.getValueAt(row, column);
                    String id = model.getValueAt(row, 0).toString();
                    if (column == 2) {
                        updatePrice(id, columnName, data.toString());
                    }
                    else if (column == 3) {
                        updateDatabase(id, columnName, data.toString()); 
                    }
                }
            }
        });
        //adds table to a scrollpane
        scroll = new JScrollPane(supplyTable);
        scroll.setBounds(0, 0, 1035, 625);
        //adds scrollpane to the panel
        tablePanel.add(scroll);
        
        //adding and deleting panel
        addingPanel = new JPanel();
        addingPanel.setBounds(10, 10, 280, 625);
        addingPanel.setBackground(new Color(213, 213, 213));
        addingPanel.setLayout(null);
        
        //Button
        aButton = new JButton("Add");
        aButton.setForeground(Color.white);
        aButton.setBackground(new Color (1, 113, 187));
        aButton.addActionListener(this);
        aButton.setFocusable(false);
        aButton.setFont(new Font("Arial", Font.PLAIN, 20));
        aButton.setBounds(90, 500, 110, 40);
 
        //TextFields and JLabel
        id = new JLabel("Product ID");
        id.setBounds(30, 60, 150, 30);
        id.setFont(new Font("Arial", Font.PLAIN, 20));
        
        ID = new JTextField();
        ID.setFont(new Font ("Arial", Font.PLAIN, 15));
        ID.setBounds(30, 100, 220, 30);
        
        name = new JLabel("Product Name");
        name.setBounds(30, 160, 150, 30);
        name.setFont(new Font("Arial", Font.PLAIN, 20));
        
        Name = new JTextField();
        Name.setFont(new Font ("Arial", Font.PLAIN, 15));
        Name.setBounds(30, 200, 220, 30);
        
        prc = new JLabel("Price");
        prc.setBounds(30, 260, 150, 30);
        prc.setFont(new Font("Arial", Font.PLAIN, 20));
        
        Prc = new JTextField();
        Prc.setFont(new Font ("Arial", Font.PLAIN, 15));
        Prc.setBounds(30, 300, 220, 30);
        
        stk = new JLabel("Stock");
        stk.setBounds(30, 360, 150, 30);
        stk.setFont(new Font("Arial", Font.PLAIN, 20));
        
        Stk = new JTextField();
        Stk.setFont(new Font ("Arial", Font.PLAIN, 15));
        Stk.setBounds(30, 400, 220, 30);
  
        addingPanel.add(ID);
        addingPanel.add(id);
        addingPanel.add(Name);
        addingPanel.add(name);
        addingPanel.add(Prc);
        addingPanel.add(prc);
        addingPanel.add(Stk);
        addingPanel.add(stk);
        addingPanel.add(aButton);
        
        supplyPanel.add(addingPanel);
        supplyPanel.add(tablePanel);
 
    }

    @Override
    //this actionPerformed method is responsible for registring new products
    //updating the database, and automatically updating the table in the frame
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == aButton) {
            addProduct();
        }  
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
        if (e.getSource() == supplyTable) {
            int SelectedRowIndex = supplyTable.getSelectedRow();
               if(SelectedRowIndex != -1) {
               int option = JOptionPane.showConfirmDialog
               (null, "Do you want to delete the selected row?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
               if(option == JOptionPane.YES_OPTION) {
                   methods m = new methods();
                   m.deleteRow(SelectedRowIndex, model);
               }
            else {
                
            }
        }
  
            /*if (e.getSource() == supplyTable) {
               int choice = JOptionPane.showConfirmDialog(
               null, "Do you want to restock?", "", JOptionPane.YES_NO_OPTION);
               if(choice == JOptionPane.YES_OPTION) {
                    int row = supplyTable.rowAtPoint(e.getPoint());
                    int col = supplyTable.columnAtPoint(e.getPoint());
                    if (row >= 0 && col >= 0) {
                        supplyTable.editCellAt(row, col);
                        Component editor = supplyTable.getEditorComponent();
                        if (editor != null) {
                    editor.requestFocus();
               }
                else if (choice == JOptionPane.NO_OPTION){
                    int choice2 = JOptionPane.showConfirmDialog(
                    null, "Do you want to delete a product?", "", JOptionPane.YES_NO_OPTION);
                    if (choice2 == JOptionPane.YES_OPTION) {
                        deleteProduct();
                    }
                    }     
                 }
             }
        }*/
    }}


    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
      
    }
    
    public void updateDatabase(String id, Object stock, String newValue) {

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
            System.out.println("Error: " + e.getMessage());
        }
    }
    public void updatePrice(String id, Object price, String newValue) {

        String url = "jdbc:mysql://localhost:3306/products";
        String user = "root";
        String pass = "PassWord";

        String updateQuery = "UPDATE prddatabase SET Price = ? WHERE IDproduct = ?";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
                pstmt.setString(1, newValue);
                pstmt.setString(2, id);
                pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
        
    private void addProduct() {
        //SQL Syntax for inputing a product into database 
       String insert = "INSERT INTO prddatabase (IDproduct, ProductName, Price, Stocks) VALUES (?, ?, ?, ?)";
       //and checking each rows to look for a match
       String check = "SELECT COUNT(*) AS count FROM prddatabase WHERE IDproduct = ?;";
       
       //Connects 
        try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/products", "root", "PassWord");
              PreparedStatement pstinsert = conn.prepareStatement(insert);
              PreparedStatement pstcheck = conn.prepareStatement(check)) {
              Statement stmt = conn.createStatement();
              ResultSet rs = stmt.executeQuery("SELECT * FROM prddatabase;");
            
               String i = ID.getText(); 
               String n = Name.getText();
               String p = Prc.getText();
               String s = Stk.getText();
               
               pstcheck.setString(1, i);
               ResultSet rsCheck = pstcheck.executeQuery();
               rsCheck.next();
               int count = rsCheck.getInt("count");
         
               if (i.isEmpty() || n.isEmpty() || p.isEmpty() || s.isEmpty()) {
                   JOptionPane.showMessageDialog(this, "Please fill in all required fields.", "Error", JOptionPane.ERROR_MESSAGE);
               }
               else if (count > 0) {
                   JOptionPane.showMessageDialog(this, "Product ID already exists.", "Error", JOptionPane.ERROR_MESSAGE);
               }
               
               else {
                   pstinsert.setString(1, i);
                   pstinsert.setString(2, n);
                   pstinsert.setString(3, p);
                   pstinsert.setString(4, s);
                   
                   int registered = pstinsert.executeUpdate();
                   if(registered > 0) {
                       JOptionPane.showMessageDialog(this, "Added Succesfully!", "", JOptionPane.INFORMATION_MESSAGE);
                       ID.setText("");
                       Name.setText("");
                       Prc.setText("");
                       Stk.setText("");
                       
                       //calls the supplyTable
                       DefaultTableModel model = (DefaultTableModel) supplyTable.getModel();
                       //adds data from textfields into a row
                       Object [] rowData = {i, n, p, s};
                       model.addRow(rowData);

                       this.dispose();    
                   }
                   
               }pstinsert.close();
               conn.close();
               
        } 
        
       catch(SQLException sqle) {
           System.out.println("Error: " + sqle.getMessage());
       }
    }

}
