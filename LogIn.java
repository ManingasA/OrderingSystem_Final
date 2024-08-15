package orderingsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.lang.*;
import java.util.Set;
import java.sql.*;


public class LogIn extends JFrame implements ActionListener{

    JFrame frame = new JFrame();
    JButton button;
    JButton signUp;
    JTextField inputUser;
    JPasswordField inputPassword;
    JLabel labelUser;
    JLabel labelPassword;
    JLabel popUpMessage;
    JLabel welcomeMessage;
    JLabel image;
    JPanel background;
    SupplyCatalog supply;
    SalesCatalog sales;
    
//calls the SupplyCatalog class because it is called in the mainPage which will be called later on
    LogIn(SupplyCatalog supplycatalog, SalesCatalog salescatalog) {
        this.sales = salescatalog;
        this.supply = supplycatalog;

        background = new JPanel();
        background.setBackground(new Color(250,240,230));
        background.setBounds(0, 0, 400, 400 );
        background.setLayout(null);

        ImageIcon icon = new ImageIcon("C:\\Users\\arnel\\OneDrive\\Desktop\\Coding\\OrderingSystem\\src\\orderingsystem\\Logo.png");
        image = new JLabel(icon);
        image.setBounds(140, 10, 100, 70);
        
        welcomeMessage = new JLabel("MUCHI GROCERY STORE");
        welcomeMessage.setFont(new Font("Book Antiqua",Font.PLAIN, 20));
        welcomeMessage.setBounds(70, 65, 250, 40);

        inputUser = new JTextField();
        inputUser.setBounds(155, 125, 150, 30);

        inputPassword = new JPasswordField();
        inputPassword.setBounds(155, 185, 150, 30);
      
        labelUser = new JLabel("Username: ");
        labelUser.setBounds(75, 90, 100, 100);

        labelPassword = new JLabel("Password: ");
        labelPassword.setBounds(75, 150, 100, 100);

        //popUpMessage = new JLabel("");
        //popUpMessage.setBounds(100, 30, 100, 100);

        button = new JButton("LogIn");
        button.setBounds(65, 250, 100, 30);
        button.setBorder(null);
        button.setForeground(Color.white);
        button.setBackground(new Color (1, 113, 187));
        button.setFocusable(false);
        button.setFont(new Font("Arial",Font.PLAIN,15));
        button.addActionListener(this);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        signUp = new JButton("Create Account");
        signUp.setBounds(185, 250, 140, 30);
        signUp.setBorder(null);
        signUp.setForeground(Color.white);
        signUp.setBackground(new Color (1, 113, 187));
        signUp.setFocusable(false);
        signUp.setFont(new Font("Arial",Font.PLAIN,15));
        signUp.addActionListener(this);
        signUp.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        background.add(inputUser);
        background.add(inputPassword);
        background.add(labelUser);
        background.add(labelPassword); 
        background.add(welcomeMessage);
        background.add(image);
        //background.add(popUpMessage);
        background.add(button);
        background.add(signUp);
        
        frame.add(background);
        frame.setTitle("LogIn"); 
        frame.setIconImage(icon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
        frame.setSize(400, 400);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null); 
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setVisible(true);
        
    }
           
    @Override
    public void actionPerformed (ActionEvent e) {
    //if button is pressed then the following codes will perform
        if (e.getSource() == button) {
        String userID = inputUser.getText();
        String password = String.valueOf(inputPassword.getPassword());

        //SQL syntax to scan and select from the database, 
        //this is to see if the user's input match something from the database
        String sql = "SELECT Password FROM login.accounts WHERE Username = ?";
        //connects to the database
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/products", "root", "PassWord");
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, userID);
            ResultSet rs = pst.executeQuery();

        //this scans every row in the database to see if the user's input match with anything,   
            if (rs.next()) {
                String storedPassword = rs.getString("Password");
                //states that if the password from the database matched with the user's password input
                if (storedPassword.equals(password)) {
                //it will dispose this frame and open up the MainPage frame
                    frame.dispose();
                    new MainPage(supply, sales);
                   
                } 
                else {
                    //if the user's password input doesn't match with anything in the database
                    //it will pop an alert showing the String
                    JOptionPane.showMessageDialog(this, "Incorrect Password");
                    /*popUpMessage.setBounds(160, 190, 500, 10);
                    popUpMessage.setForeground(Color.red);
                    popUpMessage.setText("Incorrect Password");*/
                }
                //if the user's username input doesn't match with anything in the database
                //it will pop an alert showing the String
            } else { 
                JOptionPane.showMessageDialog(this, "Username not found");
               /* popUpMessage.setBounds(160, 130, 500, 10);
                popUpMessage.setForeground(Color.red);
                popUpMessage.setText("Username not found");*/
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }
        
        if (e.getSource() == signUp) {
            //if the user pressed the SignUp button, it will call the SignIn class
            SignIn createNewAccount = new SignIn();
        }
    }
}