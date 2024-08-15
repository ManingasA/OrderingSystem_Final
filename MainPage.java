package orderingsystem;

//imports all the necessary class to use
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class MainPage extends JFrame implements ActionListener, KeyListener, MouseListener{

    //the frame
        JFrame mainPage = new JFrame();
    //the three panels (colored in grey)
        JPanel register, table, receipt, computation, mainPanel;
    //texts in the topPanel
        JLabel productID, productName, quantity, price, subtotalprice;
    //TextFields in the topPanel
        JTextField prodIDField, prodNameField, qtyField, priceField, subtotalpriceField,
                totalTextField, payTextField, balanceTextField;
    //buttons
        JButton plusButton, minusButton, addButton, printBillButton, check, cross;         
    //texts in the rightPanel    
        JLabel payLabel, totalLabel, balanceLabel;
    //table in the bottomPanel
        JTable bottomTable;
        Object[] column = {"Product ID", "Product Name", "Quantity", "Price", "SubTotal"};
        DefaultTableModel model = new DefaultTableModel();  
        JScrollPane scrollPane;    
    //texts in the receipts
        JLabel  subTotalReceipt, cashReceipt, changeReceipt;
    //prints out the date and time
        Date currentDate = new Date();
    //formats the time
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
    //formats the date
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
      
    //Buttons for PanelSwitching
        JButton mainButton, supplyButton, salesButton;   
    //Panels for Buttons for panelswitching
        JPanel buttonPanel;
    //icon
        JLabel image;
    //ScrollPane for Receipt
        JScrollPane pane;
        JTextArea Receipt;
        StringBuilder text = new StringBuilder();
        String space = "                                                 ";
        String divider = "   ---------------------------------------"
                + "-------------------------------------------------   ";
    //CardLayout
        CardLayout card = new CardLayout();
        JPanel cardPanel = new JPanel(card);
        SupplyCatalog supply;
        SalesCatalog sales;
  
        LocalDate date = LocalDate.now();
        private int orderNo = 0;
        
        //TEST
        JTextArea receiptarea = new JTextArea();
        

        @SuppressWarnings({"LeakingThisInConstructor", "StringConcatenationInsideStringBufferAppend"})
    MainPage(SupplyCatalog supplycatalog, SalesCatalog salescatalog){
        
        this.sales = salescatalog;
        this.supply = supplycatalog;
        
        cardPanel.setBounds(0, 0, 1335, 635);
        
        //icon
        ImageIcon icon = new ImageIcon("C:\\Users\\arnel\\OneDrive\\Desktop\\Coding\\OrderingSystem\\src\\orderingsystem\\Logo.png");
        image = new JLabel(icon);
        image.setBounds(140, 10, 100, 70);
        
       
        //mainPanel settings
        mainPanel = new JPanel();
        mainPanel.setSize(1335, 635);
        mainPanel.setLayout(null);

        //top panel
        register = new JPanel();
        register.setBackground(new Color(200, 203, 218));       
        register.setBounds(15, 15, 935, 170);
        register.setLayout(null);     
        
        productID = new JLabel("Product ID");
        productID.setBounds(50, 35, 150, 30);
        productID.setFont(new Font("Arial", Font.PLAIN, 20));
        
        prodIDField = new JTextField();
        //added the listener here so that when the user pressed "enter key"
        //the code will not perform something else that uses the same key
        prodIDField.addKeyListener(new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent enew) {
        if (enew.getKeyCode() == KeyEvent.VK_ENTER) {
            productDatabase func = new productDatabase(MainPage.this);   
        }
    }
});
        prodIDField.setFont(new Font ("Arial", Font.PLAIN, 15));
        prodIDField.setBounds(45, 75, 100, 30);
        
        productName = new JLabel("Product Name");
        productName.setBounds(220, 35, 150, 30);
        productName.setFont(new Font("Arial", Font.PLAIN, 20));
        
        //added the listener here so that when the user pressed "enter key"
        //the code will not perform something else that uses the same key
        prodNameField = new JTextField();
        prodNameField.addKeyListener(new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent enew) {
        if (enew.getKeyCode() == KeyEvent.VK_ENTER) {
            productDatabase func = new productDatabase(MainPage.this);   
        }
    }
});
        prodNameField.setFont(new Font ("Arial", Font.PLAIN, 15));
        prodNameField.setBounds(170, 75, 220, 30);
        
        quantity = new JLabel("Quantity");
        quantity.setBounds(430, 35, 150, 30);
        quantity.setFont(new Font("Arial", Font.PLAIN, 20));
        
        //added the listener here so that when the user pressed "enter key"
        //the code will not perform something else that uses the same key
        qtyField = new JTextField();
        qtyField.addKeyListener(new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent calc) {
        if (calc.getKeyCode() == KeyEvent.VK_ENTER) {
           Integer qt = Integer.valueOf(qtyField.getText());
           Double price = Double.valueOf(priceField.getText());
           String subtotal = String.valueOf(qt * price);
           subtotalpriceField.setText(subtotal);          
        }
    }
});
        qtyField.setFont(new Font ("Arial", Font.PLAIN, 15));
        qtyField.setBounds(420, 75, 100, 30);
        
        plusButton = new JButton();
        plusButton.setBounds(530, 65, 30, 20);
        plusButton.setBackground(Color.green);
        plusButton.addActionListener(this);
        plusButton.setFocusable(false);
        
        minusButton = new JButton();
        minusButton.setBounds(530, 95, 30, 20);
        minusButton.setBackground(Color.red);
        minusButton.addActionListener(this);
        minusButton.setFocusable(false);
               
        price = new JLabel("Price");
        price.setBounds(600, 35, 150, 30);
        price.setFont(new Font("Arial", Font.PLAIN, 20));
        
        priceField = new JTextField();
        priceField.setFont(new Font ("Arial", Font.PLAIN, 15));
        priceField.setBounds(590, 75, 100, 30);
        
        subtotalprice = new JLabel("Subtotal");
        subtotalprice.setBounds(750, 35, 150, 30);
        subtotalprice.setFont(new Font("Arial", Font.PLAIN, 20));
        
        subtotalpriceField = new JTextField("0.0");
        subtotalpriceField.setFont(new Font ("Arial", Font.PLAIN, 15));
        //tPriceField.setEnabled(false);
        subtotalpriceField.setBounds(745, 75, 100, 30);
        
        addButton = new JButton("Add");
        addButton.setForeground(Color.white);
        addButton.setBackground(new Color (1, 113, 187));
        addButton.addActionListener(this);
        addButton.setFocusable(false);
        addButton.setFont(new Font("Arial", Font.PLAIN, 20));
        addButton.setBounds(745, 120, 100, 40);
        
        register.add(productID);
        register.add(prodIDField);
        register.add(productName);
        register.add(prodNameField);
        register.add(quantity);
        register.add(qtyField);
        register.add(plusButton);
        register.add(minusButton);
        register.add(price);
        register.add(priceField);
        register.add(subtotalprice);
        register.add(subtotalpriceField);
        register.add(addButton); 
        
    //bottomPanel
        table = new JPanel();
        table.setBackground(new Color(200, 203, 218));
        table.setBounds(15, 200, 935, 435);
        table.setLayout(null);
   
        bottomTable = new JTable();
        model.setColumnIdentifiers(column);
        bottomTable.setModel(model);
        bottomTable.setBackground(Color.white);
        bottomTable.setForeground(Color.black);
        bottomTable.setSelectionBackground(new Color (226, 89, 103));
        bottomTable.setSelectionForeground(Color.white);
        bottomTable.setFont(new Font("Arial", Font.PLAIN, 13));
        bottomTable.setRowHeight(22);
        bottomTable.addMouseListener((MouseListener) this);
        bottomTable.setAutoCreateRowSorter(false);
        
        JTableHeader header = bottomTable.getTableHeader();
        header.setBorder(null);
        //bottomTable.setShowGrid(false);
    
        scrollPane = new JScrollPane(bottomTable);
        scrollPane.setForeground(Color.red);
        scrollPane.setBackground(Color.white);
        scrollPane.setBounds(0, 0, 935, 435);
       
        table.add(scrollPane);
      
    //Texts that will appear in the receipt panel
    //Receipt panel    
        receipt = new JPanel();
        receipt.setBackground(new Color(210, 213, 218));
        receipt.setBounds(960, 15, 375, 300);
        receipt.setLayout(null);
        
        Receipt = new JTextArea();
        Receipt.setBounds(0, 0, 375, 315);
        Receipt.setEditable(false);
        Receipt.setLineWrap(true);
        Receipt.setWrapStyleWord(true);
        
        String d = dateFormat.format(currentDate);
        String t = timeFormat.format(currentDate);

        text.append(space + "Muchi Grocery Store\n");
        text.append(space +"          Salcedo II\n");
        text.append(space +"Tel: 0000-000-0000\n");
        text.append(divider + "\n");
        text.append("                 Item\t\tQTY\tPrice\n");
        text.append(divider);
        
        Receipt.setText(text.toString());       
        
        pane = new JScrollPane (Receipt);
        pane.setBounds(0, 0, 375, 300);
        
        subTotalReceipt = new JLabel();
        cashReceipt = new JLabel();
        changeReceipt = new JLabel();
          
        receipt.add(subTotalReceipt);
        receipt.add(cashReceipt);
        receipt.add(changeReceipt);
        receipt.add(pane);
        
    //buttons
        buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBounds(15, 640, 250, 35);
        //buttonPanel.setBackground(new Color(210, 213, 218));  

        mainButton = new JButton("Main");
        mainButton.setBackground(Color.GREEN);
        mainButton.setForeground(Color.BLACK);
        mainButton.setFocusable(false);
        mainButton.addActionListener(this);
        mainButton.setFont(new Font("Arial", Font.PLAIN, 15));
        mainButton.setSize(100, 30);
        
        supplyButton = new JButton("Products");
        supplyButton.setBackground(Color.YELLOW);
        supplyButton.setForeground(Color.BLACK);
        supplyButton.addActionListener(this);
        supplyButton.setFocusable(false);
        supplyButton.setFont(new Font("Arial", Font.PLAIN, 15));
        supplyButton.setSize(100, 30);
        
        salesButton = new JButton("Sales");
        salesButton.setBackground(Color.RED);
        salesButton.setForeground(Color.BLACK);
        salesButton.setFocusable(false);
        salesButton.addActionListener(this);
        salesButton.setFont(new Font("Arial", Font.PLAIN, 15));
        salesButton.setSize(100, 30);
        
    //panel for the switching buttons
        buttonPanel.add(mainButton);
        buttonPanel.add(supplyButton);
        buttonPanel.add(salesButton);
        
    //Panel for Total, Pay, Balance 
        computation = new JPanel();
        computation.setBackground(new Color(210, 213, 218));
        computation.setBounds(960, 320, 375, 315);
        computation.setLayout(null);
        
    //Total, Pay, Balance
        totalLabel = new JLabel("Total");
        totalLabel.setBounds(165, 10, 150, 30);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 20));
        
        totalTextField = new JTextField();
        totalTextField.setBounds(120, 40, 150, 30);
        totalTextField.setFont(new Font("Arial", Font.BOLD, 25));
       //totalTextField.setEditable(false);
        
        payLabel = new JLabel("Pay");
        payLabel.setBounds(170, 80, 150, 30);
        payLabel.setFont(new Font("Arial", Font.BOLD, 20));
        
        payTextField = new JTextField();
        payTextField.setBounds(120, 110, 150, 30);
        payTextField.setFont(new Font("Arial", Font.BOLD, 25));
        payTextField.addKeyListener(this);
     
        balanceLabel = new JLabel("Balance");
        balanceLabel.setBounds(160, 150, 150, 30);
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 20));
        
        balanceTextField = new JTextField();
        balanceTextField.setBounds(120, 180, 150, 30);
        balanceTextField.setFont(new Font("Arial", Font.BOLD, 25));
        
        ImageIcon ck = new ImageIcon ("C:\\Users\\arnel\\OneDrive\\Desktop\\Coding\\OrderingSystem\\src\\orderingsystem\\Check.png");
        int width = 70, height = 70;
        Image oI = ck.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon sCk = new ImageIcon (oI);
        check = new JButton(sCk);
        check.setBorder(BorderFactory.createEmptyBorder());
        check.setBackground(null);
        check.setBounds(70, 243, 60, 50);
        check.addActionListener(this);
        check.setFont(new Font("Arial", Font.BOLD, 15));
        check.setFocusable(false);
        check.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        ImageIcon cs = new ImageIcon ("C:\\Users\\arnel\\OneDrive\\Desktop\\Coding\\OrderingSystem\\src\\orderingsystem\\Cross.png");
        int width1 = 60, height1 = 60;
        Image oi = cs.getImage().getScaledInstance(width1, height1, Image.SCALE_SMOOTH);
        ImageIcon sCs = new ImageIcon (oi);
        cross = new JButton(sCs);
        cross.setBorder(BorderFactory.createEmptyBorder());
        cross.setBackground(null);
        cross.setBounds(260, 242, 60, 50);
        cross.addActionListener(this);
        cross.setFont(new Font("Arial", Font.BOLD, 15));
        cross.setFocusable(false);
        cross.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        printBillButton = new JButton("Print Bill");
        printBillButton.setBounds(135, 245, 120, 40);
        /*printBillButton.addActionListener(e -> {
            try{
                receiptarea.print();
            }catch(Exception ex) {
                
            }
        });*/
        printBillButton.addActionListener(this);
        printBillButton.setFont(new Font("Arial", Font.BOLD, 15));
        printBillButton.setFocusable(false);
        printBillButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
    //adds all the elements for the Total, Pay, Balance section  
        computation.add(cross);
        computation.add(check);
        computation.add(printBillButton);
        computation.add(balanceTextField);
        computation.add(balanceLabel);
        computation.add(payTextField);
        computation.add(payLabel);
        computation.add(totalTextField);
        computation.add(totalLabel); 
   
    ///adds 4 panels onto the CardLayout panel
        
        mainPanel.add(computation);
        mainPanel.add(receipt);
        mainPanel.add(register);  
        mainPanel.add(table); 

        cardPanel.add(mainPanel, "Main");
        cardPanel.add(supply.supplyPanel, "Supply");
        cardPanel.add(sales.salesPanel, "Sales");
        
    //adds button panel to the frame
        mainPage.add(cardPanel);
        mainPage.add(buttonPanel);
        
    //settings of our frame  
        mainPage.setSize(1365, 720);
        mainPage.setLayout(null);
        mainPage.setResizable(false);
        mainPage.setIconImage(icon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
        mainPage.setTitle("Ordering System");
        mainPage.setIconImage(icon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
        mainPage.setLocationRelativeTo(null);     
        mainPage.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        mainPage.setVisible(true);
     
        
     //ignore the comment below this text
     //frame.setSize(java.awt.Toolkit.getDefaultToolkit().getScreenSize());
        
    }
                
        
    //the codes from here down to the bottom is what makes the buttons, textfields, etc, interactive
    @Override
        @SuppressWarnings("StringConcatenationInsideStringBufferAppend")
    public void actionPerformed(ActionEvent e) {
            
            methods m = new methods();
            String item = prodNameField.getText();
            String qty = qtyField.getText();
            String pr = priceField.getText();
            
            if (e.getSource() == supplyButton) {  
            card.show(cardPanel, "Supply");

            }

            if(e.getSource() == mainButton) {
                card.show(cardPanel, "Main");
                receipt.setVisible(true);
            }

            if (e.getSource() == salesButton) {
                card.show(cardPanel, "Sales");
            }

            if (e.getSource () == addButton) {
                Object id = prodIDField.getText();
                int checkStock = m.CheckStock(supply.supplyTable, id, 0, 3);
                               
                if (prodIDField.getText().equals("") || prodNameField.getText().equals("") ||
                    qtyField.getText().equals("") || priceField.getText().equals("")){ 
                    JOptionPane.showMessageDialog(this, "Please Enter All Data");
                   
                } 
                else {
                    if (checkStock <= 0) {
                    JOptionPane.showMessageDialog(null, "Out of Stock!");
                    return;
                    }
                    
                    if (checkStock <= 10 && checkStock >= 1) {
                        JOptionPane.showMessageDialog(null, "Stock is running very low!");
                        m.addToCart(model, prodIDField, prodNameField, qtyField, 
                            priceField, subtotalpriceField, totalTextField, Receipt, text);
        
                    }
                    if (checkStock <= 25 && checkStock >= 11) {
                        JOptionPane.showMessageDialog(null, "Stock is running low!"); 
                        m.addToCart(model, prodIDField, prodNameField, qtyField, 
                        priceField, subtotalpriceField, totalTextField, Receipt, text);
                    }
                    if (checkStock >= 26) {
                        m.addToCart(model, prodIDField, prodNameField, qtyField, 
                        priceField, subtotalpriceField, totalTextField, Receipt, text);
                    }
                }     
            }
            //if the button is pressed, it will add one
            if(e.getSource() == plusButton) {
                Integer quantityTable = Integer.valueOf(qtyField.getText());
                quantityTable++;
                qtyField.setText(quantityTable.toString());
                
                Double p = Double.valueOf(priceField.getText());
                String subtotal = String.valueOf(quantityTable * p);
                subtotalpriceField.setText(subtotal);    
            }
            //if the button is pressed, it will decrease one
            if(e.getSource() == minusButton) {
                Integer quantityTable = Integer.valueOf(qtyField.getText());
                quantityTable--;
                qtyField.setText(quantityTable.toString());
                
                Double p = Double.valueOf(priceField.getText());
                String subtotal = String.valueOf(quantityTable * p);
                subtotalpriceField.setText(subtotal);
            }
            //this code will print out the receipt once the printBill button is pressed
            
            if(e.getSource() == check) {
  
                text.append(" " + divider + "\n");
                text.append("     Subtotal: \t\t\t" + totalTextField.getText() + "\n");
                text.append("     Payment: \t\t\t" + payTextField.getText() + "\n");
                text.append("     Balance: \t\t\t" + balanceTextField.getText());
                text.append("\n\n\t              Order number " + orderNo);
                text.append("\n\t          " + dateFormat.format(currentDate));
                text.append("  " + timeFormat.format(currentDate));
                text.append("\n\t     Thank you for your purchase!\n");
                
                Receipt.setText(text.toString());
                
                double total = Double.parseDouble(totalTextField.getText()); // Default value if parsing fails

                // Clear the text fields
                totalTextField.setText("");
                payTextField.setText("");
                balanceTextField.setText("");
                
                orderNo++;
                // Update the database with the sales information
                m.salesTableUpdate(date, orderNo, total);
                m.updateStockFromTable(bottomTable);
            }
            if (e.getSource() == printBillButton) {
                
                String r = Receipt.getText();
                
                receiptarea = new JTextArea(r);
                receiptarea.setBounds(10, 0, 375, 315);
                receiptarea.setLineWrap(true);
                receiptarea.setWrapStyleWord(true);
                receiptarea.setEditable(false);
                JScrollPane spane = new JScrollPane(receiptarea);
                spane.setBounds(0, 0, 375, 315);
                
                
                JFrame receiptframe = new JFrame ();
                
                //receiptframe.setUndecorated(true);
                receiptframe.add(spane);
                receiptframe.setResizable(false);
                receiptframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                receiptframe.setVisible(true);
                receiptframe.setSize(405, 315);
                receiptframe.setLocationRelativeTo(null);
    
            }
            
            if (e.getSource() == cross) {
                
                text.setLength(0);
                text.append(getInitialReceiptText());
                Receipt.setText(text.toString());
                
                totalTextField.setText("");
                payTextField.setText("");
                balanceTextField.setText("");
                
               model.setRowCount(0);
            }
            
           
        }
    
//the code below will make it so that when the user typed a key, it will respond
    @Override
    public void keyTyped(KeyEvent e) {
        
    }
//the code below will make it so that when the user pressed a key, it will respond
//the code below will get the text in the total, pay, and balance field and turn them into integers
//and calculate them
    
    @Override
    public void keyPressed(KeyEvent e) {
       
        try {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            double num1 = Double.parseDouble(totalTextField.getText());   
            double num2 = Double.parseDouble(payTextField.getText());
            String result = String.valueOf(num2 - num1);
            
            if (num2 < num1) {
                JOptionPane.showMessageDialog(null, "Insufficient Payment");
                balanceTextField.setText("");
            }
            else {
            balanceTextField.setText(result); 
            
            subTotalReceipt.setText("Subtotal: " + " ₱" + totalTextField.getText());
            cashReceipt.setText("      Cash: " + " ₱" + payTextField.getText());
            changeReceipt.setText(" Change: " + " ₱" + result);            
            }
        }
        }
        catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "Only enter numbers");
        }

        
       /*try {
       if (e.getKeyCode() == KeyEvent.VK_ENTER) {
           productDatabase data = new productDatabase();
           
           String enteredId = prodIDField.getText();
           String productName = data.getProductName();
           String productPrice = data.getPrice();
           
           
       }
       }
       catch (NumberFormatException nme) {
           nme.printStackTrace();
       }*/
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {   
         
        if (e.getSource() == bottomTable) {
               int SelectedRowIndex = bottomTable.getSelectedRow();
               if(SelectedRowIndex != -1) {
               int option = JOptionPane.showConfirmDialog
               (null, "Do you want to delete the selected row?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
               if(option == JOptionPane.YES_OPTION) {
                   model.removeRow(SelectedRowIndex);
               }
            }
        }
    }

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
 
        @SuppressWarnings("StringConcatenationInsideStringBufferAppend")
        private String getInitialReceiptText () {
        
        StringBuilder t = new StringBuilder();

        t.append(space + "Muchi Grocery Store\n");
        t.append(space +"          Salcedo II\n");
        t.append(space +"Tel: 0000-000-0000\n");
        t.append(divider + "\n");
        t.append("                 Item\t\tQTY\tPrice\n");
        t.append(divider);
        
        return t.toString();
    }
}