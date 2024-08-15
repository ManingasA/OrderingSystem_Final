
package orderingsystem;

import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import java.awt.*;

//This class is responsible for allowing the users to edit the cells in the table
//It's not done yet, still learning each methods
public class CellEditor extends AbstractCellEditor implements TableCellEditor{

    JTextField textField;
    
    public CellEditor () {

        textField = new JTextField();
    }

    @Override
    public Object getCellEditorValue() {
        return textField.getText();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        
        textField.setText(value != null ? value.toString() : "");
        return textField;
    }

    @Override
    public boolean isCellEditable (EventObject e) {
        return true;
    }
}
