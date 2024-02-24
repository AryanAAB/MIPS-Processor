/**
 * Creates a basic table for the register, data, and instructions in the GUI.
 * 
 * @author Aryan, Pratham, Arnav
 * @version 1.0
 * @since 15/02/24
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Table extends JTable
{
    private JScrollPane scrollPane;

    private Set<Integer> set;

    /**
     * @param data : The data that is to be put into the table.
     * @param columns : The columns in the table. 
     */
    public Table(Object [][] data, Object [] columns)
    {
        super(
            new DefaultTableModel(data, columns)
            {
                @Override
                public boolean isCellEditable(int row, int column)
                {
                    return false;
                }
            }
        );
        
        set = new TreeSet<>();
        
        setAlternateColor(Color.WHITE, Color.WHITE);
        
        scrollPane = new JScrollPane(this);
    }

    /**
     * @return JScrollPane : The scroll pane that has the JTable.
     */
    public JScrollPane getScrollPane()
    {
        return scrollPane;
    }

    /**
     * Creates a custom look and feel for the table.
     */
    private void setCustomLookAndFeel()
    {
        this.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        this.setRowHeight(30);
        this.setFont(new Font("SansSerif", Font.PLAIN, 12));
        this.setShowGrid(true);
        this.setGridColor(Color.LIGHT_GRAY);
        this.setEnabled(false);
        this.setDefaultEditor(Object.class, null);
    }

    /**
     * Sets the header color of the table to this color.
     * @param color : The color of the header.
     */
    public void setHeaderColor(Color color)
    {
        this.getTableHeader().setBackground(color);
        this.getTableHeader().setForeground(Color.WHITE);

        this.revalidate();
    }

    /**
     * Sets alternate colors for each row.
     * @param first : The first color
     * @param second : The second color
     */
    public void setAlternateColor(Color first, Color second)
    {
        SetAlternateRenderer alternate = new SetAlternateRenderer(first, second, null, -1);
        
        for(int i = 0; i < getColumnCount(); i++)
        {
            this.getColumnModel().getColumn(i).setCellRenderer(alternate);
        }

        setCustomLookAndFeel();

        this.revalidate();
    }

    /**
     * Sets the value at position row, col with obj.
     * @param obj : The new value at the position row, col
     * @param row : The row of the table
     * @param col : The col of the table
     * @param color1 : The first alternate color
     * @param color2 : The second alternate color
     * @param color3 : The color of this row
     */
    public void setValueAt(Object obj, int row, int col, Color color1, Color color2, Color color3)
    {
        this.setValueAt(obj, row, col);

        SetAlternateRenderer alternate = new SetAlternateRenderer(color1, color2, color3, row);

        for(int i = 0; i < this.getColumnCount(); i++)
            this.getColumnModel().getColumn(i).setCellRenderer(alternate);

        this.scrollRectToVisible(new Rectangle(this.getCellRect(row, col, true)));

        setCustomLookAndFeel();

        this.revalidate();
    }

    /**
     * Sets the color of this row to color3.
     * @param color1 : The first alternate color
     * @param color2 : The second alternate color
     * @param color3 : The color of this row
     * @param row : The row of the table
     */
    public void highlight(Color color1, Color color2, Color color3, int row)
    {
        SetAlternateRenderer alternate = new SetAlternateRenderer(color1, color2, color3, row);

        for(int i = 0; i < this.getColumnCount(); i++)
            this.getColumnModel().getColumn(i).setCellRenderer(alternate);
        
        this.scrollRectToVisible(new Rectangle(this.getCellRect(row, 0, true)));

        setCustomLookAndFeel();
    
        this.revalidate();
    }

    /**
     * Resets the table to the colors represented by first and second.
     * @param first : The first alternate color
     * @param second : The second alternate color
     */
    public void reset(Color first, Color second)
    {
        set = new TreeSet<>();

        setAlternateColor(first, second);
    }

    private class SetAlternateRenderer extends DefaultTableCellRenderer 
    {
        private Color color1, color2, color3;
        
        /**
         * @param color1 : The first alternate color
         * @param color2 : The second alternate color
         * @param color3 : The color of this row
         * @param row : The desired row
         */
        public SetAlternateRenderer(Color color1, Color color2, Color color3, int row)
        {
            set.add(row);

            this.color1 = color1;
            this.color2 = color2;
            this.color3 = color3;
        }

        /**
         * @param table : The JTable
         * @param value : The value of this cell
         * @param isSelected : Whether or not this cell is selected
         * @param hasFocus : Whether or not this cell has focus
         * @param row : The row of this cell
         * @param col : The col of this cell
         * @return Component : The new cell
         * @Override
         */
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
        {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            if(!isSelected)
            {
                if(color3 != null && set.contains(row))
                {
                    cell.setBackground(color3);
                }
                else if (row % 2 == 0)
                    cell.setBackground(color1);
                else
                    cell.setBackground(color2);
            }
            else
            {
                cell.setBackground(table.getSelectionBackground());
                cell.setForeground(table.getSelectionForeground());
            }
            
            return cell;
        }
    }
}
