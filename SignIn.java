
package orderingsystem;

import java.util.*;
import java.awt.event.*;
import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.border.Border;

public class SignIn extends JFrame implements ActionListener{
    
    //creates a variable for each elements in the frame that are to be initialized later on
    JFrame frame = new JFrame();
    JPanel background;
    JButton create;
    JTextField firstName, surName, username;
    JPasswordField password;
    JLabel firstNameLabel, surNameLabel, usernameLabel, passwordLabel;
    SupplyCatalog supply;
    SalesCatalog sales;
 
    SignIn() {
     //calls on the variables outside constructor and instantiate and modify them
        background = new JPanel();
        background.setBackground(new Color(254, 241, 2));
        background.setBounds(0, 0, 400, 450 );
        
        firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setBounds(60, 80, 150, 10);
          
        surNameLabel = new JLabel("Surname:");
        surNameLabel.setBounds(60, 130, 150, 10);
               
        usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(60, 180, 150, 10);
         
        passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(60, 230, 150, 10);
       
        firstName = new JTextField();
        firstName.setBounds(140, 70, 150, 30);
        
        surName = new JTextField();
        surName.setBounds(140, 120, 150, 30);
        
        username = new JTextField();
        username.setBounds(140, 170, 150, 30);
        
        password = new JPasswordField();
        password.setBounds(140, 220, 150, 30);
       
    //sets the text inside a button    
        create = new JButton("Create");
    //sets the properties of the button
        create.setBounds(140, 330, 100, 30);
        create.setBorder(null);
        create.setForeground(Color.white);
        create.setBackground(new Color (1, 113, 187));
    //makes it so that there are no borken line squares appearing when button is pressed
        create.setFocusable(false);
        create.setFont(new Font("Arial",Font.PLAIN,15));
    //makes the button interactive
        create.addActionListener(this);
    //makes it so that when the cursor hovers over the button it changes the cursor's icon
        create.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
    //adds every components or elements onto the frame
        frame.add(firstNameLabel);
        frame.add(surNameLabel);
        frame.add(usernameLabel);
        frame.add(passwordLabel);
        frame.add(firstName);
        frame.add(surName);
        frame.add(username);
        frame.add(password);
        frame.add(create);
        frame.add(background);
    //properties of the frame
        frame.setTitle("Sign Up");
        frame.setSize(400, 450);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
  
    //SQL syntax to be performed earlier
    //registers users input into database
        String sql = "INSERT INTO login.accounts (FirstName, LastName, Username, Password) VALUES (?, ?, ?, ?)";
        
    //responsible for creating a connection between this java system and the database    
        try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/login", "root", "PassWord");
            //this prepares the SQL syntax to be executed later on
              PreparedStatement pst = conn.prepareStatement(sql)) {
            //gets the text from the textfield and puts them in a String
               String name = firstName.getText();
               String lastName = surName.getText();
               String uName = username.getText();
               String pWord = password.getText();
            //it will pop up an alert when one of the textfields are empty
               if (name.isEmpty() || lastName.isEmpty() || uName.isEmpty() || pWord.isEmpty()) {
                   JOptionPane.showMessageDialog(this, "Please fill in all required fields.", "Error", JOptionPane.ERROR_MESSAGE);
               }
               
               else {
                   
                //inserts data from textfields to the respective columns   
                   pst.setString(1, name);
                   pst.setString(2, lastName);
                   pst.setString(3, uName);
                   pst.setString(4, pWord);
                //executes the prepared statement with the given data 
                   int registered = pst.executeUpdate();
                //pops up an alert once the registration is successful
                   if(registered > 0) {
                       JOptionPane.showMessageDialog(this, "Registered Succesful!", "Registered", JOptionPane.INFORMATION_MESSAGE);
                    //calls on the LogIn page and disposes/ closes this frame
                       new LogIn(supply, sales);
                       frame.dispose();
                       
                   }
               }
               //closes the connection from database
               pst.close();
               conn.close();
               
        } 
        
       catch(SQLException sqle) {
           //prints the error if there is one
           sqle.printStackTrace();
       }
    
}}
             

