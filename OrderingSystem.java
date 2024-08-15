// @author Maningas_Arnel
package orderingsystem;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import javax.swing.border.Border;

public class OrderingSystem {

    //Main Class
    public static void main(String[] args) {
       CellEditor cell = new CellEditor();
       SupplyCatalog supply = new SupplyCatalog(cell);
       SalesCatalog sales = new SalesCatalog();
       //Calls on the LogIn frame with the supply and sales class
       new LogIn(supply, sales);
       //new MainPage(supply);
       //SupplyCatalog obj = new SupplyCatalog();
    }
    
}
